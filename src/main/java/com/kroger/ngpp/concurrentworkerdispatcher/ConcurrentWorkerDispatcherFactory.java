package com.kroger.ngpp.concurrentworkerdispatcher;

import com.kroger.ngpp.common.utils.exceptionhandler.ExceptionHandler;
import com.kroger.ngpp.jsonreader.JsonReader;
import reactor.core.scheduler.Scheduler;

public interface ConcurrentWorkerDispatcherFactory<E> {

    ConcurrentWorkerDispatcher<E> createConcurrentWorkerDispatcher(
            JsonReader<E> source,
            int chunkSize,
            int concurrency,
            ExceptionHandler exceptionHandler
    );

    ConcurrentWorkerDispatcher<E> createConcurrentWorkerDispatcher(
            JsonReader<E> source,
            int chunkSize,
            int concurrency,
            ExceptionHandler exceptionHandler,
            Scheduler scheduler
    );
}
