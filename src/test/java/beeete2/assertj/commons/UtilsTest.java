package beeete2.assertj.commons;

import beeete2.assertj.entities.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class UtilsTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private List<Person> persons;

    @Before
    public void setUp() {
        persons = Arrays.asList(
                new Person("user1", 18, "male"),
                new Person("user2", 21, "female"),
                new Person("user3", 35, "male"),
                new Person("user4", 35, "female"),
                new Person("user5", 50, "male")
        );
    }


    @Test
    public void writeToString() throws Exception {
        File file = temporaryFolder.newFile("test.txt");
        Path actual = file.toPath();
        Utils.writeToString(actual, "Hello World.");

        assertThat(actual).exists()
                .isRegularFile();
        assertThat(actual.toFile())
                .hasContent("Hello World.");
    }

    @Test
    public void extractByGender() {
        List<Person> actual = Utils.extractByGender(persons, "male");
        assertThat(actual).extracting("name")
                .hasSize(3)
                .containsExactly("user1", "user3", "user5");

        // java8 style
        actual = Utils.extractByGender(persons, "male");
        assertThat(actual).extracting(Person::getName)
                .hasSize(3)
                .containsExactly("user1", "user3", "user5");
    }

    @Test
    public void extractByGenderThrownExceptions() {
        // @formatter:off
        assertThatThrownBy(() -> { Utils.extractByGender(persons, null); })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("gender must be not null or empty.");

        assertThatThrownBy(() -> { Utils.extractByGender(persons, ""); })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("gender must be not null or empty.");

        assertThatThrownBy(() -> { Utils.extractByGender(persons, "root"); })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("gender can be choice only male or female.");
        // @formatter:on
    }

    @Test
    public void extractGteByAge() {
        List<Person> actual = Utils.extractGteByAge(persons, 35);
        assertThat(actual).extracting("name", "age")
                .hasSize(3)
                .containsExactly(
                        tuple("user3", 35),
                        tuple("user4", 35),
                        tuple("user5", 50));
    }


}