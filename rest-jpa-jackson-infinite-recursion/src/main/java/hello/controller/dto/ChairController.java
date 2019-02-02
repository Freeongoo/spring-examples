package hello.controller.dto;

import hello.entity.dto.Chair;
import hello.entity.dto.ChairDto;
import hello.service.Service;
import hello.service.dto.ChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChairController.PATH)
public class ChairController extends AbstractDtoRestController<Chair, Long, ChairDto> {

    public final static String PATH = "/api/chairs";

    private final ChairService service;

    @Autowired
    public ChairController(ChairService service) {
        this.service = service;
    }

    @Override
    protected Service<Chair, Long> getService() {
        return service;
    }

    @Override
    protected Class<ChairDto> getDtoClass() {
        return ChairDto.class;
    }
}
