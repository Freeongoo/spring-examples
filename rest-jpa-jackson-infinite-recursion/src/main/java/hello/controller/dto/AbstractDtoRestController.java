package hello.controller.dto;

import hello.service.Service;
import hello.util.ObjectMapperUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

public abstract class AbstractDtoRestController<T, ID, DTO> {

    protected abstract Service<T, ID> getService();
    protected abstract Class<DTO> getDtoClass();

    @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON_VALUE)
    public Collection<DTO> getAll() {
        Collection<T> collection = getService().getAll();
        return ObjectMapperUtils.mapAll(collection, getDtoClass());
    }

    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public DTO getOne(@PathVariable ID id) {
        T item = getService().getById(id);
        return ObjectMapperUtils.map(item, getDtoClass());
    }
}
