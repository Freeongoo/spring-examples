package hello.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "/home/vasya/test/"; // set your dir for upload files

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}