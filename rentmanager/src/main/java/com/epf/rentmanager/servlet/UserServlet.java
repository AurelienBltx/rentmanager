package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private ClientService clientService = ClientService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            req.setAttribute("clients", this.clientService.findAll());
        }
        catch (ServiceException e){
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(req, resp);
    }

}
