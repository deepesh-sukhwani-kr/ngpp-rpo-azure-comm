package com.kroger.ngpp.concurrentworkerdispatcher.testutils.testmodel;

import com.kroger.ngpp.testutils.models.TestModel;
import lombok.AllArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
public class TestModelPersistence {

    private final String directoryName;

    public void persist(final List<TestModel> models) throws IOException {
        Files.createDirectories(Paths.get(directoryName));
        try (
                final OutputStream out = new FileOutputStream(
                        directoryName + "/" + models.get(0).getName() + ".txt");
                final PrintWriter writer = new PrintWriter(out)
        ) {
            models.forEach(writer::println);
            writer.flush();
        }
    }
}
