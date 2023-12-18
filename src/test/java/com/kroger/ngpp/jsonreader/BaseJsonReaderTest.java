package com.kroger.ngpp.jsonreader;

import com.kroger.ngpp.jsonreader.testutils.testmodel.TestModelJsonReader;
import com.kroger.ngpp.testutils.filewriters.ModelToJsonFileWriter;
import com.kroger.ngpp.testutils.models.TestModel;
import com.kroger.ngpp.testutils.models.TestModelFactory;
import com.kroger.ngpp.testutils.objectmappers.ObjectMappersFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseJsonReaderTest {

    private final static String JSON_READER_STREAM_SOURCE_PATH = String.format(
            "src/test/JsonReaderStreamSource-%s.txt", Instant.now());
    private final static int    SOURCE_ELEMENTS_SIZE           = 152;

    @BeforeAll
    static void populateSourceFile() throws IOException {
        new ModelToJsonFileWriter<TestModel>(ObjectMappersFactory.defaultObjectMapper())
                .writeRandomJsonsTo(JSON_READER_STREAM_SOURCE_PATH,
                        TestModelFactory.createRandomModels(SOURCE_ELEMENTS_SIZE));
    }

    @Test
    public void whenValidInputStream_thenReadAllElements() throws IOException {
        try (InputStream input = new FileInputStream(JSON_READER_STREAM_SOURCE_PATH)) {
            BaseJsonReader<TestModel> reader = new TestModelJsonReader(input,
                    ObjectMappersFactory.defaultObjectMapper());

            int i = 0;
            for (; reader.hasNext(); i++) {
                reader.next();
            }
            assertEquals(SOURCE_ELEMENTS_SIZE, i,
                    "Reader didn't create all elements from the input");
        }
    }

    @Test
    public void whenInvalidToken_thenTrowException() throws IOException {
        try (InputStream input = new ByteArrayInputStream("invalidToken".getBytes())) {
            final BaseJsonReader<TestModel> reader = new TestModelJsonReader(input,
                    ObjectMappersFactory.defaultObjectMapper());
            assertThrows(JsonReaderException.class, reader::next);
        }
    }

    @Test
    public void whenObjectMapperCantParseObject_thenThrowException() throws IOException {
        try (InputStream input = new ByteArrayInputStream(
                "{ \"nonExistentField\": 1 }".getBytes())) {
            final BaseJsonReader<TestModel> reader = new TestModelJsonReader(input,
                    ObjectMappersFactory.failOnUnknownProperties());
            assertThrows(JsonReaderException.class, reader::next);
        }
    }

    @Test
    public void testClose() throws IOException {
        try (InputStream input = new FileInputStream(JSON_READER_STREAM_SOURCE_PATH)) {
            BaseJsonReader<TestModel> reader = new TestModelJsonReader(input,
                    ObjectMappersFactory.defaultObjectMapper());
             reader.close();
        }
    }

    @Test
    public void testCloseWithNullInputReader() {
        BaseJsonReader<TestModel> reader;
        try {
            reader  = new TestModelJsonReader(null,
                    ObjectMappersFactory.defaultObjectMapper());
            reader.close();
        }
        catch (IOException e) {}
    }

    @AfterAll
    static void deleteSourceFile() {
        new File(JSON_READER_STREAM_SOURCE_PATH).delete();
    }
}
