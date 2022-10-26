package com.kroger.ngpp.testutils.models;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestModelFactory {

    final static Random rand = new Random();

    public static List<TestModel> createRandomModels(final int size) {
        return IntStream.range(0, size)
                .mapToObj(Integer::toString)
                .map(TestModelFactory::createRandomModel)
                .collect(Collectors.toList());
    }

    public static TestModel createRandomModel(String id) {
        return TestModel.builder()
                .name(id)
                .age(randomInt())
                .occupation(
                        TestModel.Occupation.builder()
                                .description(randomString())
                                .salary(randomDouble())
                                .localDate(LocalDate.now())
                                .build()
                ).build();
    }

    private static String randomString() {
        return RandomStringUtils.random(10, true, false);
    }

    private static Integer randomInt() {
        return rand.nextInt();
    }

    private static Double randomDouble() {
        return rand.nextDouble();
    }
}
