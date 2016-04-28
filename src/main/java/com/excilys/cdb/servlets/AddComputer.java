package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.util.UtilDate;

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
        companyService = CompanyService.getInstance();
        computerService = ComputerService.getInstance();
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

        Computer.Builder builder = new Computer.Builder(
                request.getParameter("computerName"));
        String tmp = request.getParameter("introduced");
        if (!tmp.equals("")) {
            builder.introduced(UtilDate.StringToLocalDate(tmp));
        }
        tmp = request.getParameter("discontinued");
        if (!tmp.equals("")) {
            builder.discontinued(UtilDate.StringToLocalDate(tmp));
        }
        long id = Long.parseLong(request.getParameter("companyId"));
        if (id > 0) {
            Company company = companyService.getCompanyById(id);
            builder.company(company);
        }

        Computer computer = computerService.createComputer(builder.build());
        if (computer != null) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.getRequestDispatcher("/WEB-INF/views/500.html")
                    .forward(request, response);
        }
    }

}
