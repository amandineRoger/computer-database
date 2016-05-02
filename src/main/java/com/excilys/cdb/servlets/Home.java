package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class Home.
 */

public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ComputerService computerService;
    private static ComputerMapper computerMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        computerService = ComputerService.INSTANCE;
        computerMapper = ComputerMapper.INSTANCE;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Page<Computer> page;
        int pageNumber = 0;
        int limit = 10;
        Map<String, String[]> parameters = request.getParameterMap();

        if (parameters.containsKey("limit")) {
            limit = Integer.parseInt(parameters.get("limit")[0]);
        }
        if (parameters.containsKey("page")) {
            pageNumber = Integer.parseInt(parameters.get("page")[0]);
        }
        // page construction
        page = computerService.getComputerList(pageNumber, limit);

        // Mapping into DTO
        Page.Builder<ComputerDTO> dtoPageBuilder = new Page.Builder<ComputerDTO>(
                page.getNbResults()).pageNumber(pageNumber).limit(limit);
        Page<ComputerDTO> dtoPage = dtoPageBuilder.build();
        dtoPage.setList(computerMapper.convertPageList(page.getList()));

        // attach attributes to request
        request.setAttribute("computersCount", computerService.getNbItem());
        request.setAttribute("page", dtoPage);
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
