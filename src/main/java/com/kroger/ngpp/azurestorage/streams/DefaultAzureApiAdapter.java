package com.kroger.ngpp.azurestorage.streams;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;

import java.io.InputStream;

public class DefaultAzureApiAdapter implements AzureApiAdapter {

    @Override
    public InputStream getBlobInputStream(final String endpoint) {
        final BlobClient blobClient = new BlobClientBuilder().endpoint(endpoint).buildClient();
        return blobClient.openInputStream();
    }

}
