package com.kroger.ngpp.concurrentworkerdispatcher;

import com.kroger.ngpp.common.utils.exceptionhandler.ExceptionHandler;
import com.kroger.ngpp.concurrentworkerdispatcher.testutils.testmodel.TestModelDispatcher;
import com.kroger.ngpp.concurrentworkerdispatcher.testutils.testmodel.TestModelPersistence;
import com.kroger.ngpp.jsonreader.testutils.mockreaders.CollectionJsonReader;
import com.kroger.ngpp.testutils.exceptions.MockException;
import com.kroger.ngpp.testutils.exceptions.TestSetupException;
import com.kroger.ngpp.testutils.math.Divisions;
import com.kroger.ngpp.testutils.models.TestModel;
import com.kroger.ngpp.testutils.models.TestModelFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.kroger.ngpp.concurrentworkerdispatcher.DispatcherErrorContexts.CHUNK_CONTEXT;
import static com.kroger.ngpp.concurrentworkerdispatcher.DispatcherErrorContexts.SOURCE_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BaseConcurrentWorkerDispatcherTest {

    private final static String DOWNSTREAM_OUTPUT_DIRECTORY = String.format(
            "src/test/DispatcherTestOutput-%s.txt",
            Instant.now());

    @Mock
    private ExceptionHandler exceptionHandler;

    @Test
    public void whenValidInputModels_thenExpectAllOfThem() {

        final int totalElements = 103;
        final int chunkSize = 5;
        final int totalChunks = 21;

        final AtomicInteger callbackCounter = new AtomicInteger(0);
        final List<TestModel> models = TestModelFactory.createRandomModels(totalElements);
        final Flux<List<TestModel>> publisher = new TestModelDispatcher(
                new CollectionJsonReader<>(models),
                chunkSize,
                10,
                exceptionHandler
        ).executeOnEach(chunk -> {
            callbackCounter.incrementAndGet();
        });
        StepVerifier.create(publisher)
                .expectNextCount(totalChunks) // 20 chunks of 5 and 1 of 3
                .expectComplete()
                .verify();
        assertEquals(totalChunks, callbackCounter.get());
    }

    @Test
    public void whenExceptionThrownInSource_thenExpectError() {
        final List<TestModel> models = TestModelFactory.createRandomModels(100);
        final Flux<List<TestModel>> publisher = new TestModelDispatcher(
                new CollectionJsonReader<>(models, 55),
                1,
                1, //Concurrency set to 1 to know that exactly 55 will call Next before Error
                exceptionHandler
        ).executeOnEach(chunk -> {
            //Do something
        });
        StepVerifier.create(publisher)
                .expectNextCount(55)
                .expectError(MockException.class)
                .verify();
        verify(exceptionHandler, times(1))
                .handle(any(MockException.class), eq(SOURCE_CONTEXT));
    }

    @Test
    public void whenExceptionThrownInChunk_thenContinueWithOthers() {
        final int totalElements = 100;
        final int chunkSize = 5;
        final long totalChunks = Divisions.LongRoundUp(totalElements, chunkSize);

        final AtomicInteger callbackCounter = new AtomicInteger(0);
        final List<TestModel> models = TestModelFactory.createRandomModels(totalElements);
        final Flux<List<TestModel>> publisher = new TestModelDispatcher(
                new CollectionJsonReader<>(models),
                chunkSize,
                10,
                exceptionHandler
        ).executeOnEach(chunk -> {
            int element = callbackCounter.incrementAndGet();
            if (element == 10) {
                throw new MockException("Random chunk failed");
            }
        });
        StepVerifier.create(publisher)
                .expectNextCount(totalChunks)
                .expectComplete()
                .verify();
        verify(exceptionHandler, times(1))
                .handle(any(MockException.class), eq(CHUNK_CONTEXT));
    }

    @Test
    @Disabled("Only run in local due to Thread sleep + directory creation")
    public void persistInFiles() {

        final int inputLength = 203;
        final int chunkSize = 5;
        final int concurrency = 11;

        final List<TestModel> models = TestModelFactory.createRandomModels(inputLength);
        final BaseConcurrentWorkerDispatcher<TestModel> dispatcher = new TestModelDispatcher(
                new CollectionJsonReader<>(models),
                chunkSize,
                concurrency,
                exceptionHandler
        );
        final Random rand = new Random();
        final TestModelPersistence persistence = new TestModelPersistence(
                DOWNSTREAM_OUTPUT_DIRECTORY);

        final Flux<List<TestModel>> publisher = dispatcher.executeOnEach(
                chunk -> {
                    try {
                        //Simulate IO
                        Thread.sleep(rand.nextInt(3000));
                        //Persist each chunk to a new file in a concurrent way
                        persistence.persist(chunk);
                    }
                    catch (Exception e) {
                        throw new TestSetupException("Failed to persist in files", e);
                    }
                });

        final long elementsEmitted = Divisions.LongRoundUp(inputLength, chunkSize);

        StepVerifier.create(publisher)
                .expectNextCount(elementsEmitted)
                .expectComplete()
                .verify();
    }

    @Test
    @Disabled("Only run in local due to Thread sleep + directory creation")
    public void persistInFilesWhitNullSource() {

        final int inputLength = 203;
        final int chunkSize = 5;
        final int concurrency = 11;

        final List<TestModel> models = TestModelFactory.createRandomModels(inputLength);
        final BaseConcurrentWorkerDispatcher<TestModel> dispatcherNull = new TestModelDispatcher(
                null,
                chunkSize,
                concurrency,
                exceptionHandler
        );
        final Random rand = new Random();
        final TestModelPersistence persistence = new TestModelPersistence(
                DOWNSTREAM_OUTPUT_DIRECTORY);

        final Flux<List<TestModel>> publisherNull = dispatcherNull.executeOnEach(
                chunk -> {
                    try {
                        //Simulate IO
                        Thread.sleep(rand.nextInt(3000));
                        //Persist each chunk to a new file in a concurrent way
                        persistence.persist(chunk);
                    }
                    catch (Exception e) {
                        throw new TestSetupException("Failed to persist in files", e);
                    }
                });

        StepVerifier.create(publisherNull)
                .expectError()
                .verify();

        final BaseConcurrentWorkerDispatcher<TestModel> dispatcher = new TestModelDispatcher(
                new CollectionJsonReader<>(models),
                chunkSize,
                concurrency,
                exceptionHandler
        );
        final Flux<List<TestModel>> publisher = dispatcher.executeOnEach(
                chunk -> {
                    try {
                        //Simulate IO
                        Thread.sleep(rand.nextInt(3000));
                        //Persist each chunk to a new file in a concurrent way
                        persistence.persist(chunk);
                    }
                    catch (Exception e) {
                        throw new TestSetupException("Failed to persist in files", e);
                    }
                });

        StepVerifier.create(publisher)
                .expectComplete()
                .verify();
    }
}
