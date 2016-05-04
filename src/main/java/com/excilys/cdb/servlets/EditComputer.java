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
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.util.UtilDate;

/**
 * Servlet implementation class EditComputer.
 */

public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<Company> companies;
    private CompanyService companyService;
    private ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
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

        // get ID of wanted computer
        String dtoID = request.getParameter("id");
        long id = Long.parseLong(dtoID);

        // get computer by its ID
        Computer computer = computerService.getComputerDetail(id);
        ComputerDTO dto = ComputerMapper.INSTANCE.computerToDTO(computer);

        // Set attributes to request
        request.setAttribute("computer", dto);
        request.setAttribute("companiesList", companies);

        request.getRequestDispatcher("WEB-INF/views/editComputer.jsp")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("computerId"));
        Computer.Builder builder = new Computer.Builder(
                request.getParameter("computerName"));
        String tmp = request.getParameter("introduced");
        if (!tmp.equals("")) {
            builder.introduced(UtilDate.stringToLocalDate(tmp));
        }
        tmp = request.getParameter("discontinued");
        if (!tmp.equals("")) {
            builder.discontinued(UtilDate.stringToLocalDate(tmp));
        }
        long compID = Long.parseLong(request.getParameter("companyId"));
        if (compID > 0) {
            Company company = companyService.getCompanyById(compID);
            builder.company(company);
        }

        Computer computer = builder.build();

        if (computer != null && computerService.validateComputer(computer)) {
            computer.setId(id);
            computerService.updateComputer(computer);

            response.sendRedirect("home");
        } else {
            request.getRequestDispatcher("/WEB-INF/views/500.html")
                    .forward(request, response);
        }
    }

}