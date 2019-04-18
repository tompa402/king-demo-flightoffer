package hr.kingict.novak.flightoffer.api;

import hr.kingict.novak.flightoffer.model.IATACode;
import hr.kingict.novak.flightoffer.service.IATACodeService;
import hr.kingict.novak.flightoffer.service.IATACodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/iata-code")
public class IATACodeController {

    private final IATACodeService codeService;

    @Autowired
    public IATACodeController(IATACodeService codeService) {
        this.codeService = codeService;
    }

    @RequestMapping
    public List<IATACode> getDataByInput(@RequestParam(value = "name") String name) {
        return codeService.getIataCodesByName(name);
    }

//    @RequestMapping("/all")
//    public List<IATACode> getAll(){
//        return codeService.getAll();
//    }

}
