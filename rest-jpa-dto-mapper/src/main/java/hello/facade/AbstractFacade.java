package hello.facade;

import hello.util.ObjectMapperUtils;
import hello.service.BaseService;

import java.util.Collection;
import java.util.List;

public abstract class AbstractFacade<D, E> implements Facade<D, E> {

    protected abstract Class<D> getDtoClass();
    protected abstract BaseService<E> getService();

    protected D convertToDto(E e) {
        return ObjectMapperUtils.map(e, getDtoClass());
    }

    protected List<D> convertToDto(Collection<E> collection) {
        return ObjectMapperUtils.mapAll(collection, getDtoClass());
    }

    @Override
    public D get(Long id) {
        return convertToDto(getService().get(id));
    }

    @Override
    public List<D> getAll() {
        return convertToDto(getService().getAll());
    }
}
