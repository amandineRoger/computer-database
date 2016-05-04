package com.excilys.cdb.servlets;

import java.io.IOException;

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
    private int limit;
    private int pageNumber;
    private boolean asc;

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

        // get request parameters
        String paramLimit = request.getParameter("limit");
        String paramPage = request.getParameter("page");
        String paramSearch = request.getParameter("search");
        String paramOrder = request.getParameter("order");
        String paramAsc = request.getParameter("asc");

        if (paramLimit != null && !paramLimit.equals("")) {
            limit = Integer.parseInt(paramLimit);
        } else {
            limit = 10;
        }

        if (paramPage != null && !paramPage.equals("")) {
            pageNumber = Integer.parseInt(paramPage);
        } else {
            pageNumber = 0;
        }

        if (paramSearch == null) {
            paramSearch = "";
        }
        if (paramOrder == null) {
            paramOrder = "";
        }
        if (paramAsc == null || paramAsc.equals("true")) {
            page = computerService.searchByName(paramSearch, pageNumber * limit,
                    limit, paramOrder, true);
            asc = false;
        } else {
            page = computerService.searchByName(paramSearch, pageNumber * limit,
                    limit, paramOrder, false);
            asc = true;
        }

        // page construction
        // page = computerService.getComputerList(pageNumber, limit);

        // Mapping into DTO
        Page.Builder<ComputerDTO> dtoPageBuilder = new Page.Builder<ComputerDTO>(
                page.getNbResults()).pageNumber(pageNumber).limit(limit);
        Page<ComputerDTO> dtoPage = dtoPageBuilder.build();
        dtoPage.setList(computerMapper.convertPageList(page.getList()));

        // attach attributes to request
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("limit", limit);
        request.setAttribute("search", paramSearch);
        request.setAttribute("order", paramOrder);
        request.setAttribute("asc", asc);
        request.setAttribute("computersCount",
                computerService.getSearchedCount(paramSearch));
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
