package hello.facade;

import hello.service.BaseService;

/**
 * See changed Generics places
 *
 * @param <T>
 * @param <ID>
 * @param <DTO>
 */
public interface Facade<T, ID, DTO> extends BaseService<DTO, ID> {

}
