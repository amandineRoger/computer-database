package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.util.Errors;
import com.excilys.cdb.util.UtilDate;

import validators.ComputerValidator;
import validators.DTOValidator;

/**
 * Servlet implementation class AddComputer.
 */
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<Company> companies;
    private CompanyService companyService;
    private ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // companyService = CompanyService.getInstance();
        companyService = CompanyService.INSTANCE;
        computerService = ComputerService.INSTANCE;
        companies = companyService.getAllCompanies();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("companiesList", companies);

        request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ComputerDTO dto = new ComputerDTO();
        dto.setName(request.getParameter("computerName"));
        dto.setIntroduced(request.getParameter("introduced"));
        dto.setDiscontinued(request.getParameter("discontinued"));
        // TODO possible d'avoir company invalide ? (liste déroulante)

        Errors errors = new Errors();
        DTOValidator dtoValidator = new DTOValidator(errors);
        dtoValidator.validate(dto);
        Computer computer = null;

        if (errors.isEmpty()) {
            Computer.Builder builder = new Computer.Builder(dto.getName());
            String tmp = request.getParameter("introduced");
            if (!tmp.equals("")) {
                builder.introduced(UtilDate.stringToLocalDate(tmp));
            }
            tmp = request.getParameter("discontinued");
            if (!tmp.equals("")) {
                builder.discontinued(UtilDate.stringToLocalDate(tmp));
            }
            // TODO check in DTOValidation
            long id = Long.parseLong(request.getParameter("companyId"));
            if (id > 0) {
                Company company = companyService.getCompanyById(id);
                builder.company(company);
            }

            computer = builder.build();
            ComputerValidator computerValidator = new ComputerValidator(errors);
            computerValidator.validate(computer);
        }

        if (computer != null && !errors.isEmpty()) {
            computerService.createComputer(computer);
            request.setAttribute("companiesList", companies);
            request.setAttribute("postMessage", "true");
            request.setAttribute("messageLevel", "success");
            request.setAttribute("messageHeader", "Computer added");
            request.setAttribute("messageBody", "The computer \""
                    + computer.getName() + "\" has been successfully added.");

            request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
                    .forward(request, response);
        } else if (!errors.isEmpty()) {
            request.setAttribute("errorsMap", errors);
            doGet(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/500.html")
                    .forward(request, response);
        }
    }

}
