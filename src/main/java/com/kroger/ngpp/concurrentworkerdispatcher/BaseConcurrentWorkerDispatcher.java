package com.kroger.ngpp.concurrentworkerdispatcher;

import com.kroger.ngpp.common.utils.exceptionhandler.ExceptionHandler;
import com.kroger.ngpp.jsonreader.JsonReader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.kroger.ngpp.concurrentworkerdispatcher.DispatcherErrorContexts.CHUNK_CONTEXT;
import static com.kroger.ngpp.concurrentworkerdispatcher.DispatcherErrorContexts.SOURCE_CONTEXT;

public abstract class BaseConcurrentWorkerDispatcher<E>
        implements ConcurrentWorkerDispatcher<E> {

    private final JsonReader<E> source;
    private final int           chunkSize;
    private final int              concurrency;
    private final ExceptionHandler exceptionHandler;
    private final Scheduler        scheduler;

    /**
     * @param source           the source from which items will be retrieved.
     * @param chunkSize        elements will be grouped in lists of this size
     * @param concurrency      The amount of concurrent inner subscriptions.
     *                         It doesn't necessarily mean that they will be run on parallel.
     * @param exceptionHandler defines how to handle the errors that occur either in the source
     *                         or while processing the chunks.
     * @param scheduler        indicates how to run the concurrent subscriptions.
     */
    public BaseConcurrentWorkerDispatcher(
            final JsonReader<E> source,
            final int chunkSize,
            final int concurrency,
            final ExceptionHandler exceptionHandler,
            final Scheduler scheduler) {
        this.source = source;
        this.concurrency = concurrency;
        this.chunkSize = chunkSize;
        this.exceptionHandler = exceptionHandler;
        this.scheduler = scheduler;
    }

    /**
     * @param source           the source from which items will be retrieved.
     * @param chunkSize        elements will be grouped in lists of this size
     * @param concurrency      The amount of concurrent inner subscriptions.
     *                         It doesn't necessarily mean that they will be run on parallel.
     * @param exceptionHandler defines how to handle the errors that occur either in the source
     *                         or while processing the chunks.
     */
    public BaseConcurrentWorkerDispatcher(
            final JsonReader<E> source,
            final int chunkSize,
            final int concurrency,
            final ExceptionHandler exceptionHandler) {
        this(source, chunkSize, concurrency, exceptionHandler, Schedulers.boundedElastic());
    }

    @Override
    public Flux<List<E>> executeOnEach(final Consumer<? super List<E>> callback) {
        return Flux.generate((SynchronousSink<E> sink) -> {
                    if (source.hasNext()) {
                        sink.next(source.next());
                    }
                    else {
                        sink.complete();
                    }
                })
                .buffer(chunkSize)
                .flatMap(chunk -> Mono.just(chunk)
                                .doOnNext(input -> processChunk(callback, input))
                                .subscribeOn(scheduler)
                        , concurrency)
                .doOnError(this::closeReaderOnError)
                .doOnComplete(this::closeReaderOnComplete);
    }

    private void processChunk(final Consumer<? super List<E>> callback, List<E> elements) {
        try {
            callback.accept(elements);
        }
        catch (Throwable error) {
            exceptionHandler.handle(error, CHUNK_CONTEXT);
        }
    }

    private void closeReaderOnError(final Throwable e) {
        closeReader(e::addSuppressed);
        handleSourceError(e);
    }

    private void closeReaderOnComplete() {
        closeReader(this::handleSourceError);
    }

    private void closeReader(final Consumer<? super Throwable> handleCloseException) {
        if (source != null) {
            try {
                source.close();
            }
            catch (IOException ex) {
                handleCloseException.accept(ex);
            }
        }
    }

    private void handleSourceError(final Throwable error) {
        exceptionHandler.handle(error, SOURCE_CONTEXT);
    }
}
