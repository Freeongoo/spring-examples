package hello.service;

public interface GeneralService<T> {
    T get(Long id);
}
