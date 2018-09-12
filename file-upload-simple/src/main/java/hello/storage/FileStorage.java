package hello.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    String UPLOAD_DIR = "/home/vasya/test";

    void save(MultipartFile file);
}