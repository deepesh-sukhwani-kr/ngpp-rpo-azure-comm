package com.kroger.ngpp.azurestorage;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public interface AzureStorageService<E> {

    /**
     * Returns a cold publisher that will emit chunks of elements from the Azure Blob
     *
     * @param endpoint Azure endpoint containing the SAS token
     * @param callback Callback that will be executed concurrently for each chunk of elements. The
     *                 callback should handle its exceptions gracefully.
     * @return A cold publisher. Its onError will allow you to handle errors from the source.
     */
    Flux<List<E>> getPublisher(String endpoint, Consumer<? super List<E>> callback);
}
