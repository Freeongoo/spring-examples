package hello.service;

import java.util.List;

public interface Service<T, ID> {

    T get(ID id);

    List<T> getAll();
}
