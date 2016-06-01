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
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.util.UtilDate;

@Controller
@RequestMapping("/edit")
public class EditComputerController {
    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    private List<Company> companies;

    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;
    @Autowired
    @Qualifier("computerMapper")
    private ComputerMapper computerMapper;

    @PostConstruct
    public void initCompanies() {
        companies = companyService.getAllCompanies();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(
            @RequestParam(required = true, value = "id") String dtoID,
            Model model) {
        long id = Long.parseLong(dtoID);

        // get computer by its ID
        Computer computer = computerService.getComputerDetail(id);
        ComputerDTO dto = computerMapper.computerToDTO(computer);

        // Set attributes to model
        model.addAttribute("computer", dto);
        model.addAttribute("companiesList", companies);

        return "editComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(
            @RequestParam(required = true) Map<String, String> requestMap,
            Model model) {
        String response;
        long id = Long.parseLong(requestMap.get("computerId"));
        Computer.Builder builder = new Computer.Builder(
                requestMap.get("computerName"));
        String tmp = requestMap.get("introduced");
        if (tmp != null && !tmp.isEmpty()) {
            builder.introduced(UtilDate.stringToLocalDate(tmp));
        }
        tmp = requestMap.get("discontinued");
        if (tmp != null && !tmp.isEmpty()) {
            builder.discontinued(UtilDate.stringToLocalDate(tmp));
        }
        long compID = Long.parseLong(requestMap.get("companyId"));
        if (compID > 0) {
            Company company = companyService.getCompanyById(compID);
            builder.company(company);
        }

        Computer computer = builder.build();

        if (computer != null) {
            computer.setId(id);
            computerService.updateComputer(computer);
            response = "redirect:/home";
        } else {
            response = "500";
        }

        return response;
    }
}
