package com.kroger.ngpp.jsonreader.testutils.testmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kroger.ngpp.jsonreader.BaseJsonReader;
import com.kroger.ngpp.testutils.models.TestModel;

import java.io.InputStream;

public class TestModelJsonReader extends BaseJsonReader<TestModel> {

    public TestModelJsonReader(InputStream inputStream, ObjectMapper objectMapper) {
        super(inputStream, objectMapper, TestModel.class);
    }
}
