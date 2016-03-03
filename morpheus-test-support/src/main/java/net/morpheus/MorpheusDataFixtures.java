package net.morpheus;

import java.util.List;
import java.util.Random;

public class MorpheusDataFixtures {

    public static String someString() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static int someNumber() {
        Random generator = new Random();
        return generator.nextInt(10) + 1;
    }

    public static <T> T pickOneOf(List<T> choices) {
        Random random = new Random();
        return choices.get(random.nextInt(choices.size()));
    }
}
