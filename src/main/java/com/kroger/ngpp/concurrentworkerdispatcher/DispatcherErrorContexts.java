package com.kroger.ngpp.concurrentworkerdispatcher;

import com.kroger.ngpp.common.utils.exceptionhandler.errorcontext.ErrorContext;

public enum DispatcherErrorContexts implements ErrorContext {

    SOURCE_CONTEXT("source"),
    CHUNK_CONTEXT("chunk");

    private final String value;

    DispatcherErrorContexts(String value) {
        this.value = String.format("%s.%s",
                DispatcherErrorContexts.class.getName(),
                value);
    }

    @Override
    public String getContext() {
        return this.value;
    }

}
