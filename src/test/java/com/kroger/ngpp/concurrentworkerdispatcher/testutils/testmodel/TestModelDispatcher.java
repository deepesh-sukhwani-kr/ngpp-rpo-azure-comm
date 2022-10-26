package com.kroger.ngpp.concurrentworkerdispatcher.testutils.testmodel;

import com.kroger.ngpp.common.utils.exceptionhandler.ExceptionHandler;
import com.kroger.ngpp.concurrentworkerdispatcher.BaseConcurrentWorkerDispatcher;
import com.kroger.ngpp.jsonreader.JsonReader;
import com.kroger.ngpp.testutils.models.TestModel;
import reactor.core.scheduler.Scheduler;

public class TestModelDispatcher extends BaseConcurrentWorkerDispatcher<TestModel> {

    public TestModelDispatcher(JsonReader<TestModel> source, int chunkSize, int concurrency,
            ExceptionHandler exceptionHandler,
            Scheduler scheduler) {
        super(source, chunkSize, concurrency, exceptionHandler, scheduler);
    }

    public TestModelDispatcher(JsonReader<TestModel> source, int chunkSize, int concurrency,
            ExceptionHandler exceptionHandler) {
        super(source, chunkSize, concurrency, exceptionHandler);
    }
}
