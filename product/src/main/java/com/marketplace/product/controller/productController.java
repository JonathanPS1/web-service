package com.marketplace.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marketplace.product.model.product;
import com.marketplace.product.repository.productRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping(path = "/product")
public class productController {
    @Autowired
    private productRepository pr;
    
    //get produk
    @GetMapping("")
    public List<product> getAllProducts() {
        return pr.findAll();
    }

    //get produk by id
    @GetMapping("/{id}")
    public ResponseEntity<product> getProductById(@PathVariable Long id) {
        Optional<product> product = pr.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //post produk
    @PostMapping("/add")
    public product createProduct(@RequestBody product product) {
        return pr.save(product);
    }

    //update produk
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<product> updateProduct(@PathVariable Long id, @RequestBody product productDetails) {
        Optional<product> product = pr.findById(id);
        if (product.isPresent()) {
            product updatedProduct = product.get();
            updatedProduct.setNama_produk(productDetails.getNama_produk());
            updatedProduct.setBrand(productDetails.getBrand());
            updatedProduct.setDeskripsi(productDetails.getBrand());
            updatedProduct.setQuantity(productDetails.getQuantity());
            updatedProduct.setHarga(productDetails.getHarga());
            return ResponseEntity.ok(pr.save(updatedProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //delete produk
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<product> product = pr.findById(id);
        if (product.isPresent()) {
            pr.delete(product.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}