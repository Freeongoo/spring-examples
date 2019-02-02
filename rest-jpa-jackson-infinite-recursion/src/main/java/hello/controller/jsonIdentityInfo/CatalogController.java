package hello.controller.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Catalog;
import hello.service.jsonIdentityInfo.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CatalogController.PATH)
public class CatalogController {

    public final static String PATH = "/api/catalogs";

    private final CatalogService service;

    @Autowired
    public CatalogController(CatalogService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Catalog> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Catalog get(@PathVariable Long id) {
        return service.getById(id);
    }
}
