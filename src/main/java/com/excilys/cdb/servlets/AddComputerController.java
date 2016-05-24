package com.excilys.cdb.servlets;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.util.Errors;
import com.excilys.cdb.util.UtilDate;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.validators.DTOValidator;

@Controller
@RequestMapping("/newComputer")
public class AddComputerController {
    private List<Company> companies;
    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    @PostConstruct
    public void initCompanies() {
        companies = companyService.getAllCompanies();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(Model model) {
        model.addAttribute("companiesList", companies);
        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(
            @RequestParam(required = true) Map<String, String> requestMap,
            Model model) {
        String response = "addComputer";

        // Construct DTO from form fields
        ComputerDTO dto = new ComputerDTO();
        dto.setName(requestMap.get("computerName"));
        dto.setIntroduced(requestMap.get("introduced"));
        dto.setDiscontinued(requestMap.get("discontinued"));

        // DTO validation
        Errors errors = new Errors();
        DTOValidator dtoValidator = new DTOValidator(errors);
        dtoValidator.validate(dto);
        Computer computer = null;

        // Computer creation
        if (errors.isEmpty()) {
            Computer.Builder builder = new Computer.Builder(dto.getName());
            String tmp = dto.getIntroduced();
            if (tmp != null && !tmp.isEmpty()) {
                builder.introduced(UtilDate.stringToLocalDate(tmp));
            }
            tmp = dto.getDiscontinued();
            if (tmp != null && !tmp.isEmpty()) {
                builder.discontinued(UtilDate.stringToLocalDate(tmp));
            }
            // TODO check in DTOValidation
            long id = Long.parseLong(requestMap.get("companyId"));
            if (id > 0) {
                Company company = companyService.getCompanyById(id);
                builder.company(company);
            }

            computer = builder.build();
            ComputerValidator computerValidator = new ComputerValidator(errors);
            computerValidator.validate(computer);
        } else {
            System.out.println(
                    "Errors is not empty !! errors : " + errors.toString()); // FIXME
                                                                             // tmp
                                                                             // message
        }

        if (computer != null && errors.isEmpty()) {
            computerService.createComputer(computer);
            model.addAttribute("companiesList", companies);
            model.addAttribute("postMessage", "true");
            model.addAttribute("messageLevel", "success");
            model.addAttribute("messageHeader", "Computer added");
            model.addAttribute("messageBody", "The computer \""
                    + computer.getName() + "\" has been successfully added.");

        } else if (!errors.isEmpty()) {
            System.err.println(
                    "Errors is not empty !! errors : " + errors.toString()); // FIXME
                                                                             // tmp
                                                                             // message
            model.addAttribute("errorsMap", errors);
            return doGet(model);
        } else {
            response = "400";
        }
        return response;
    }
}
