package hello.facade;

import hello.util.ObjectMapperUtils;
import hello.service.BaseService;

import java.util.Collection;
import java.util.List;

/**
 *
 * @param <T> source Entity
 * @param <ID> Entity's id
 * @param <DTO> DTO object to convert
 */
public abstract class AbstractFacade<T, ID, DTO> implements Facade<T, ID, DTO> {

    protected abstract Class<DTO> getDtoClass();
    protected abstract BaseService<T, ID> getService();

    protected DTO convertToDto(T e) {
        return ObjectMapperUtils.map(e, getDtoClass());
    }

    protected List<DTO> convertToDto(Collection<T> collection) {
        return ObjectMapperUtils.mapAll(collection, getDtoClass());
    }

    @Override
    public DTO getById(ID id) {
        return convertToDto(getService().getById(id));
    }

    @Override
    public List<DTO> getAll() {
        return convertToDto(getService().getAll());
    }
}
