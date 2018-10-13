package hello.dao;

import hello.model.Bar;
import org.springframework.stereotype.Component;

@Component
public class BarDao implements GeneralDao<Bar> {
    @Override
    public Bar get(Long id) {
        Bar bar = new Bar(); // hardcode
        bar.setId(id);
        return bar;
    }
}
