package com.kroger.ngpp.azurestorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.ngpp.azurestorage.streams.AzureApiAdapter;
import com.kroger.ngpp.common.utils.exceptionhandler.ExceptionHandler;
import com.kroger.ngpp.concurrentworkerdispatcher.ConcurrentWorkerDispatcher;
import com.kroger.ngpp.concurrentworkerdispatcher.ConcurrentWorkerDispatcherFactory;
import com.kroger.ngpp.jsonreader.JsonReader;
import com.kroger.ngpp.jsonreader.JsonReaderFactory;
import reactor.core.publisher.Flux;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public class BaseAzureStorageService<E> implements AzureStorageService<E> {

    private final JsonReaderFactory<E>                 readerFactory;
    private final ConcurrentWorkerDispatcherFactory<E> dispatcherFactory;
    private final AzureApiAdapter  azureApiAdapter;
    private final ExceptionHandler exceptionHandler;
    private final ObjectMapper     objectMapper;
    private final int                                  chunkSize;
    private final int                                  concurrency;

    public BaseAzureStorageService(final JsonReaderFactory<E> readerFactory,
            final ConcurrentWorkerDispatcherFactory<E> dispatcherFactory,
            final AzureApiAdapter azureApiAdapter,
            final ExceptionHandler exceptionHandler,
            final ObjectMapper objectMapper,
            final int chunkSize,
            final int concurrency) {
        this.readerFactory = readerFactory;
        this.dispatcherFactory = dispatcherFactory;
        this.exceptionHandler = exceptionHandler;
        this.azureApiAdapter = azureApiAdapter;
        this.objectMapper = objectMapper;
        this.chunkSize = chunkSize;
        this.concurrency = concurrency;
    }

    @Override
    public Flux<List<E>> getPublisher(final String endpoint,
            final Consumer<? super List<E>> callback) {
        final InputStream blobInputStream = azureApiAdapter.getBlobInputStream(endpoint);
        final JsonReader<E> reader = readerFactory.createJsonReader(blobInputStream, objectMapper);
        final ConcurrentWorkerDispatcher<E> dispatcher = dispatcherFactory
                .createConcurrentWorkerDispatcher(reader, chunkSize, concurrency, exceptionHandler);
        return dispatcher.executeOnEach(callback);
    }

}
