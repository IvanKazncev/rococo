package guru.qa.rococo.utils;

import com.github.javafaker.Faker;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;



public class FakerData {

    static Faker faker = new Faker();

    public static String getLogin() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password(3,5,true);
    }

    @NotNull
    @Contract("_ -> new")
    public static String randomString(int length) {
       return RandomStringUtils.random(length,true,false);
    }
}
