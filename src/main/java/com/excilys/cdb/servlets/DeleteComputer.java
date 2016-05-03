package com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class DeleteComputer.
 */

public class DeleteComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        computerService = ComputerService.INSTANCE;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String selection = request.getParameter("selection");
        int nbDeleted = 0;
        if (selection != null && !selection.equals("")) {
            String[] selectionSplit = selection.split(",");
            nbDeleted = selectionSplit.length;
            long tmp;
            for (String idStr : selectionSplit) {
                tmp = Long.parseLong(idStr);
                computerService.deleteComputer(tmp);
            }

        }
        request.setAttribute("nbDeleted", nbDeleted);
        response.sendRedirect("home");
        // request.getRequestDispatcher("home").forward(request, response);
        // //FIXME reste sur /delete
    }

}
