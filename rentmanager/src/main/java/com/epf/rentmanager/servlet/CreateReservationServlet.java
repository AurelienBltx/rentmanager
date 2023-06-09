package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/rents/create")
public class CreateReservationServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException{
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            req.setAttribute("clients", clientService.findAll());
            req.setAttribute("vehicles", vehicleService.findAll());
        }
        catch (ServiceException e){
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long vehicle_id = Long.parseLong(req.getParameter("vehicle_id"));
        long client_id = Long.parseLong(req.getParameter("client_id"));
        LocalDate debut = LocalDate.parse(req.getParameter("debut"));
        LocalDate fin = LocalDate.parse(req.getParameter("fin"));


        try {
            reservationService.create(new Reservation(1, client_id, vehicle_id, debut, fin));
            List<Reservation> reservations = this.reservationService.findAll();
            Map<Long, Client> users = new HashMap<Long, Client>();
            Map<Long, Vehicle> cars = new HashMap<Long, Vehicle>();
            for (Reservation res : reservations) {
                users.put(res.getId(), reservationService.findClientResaById(res.getId()));
                cars.put(res.getId(), reservationService.findVehicleResaById(res.getId()));
            }
            req.setAttribute("reservations", reservations);
            req.setAttribute("users", users);
            req.setAttribute("cars", cars);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(req, resp);
    }
}
