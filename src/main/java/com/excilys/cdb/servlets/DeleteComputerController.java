package com.excilys.cdb.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.services.ComputerService;

@Controller
public class DeleteComputerController {
    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    @RequestMapping("/delete")
    protected String doPost(
            @RequestParam(required = true, value = "selection") String selectionParam,
            Model model) {
        int nbDeleted = 0;
        if (selectionParam != null && !selectionParam.equals("")) {
            String[] selectionSplit = selectionParam.split(",");
            nbDeleted = selectionSplit.length;
            long tmp;
            for (String idStr : selectionSplit) {
                tmp = Long.parseLong(idStr);
                computerService.deleteComputer(tmp);
            }

        }
        model.addAttribute("nbDeleted", nbDeleted);
        return "redirect:home";
    }

}
