package com.marketplace.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.marketplace.customer.model.customer;
import com.marketplace.customer.repository.customerRepository;

@RestController
@RequestMapping(path ="/customer")
public class customerController {
    @Autowired
    private customerRepository cr;

    @GetMapping("")
    public @ResponseBody Iterable<customer> getAllCustomers() {
        return cr.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody customer getCustomer(@PathVariable Long id) {
        return cr.findById(id).get();
    }

    @PostMapping("updateCustomer/{id}")
    public @ResponseBody boolean updateCustomer(@PathVariable Long id, @RequestBody customer c) {
        if (cr.existsById(id)) {
            c.setId(id);
            cr.save(c);
            return true;
        } else {
            return false;
        }
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public @ResponseBody boolean deleteCustomer(@PathVariable Long id) {
        if (cr.existsById(id)) {
            cr.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/addCustomer")
    public @ResponseBody boolean addCustomer(@RequestBody customer c) {
        // Generate ID baru
        Long newId = cr.count() + 1;
        c.setId(newId);

        // Validasi nomor telepon dan email
        if (isValidPhoneNumber(c.getNo_hp()) && isValidEmail(c.getEmail())) {
            cr.save(c);
            return true;
        } else {
            return false;
        }
    }

    // Method pembantu untuk validasi nomor telepon
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{12}");
    }
    
    // Method pembantu untuk validasi email
    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}
