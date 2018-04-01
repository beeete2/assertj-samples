package beeete2.assertj.commons;

import beeete2.assertj.entities.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private Utils() {
    }

    public static void writeToString(Path path, String content) throws IOException {
        Files.write(path, content.getBytes());
    }

    public static List<Person> extractByGender(List<Person> persons, String gender) {
        if (gender == null || "".equals(gender)) {
            throw new IllegalArgumentException("gender must be not null or empty.");
        }

        if (!(gender.equals("male") || gender.equals("female"))) {
            throw new IllegalArgumentException("gender can be choice only male or female.");
        }

        return persons.stream()
                .filter(s -> s.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public static List<Person> extractGteByAge(List<Person> persons, int age) {
        return persons.stream()
                .filter(s -> s.getAge() >= age)
                .collect(Collectors.toList());
    }


}
