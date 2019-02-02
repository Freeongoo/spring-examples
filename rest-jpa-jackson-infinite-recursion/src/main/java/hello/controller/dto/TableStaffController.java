package hello.controller.dto;

import hello.entity.dto.TableStaff;
import hello.entity.dto.TableStaffDto;
import hello.service.Service;
import hello.service.dto.TableStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TableStaffController.PATH)
public class TableStaffController extends AbstractDtoRestController<TableStaff, Long, TableStaffDto> {

    public final static String PATH = "/api/table-staffs";

    private final TableStaffService service;

    @Autowired
    public TableStaffController(TableStaffService service) {
        this.service = service;
    }

    @Override
    protected Service<TableStaff, Long> getService() {
        return service;
    }

    @Override
    protected Class<TableStaffDto> getDtoClass() {
        return TableStaffDto.class;
    }
}
