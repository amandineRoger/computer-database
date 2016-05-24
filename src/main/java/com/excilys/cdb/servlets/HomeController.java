package com.excilys.cdb.servlets;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.util.PageRequest;
import com.excilys.cdb.util.Sort;

@Controller
public class HomeController {
    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;
    @Autowired
    @Qualifier("computerMapper")
    private ComputerMapper computerMapper;

    @RequestMapping("/home")
    protected String homePage(
            @RequestParam(required = false) Map<String, String> params,
            Model model) {
        Page<Computer> computerPage;

        // Parsing request
        PageRequest pageRequest = new PageRequest(params);

        // get request results
        computerPage = computerService.getPage(pageRequest);

        // Mapping into DTO
        Page.Builder<ComputerDTO> dtoPageBuilder = new Page.Builder<ComputerDTO>(
                computerPage.getNbResults())
                        .pageNumber(computerPage.getPageNumber())
                        .limit(computerPage.getLimit());
        Page<ComputerDTO> dtoPage = dtoPageBuilder.build();
        dtoPage.setList(computerMapper.convertPageList(computerPage.getList()));

        computerPage = null;

        // Attach attributes to model
        String tmp = params.get("search");
        model.addAttribute("search", tmp == null ? "" : tmp);
        model.addAttribute("pageNumber", dtoPage.getPageNumber());
        model.addAttribute("limit", dtoPage.getLimit());
        Sort sort = pageRequest.getSorting();
        pageRequest = null;
        // FIXME to test
        model.addAttribute("sort", sort);
        model.addAttribute("page", dtoPage);

        model.addAttribute("order", sort.getField());
        model.addAttribute("asc", sort.getDirection());
        model.addAttribute("computersCount", dtoPage.getNbResults());
        model.addAttribute("nbPages", dtoPage.getNbPages());

        return "home";
    }
}
