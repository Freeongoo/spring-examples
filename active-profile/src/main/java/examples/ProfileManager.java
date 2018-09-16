package examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileManager {
    @Autowired
    Environment environment;

    public void getActiveProfiles() {
        if (environment.getActiveProfiles().length == 0) {
            for (final String profileName : environment.getDefaultProfiles()) {
                System.out.println("Default profile - " + profileName);
            }
        } else {
            for (final String profileName : environment.getActiveProfiles()) {
                System.out.println("Currently active profile - " + profileName);
            }
        }
    }
}