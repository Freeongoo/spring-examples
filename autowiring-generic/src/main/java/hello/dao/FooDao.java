package hello.dao;

import hello.model.Foo;
import org.springframework.stereotype.Component;

@Component
public class FooDao implements GeneralDao<Foo> {
    @Override
    public Foo get(Long id) {
        Foo foo = new Foo(); // hardcode
        foo.setId(id);
        return foo;
    }
}
