package hello.service;

import java.util.List;

public interface BaseService<E> {

    E get(Long id);

    List<E> getAll();
}
