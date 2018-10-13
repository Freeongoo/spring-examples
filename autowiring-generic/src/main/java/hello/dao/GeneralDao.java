package hello.dao;

public interface GeneralDao<T> {
    T get(Long id);
}
