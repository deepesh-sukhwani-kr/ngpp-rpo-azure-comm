package com.kroger.ngpp.azurestorage.streams;

import java.io.InputStream;

public interface AzureApiAdapter {
    InputStream getBlobInputStream(String endpoint);
}
