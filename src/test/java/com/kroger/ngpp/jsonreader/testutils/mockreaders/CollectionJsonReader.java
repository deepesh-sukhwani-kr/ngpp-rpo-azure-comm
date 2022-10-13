package com.kroger.ngpp.jsonreader.testutils.mockreaders;

import com.kroger.ngpp.jsonreader.JsonReader;
import com.kroger.ngpp.testutils.exceptions.MockException;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class CollectionJsonReader<E> implements JsonReader<E> {

    private final Iterator<E> sourceIterator;
    private       long        throwExceptionOnIteration = Long.MAX_VALUE;
    private       long        currentIteration          = 0;

    public CollectionJsonReader(Collection<E> source) {
        this.sourceIterator = source.iterator();
    }

    public CollectionJsonReader(Collection<E> source, long throwExceptionOnIteration) {
        this.sourceIterator = source.iterator();
        this.throwExceptionOnIteration = throwExceptionOnIteration;
    }

    @Override
    public boolean hasNext() {
        return this.sourceIterator.hasNext();
    }

    @Override
    public E next() {
        if (currentIteration < throwExceptionOnIteration) {
            this.currentIteration++;
            return this.sourceIterator.next();
        }
        else {
            throw new MockException("Mocking exception");
        }
    }

    @Override
    public void close() throws IOException {
        //Do nothing (Since it's not really a closeable source)
    }
}
