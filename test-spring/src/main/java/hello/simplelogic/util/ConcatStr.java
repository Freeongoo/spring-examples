package hello.simplelogic.util;

import org.springframework.stereotype.Component;

@Component
public class ConcatStr {
    public String get(String ...strings) {
        StringBuilder builder = new StringBuilder();
        for (String arg : strings) {
            builder.append(arg);
        }
        return builder.toString();  //Outputs the entire contents of the StringBuilder.
    }
}
