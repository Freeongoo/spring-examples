package hello.util;

import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

public final class StringDelimiterUtils {

    private StringDelimiterUtils() { }

    public static Optional<String> getSubString(String str, String delimiter, Integer allUntilIndexOfDelimiter) {
        List<String> strings = Arrays.asList(str.split("\\" + delimiter));
        if (isEmpty(strings)) {
            return Optional.empty();
        }

        if (strings.size() <= allUntilIndexOfDelimiter + 1) {
            return Optional.empty();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= allUntilIndexOfDelimiter; i++) {
            if (!isEmpty(stringBuilder.toString())) {
                stringBuilder.append(delimiter);
            }
            stringBuilder.append(strings.get(i));
        }

        return Optional.of(stringBuilder.toString());
    }
}
