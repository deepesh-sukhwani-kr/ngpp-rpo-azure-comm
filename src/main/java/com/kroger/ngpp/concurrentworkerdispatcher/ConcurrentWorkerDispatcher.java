package com.kroger.ngpp.concurrentworkerdispatcher;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public interface ConcurrentWorkerDispatcher<E> {

    /**
     * Returns a cold publisher that will execute the provided callback concurrently
     *
     * @param callback to be executed for each list
     * @return a cold publisher
     */
    Flux<List<E>> executeOnEach(Consumer<? super List<E>> callback);
}
