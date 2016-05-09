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
import com.excilys.cdb.util.PageRequest;
import com.excilys.cdb.util.Sort;

/**
 * Servlet implementation class Home.
 */

public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ComputerService computerService;
    private static ComputerMapper computerMapper;
    private int limit;
    private int pageNumber;

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

        // parsing request
        PageRequest pageRequest = new PageRequest(request);

        // get request results
        page = computerService.getPage(pageRequest);
        limit = page.getLimit();
        pageNumber = page.getPageNumber();
        // Mapping into DTO
        Page.Builder<ComputerDTO> dtoPageBuilder = new Page.Builder<ComputerDTO>(
                page.getNbResults()).pageNumber(pageNumber).limit(limit);
        Page<ComputerDTO> dtoPage = dtoPageBuilder.build();
        dtoPage.setList(computerMapper.convertPageList(page.getList()));

        // attach attributes to request
        String paramSearch = pageRequest.getSearch();
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("limit", limit);
        request.setAttribute("search", paramSearch);
        Sort sort = pageRequest.getSorting();
        request.setAttribute("order", sort.getField());
        request.setAttribute("asc", sort.getDirection()); // TODO check field to
                                                          // decide direction
        request.setAttribute("computersCount", page.getNbResults());
        request.setAttribute("page", dtoPage);
        // TODO tmp
        request.setAttribute("nbPages", page.getNbPages());
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
