package com.codegym.customermanager.controller;

import com.codegym.customermanager.model.Customer;
import com.codegym.customermanager.service.CustomerService;
import com.codegym.customermanager.service.CustomerServiceMysql;
import com.codegym.customermanager.service.CustomerTypeMySQL;
import com.codegym.customermanager.service.CustomerTypeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;
    private CustomerTypeService customerTypeService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceMysql();
        customerTypeService = new CustomerTypeMySQL();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showFormCreate(req, resp);
                break;
            case "edit":
                showFormEdit(req, resp);
                break;
            case "delete":
                showFormDelete(req, resp);
                break;
            default:
                showList(req, resp);
        }


    }

    private void showFormDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long idCustomer = Long.parseLong(req.getParameter("id"));
        Customer c = customerService.findById(idCustomer);

        req.setAttribute("customer", c);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customer/delete.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long idCustomer = Long.parseLong(req.getParameter("id"));
        Customer c = customerService.findById(idCustomer);

        req.setAttribute("customer", c);
        req.setAttribute("customertypes", customerTypeService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customer/edit.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createCustomer(req, resp);
                break;
            case "edit":
                editCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
            default:
        }
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long idCustomer = Long.parseLong(req.getParameter("id"));

        customerService.remove(idCustomer);
        resp.sendRedirect("/customers");
    }

    private void editCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("txtName");
        String email = req.getParameter("txtEmail");
        String address = req.getParameter("txtAddress");
        String image = req.getParameter("txtImage");

        long idCustomer = Long.parseLong(req.getParameter("id"));

        long idCustomerType = Long.parseLong(req.getParameter("sCustomerTypes"));

        Customer customer = customerService.findById(idCustomer);
        customer.setEmail(email);
        customer.setName(name);
        customer.setAddress(address);
        customer.setImage(image);
        customer.setCustomerType(idCustomerType);

        customerService.update(idCustomer, customer);

//        showList(req, resp);


        resp.sendRedirect("/customers");
    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("txtName");
        String email = req.getParameter("txtEmail");
        String address = req.getParameter("txtAddress");
        String image = req.getParameter("txtImage");
        long idCustomerType = Long.parseLong(req.getParameter("sCustomerTypes"));
//                public Customer(long id, String name, String email,
//                    String address, String image, Date createAt)
        Customer customer = new Customer(
                customerService.maxId() + 1, name, email, address, image, new Date(), idCustomerType);
        customerService.save(customer);

        req.setAttribute("message", "Thêm thành công");
        req.setAttribute("customertypes", customerTypeService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customer/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerService.findAll();
        req.setAttribute("customers", customers);
        req.setAttribute("customertypes", customerTypeService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customer/customers.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("customertypes", customerTypeService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customer/create.jsp");
        requestDispatcher.forward(req, resp);
    }
}
