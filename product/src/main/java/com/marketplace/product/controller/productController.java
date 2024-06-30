package com.marketplace.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.product.model.product;
import com.marketplace.product.repository.productRepository;

@RestController
@RequestMapping(path = "/product")
public class productController {
    @Autowired
    private productRepository pr;

    @GetMapping("/get")
    public List<product> getAllProducts() {
        return pr.findAll();
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<product> getProductById(@PathVariable Long id) {
        Optional<product> product = pr.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/post")
    public product createProduct(@RequestBody product p) {
        Long newId = pr.count() + 1;
        p.setId(newId);
        return pr.save(p);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<product> updateProduct(@PathVariable Long id, @RequestBody product productDetails) {
        Optional<product> product = pr.findById(id);
        if (product.isPresent()) {
            product updatedProduct = product.get();
            updatedProduct.setNama_produk(productDetails.getNama_produk());
            updatedProduct.setBrand(productDetails.getBrand());
            updatedProduct.setDeskripsi(productDetails.getDeskripsi());
            updatedProduct.setQuantity(productDetails.getQuantity());
            updatedProduct.setHarga(productDetails.getHarga());
            return ResponseEntity.ok(pr.save(updatedProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Optional<product> product = pr.findById(id);
        if (product.isPresent()) {
            pr.delete(product.get());
            return ResponseEntity.ok("Product with ID " + id + " berhasil dihapus");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
