package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class Home.
 */

public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ComputerService computerService;
    private Page<Computer> page;
    private static final String COUNT = "computersCount";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        computerService = ComputerService.getInstance();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameters = request.getParameterMap();

        if (parameters.isEmpty()) {
            page = computerService.getComputerList();
        } else if (parameters.containsKey("page")) {
            page.setPageNumber(Integer.parseInt(parameters.get("page")[0]));
            if (parameters.containsKey("prev")) {
                if ((parameters.get("prev")[0]).equals("true")) {
                    page = computerService.getPreviousPage();
                } else {
                    page = computerService.getNextPage();
                }
            } else {
                computerService.getPageContent();
                page = computerService.getCurrentPage();
            }

        }

        request.setAttribute(COUNT, computerService.getCount());
        request.setAttribute("page", page);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request,
                response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
