package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;


@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {

    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException{
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nom = req.getParameter("last_name");
        String prenom = req.getParameter("first_name");
        String email = req.getParameter("email");
        LocalDate naissance = LocalDate.parse(req.getParameter("naissance"));

        try {
            clientService.create(new Client(1, prenom, nom, naissance,  email));
            req.setAttribute("clients", clientService.findAll());
            
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(req, resp);
    }
}
