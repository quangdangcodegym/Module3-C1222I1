package com.codegym.customermanager.service;

import com.codegym.customermanager.model.Customer;

import java.util.*;

public class CustomerServiceImpl implements CustomerService{

    private Map<Long, Customer> customers;

    public CustomerServiceImpl() {
        customers = new HashMap<>();
        //(long id, String name, String email,
                ///String address, String image, Date createAt) {
        customers.put(1L, new Customer(1L, "Quang Dang", "quang.dang@codegym.vn",
                "28 NTP", null, new Date()));
        customers.put(2L, new Customer(2L, "Quang Dang1", "quang1.dang@codegym.vn",
                "28 NTP", null, new Date()));
        customers.put(3L, new Customer(3L, "Quang Dang2", "quang2.dang@codegym.vn",
                "28 NTP", null, new Date()));
        customers.put(4L, new Customer(4L, "Quang Dang3", "quang3.dang@codegym.vn",
                "28 NTP", null, new Date()));
    }

    @Override
    public List<Customer> findAll() {
        return  new ArrayList<>(customers.values());
    }

    @Override
    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public Customer findById(long id) {
        return customers.get(id);
    }

    @Override
    public void update(long id, Customer customer) {
        customers.put(id, customer);
    }

    @Override
    public void remove(long id) {
        customers.remove(id);
    }

    @Override
    public long maxId() {
        List<Customer> customers = findAll();
        customers.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                if (o1.getId() > o2.getId()) {
                    return 1;
                } else if (o1.getId() == o2.getId()) {
                    return 0;
                }else{
                    return -1;
                }
            }
        });

        Customer lastCustomer = customers.get(customers.size() - 1);
        return lastCustomer.getId();
    }
}
