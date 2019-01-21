package hello.controller.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Good;
import hello.service.jsonIdentityInfo.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodController {

    private final GoodService service;

    @Autowired
    public GoodController(GoodService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Good> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Good get(@PathVariable Long id) {
        return service.get(id);
    }
}
