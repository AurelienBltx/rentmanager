package com.epf.rentmanager.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        PrintWriter out = resp.getWriter();

        out.println("<HTML>\n<BODY>\n" +
                "<H1>Recapitulatif des informations</H1>\n" +
                "<UL>\n" +
                "  <LI>Nom: "
                + req.getParameter("Nom") + "\n" +
                "  <LI>Prenom: "
                + req.getParameter("Prenom") + "\n" +
                "  <LI>Age: "
                + req.getParameter("Age") + "\n" +
                "</UL>\n" +
                "</BODY></HTML>");
    }
}
