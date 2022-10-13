package com.kroger.ngpp.azurestorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.ngpp.azurestorage.streams.AzureApiAdapter;
import com.kroger.ngpp.common.utils.exceptionhandler.ExceptionHandler;
import com.kroger.ngpp.concurrentworkerdispatcher.ConcurrentWorkerDispatcher;
import com.kroger.ngpp.concurrentworkerdispatcher.ConcurrentWorkerDispatcherFactory;
import com.kroger.ngpp.jsonreader.JsonReader;
import com.kroger.ngpp.jsonreader.JsonReaderFactory;
import com.kroger.ngpp.testutils.models.TestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BaseAzureStorageServiceTest {

    @Mock
    private JsonReaderFactory<TestModel>                 readerFactory;
    @Mock
    private JsonReader<TestModel>                        reader;
    @Mock
    private ConcurrentWorkerDispatcherFactory<TestModel> dispatcherFactory;
    @Mock
    private ConcurrentWorkerDispatcher<TestModel> dispatcher;
    @Mock
    private AzureApiAdapter  azureApiAdapter;
    @Mock
    private ExceptionHandler exceptionHandler;
    @Mock
    private InputStream                       azureStream;
    @Mock
    private Consumer<? super List<TestModel>> onNextCallback;
    @Mock
    private Flux<List<TestModel>>             publisher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final int          chunkSize    = 42;
    private final int          concurrency  = 777;

    private final String endpoint = "endpoint";

    @Test
    public void getPublisherTest() {

        when(azureApiAdapter.getBlobInputStream(endpoint)).thenReturn(azureStream);
        when(readerFactory.createJsonReader(azureStream, objectMapper)).thenReturn(reader);
        when(dispatcherFactory.createConcurrentWorkerDispatcher(
                reader, chunkSize, concurrency, exceptionHandler)
        ).thenReturn(dispatcher);
        when(dispatcher.executeOnEach(onNextCallback)).thenReturn(publisher);

        final BaseAzureStorageService<TestModel> service = new BaseAzureStorageService<>(
                readerFactory, dispatcherFactory, azureApiAdapter,
                exceptionHandler, objectMapper, chunkSize, concurrency);

        assertSame(publisher, service.getPublisher(endpoint, onNextCallback));
    }

}
