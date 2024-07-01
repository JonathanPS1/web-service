package com.marketplace.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.marketplace.customer.model.customer;
import com.marketplace.customer.repository.customerRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/customer")
public class customerController {
    @Autowired
    private customerRepository cr;

    @GetMapping("")
    public @ResponseBody ResponseEntity<Object> getAllCustomers() {
        Iterable<customer> customers = cr.findAll();
        if (!customers.iterator().hasNext()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tidak ada data");
        } else {
            return ResponseEntity.ok(customers);
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getCustomer(@PathVariable Long id) {
        Optional<customer> customerOpt = cr.findById(id);
        if (customerOpt.isPresent()) {
            return ResponseEntity.ok(customerOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data tidak ditemukan");
        }
    }

    @PutMapping("/updateCustomer/{id}")
    public @ResponseBody ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody customer c) {
        if (cr.existsById(id)) {
            c.setId(id);
            cr.save(c);
            return ResponseEntity.status(HttpStatus.OK).body("Customer berhasil diperbarui");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        }
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public @ResponseBody ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        if (cr.existsById(id)) {
            cr.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Customer berhasil dihapus");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        }
    }

    @PostMapping("/addCustomer")
    public @ResponseBody ResponseEntity<Object> addCustomer(@RequestBody customer c) {
        // Validasi nomor telepon dan email
        if (isValidPhoneNumber(c.getNo_hp()) && isValidEmail(c.getEmail())) {
            // Generate ID baru
            Long newId = cr.count() + 1;
            c.setId(newId);
            cr.save(c);
            return ResponseEntity.status(HttpStatus.OK).body("Customer berhasil ditambahkan");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nomor telepon atau email tidak valid");
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
