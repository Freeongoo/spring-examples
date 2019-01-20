package hello.controller.jsonView;

import com.fasterxml.jackson.annotation.JsonView;
import hello.entity.jsonView.Company;
import hello.service.jsonView.CompanyService;
import hello.view.CompanyViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService service;

    @Autowired
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @JsonView({CompanyViews.List.class})
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Company> getAll() {
        return service.getAll();
    }

    @JsonView({CompanyViews.GetOne.class})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Company get(@PathVariable Long id) {
        return service.get(id);
    }
}
