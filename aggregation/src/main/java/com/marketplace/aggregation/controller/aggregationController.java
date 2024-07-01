package com.marketplace.aggregation.controller;

import com.marketplace.aggregation.dto.Cart;
import com.marketplace.aggregation.dto.Customer;
import com.marketplace.aggregation.dto.Order;
import com.marketplace.aggregation.dto.Product;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/aggregation")
public class aggregationController {

    private final String customerServiceUrl = "http://localhost:8081/customer";
    private final String productServiceUrl = "http://localhost:8082/product";
    private final String orderServiceUrl = "http://localhost:8083/transactions";

    @Autowired
    private RestTemplate restTemplate;

    // Endpoint untuk mendapatkansemua customer
    @GetMapping("/customer")
    public ResponseEntity<Object> getAllCustomers() {
        try {
            ResponseEntity<Customer[]> response = restTemplate.getForEntity(customerServiceUrl, Customer[].class);
            List<Customer> customers = Arrays.asList(response.getBody());
            if (!customers.isEmpty()) {
                return ResponseEntity.ok(customers);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan detail customer berdasarkan ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getCustomerDetails(@PathVariable String customerId) {
        try {
            Customer customer = restTemplate.getForObject(customerServiceUrl + "/{customerId}", Customer.class,
                    customerId);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menambah customer
    @PostMapping("/customer/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(customerServiceUrl + "/addCustomer", customer,
                    String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Customer berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan customer");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mengupdate customer berdasarkan ID
    @PutMapping("/customer/updateCustomer/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
        try {
            restTemplate.put(customerServiceUrl + "/updateCustomer/{customerId}", customer, customerId);
            return ResponseEntity.ok("Customer berhasil diperbarui");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus customer berdasarkan ID
    @DeleteMapping("/customer/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
        try {
            restTemplate.delete(customerServiceUrl + "/deleteCustomer/{customerId}", customerId);
            return ResponseEntity.ok("Customer berhasil dihapus");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Customer Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan semua produk
    @GetMapping("/product")
    public ResponseEntity<Object> getAllProducts() {
        try {
            Product[] products = restTemplate.getForObject(productServiceUrl, Product[].class);
            if (products != null && products.length > 0) {
                return ResponseEntity.ok(products);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Produk Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Produk Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan detail product berdasarkan ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProductDetails(@PathVariable String productId) {
        try {
            Product product = restTemplate.getForObject(productServiceUrl + "/{productId}", Product.class, productId);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Produk Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Produk Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menambah product
    @PostMapping("/product/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(productServiceUrl + "/addProduct", product,
                    String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Product berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan product");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mengupdate product berdasarkan ID
    @PutMapping("/product/updateProduct/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable String productId, @RequestBody Product product) {
        try {
            restTemplate.put(productServiceUrl + "/updateProduct/{productId}", product, productId);
            return ResponseEntity.ok("Product berhasil diperbarui");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Product Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus product berdasarkan ID
    @DeleteMapping("/product/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        try {
            restTemplate.delete(productServiceUrl + "/deleteProduct/{productId}", productId);
            return ResponseEntity.ok("Product berhasil dihapus");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Product Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan semua orders
    @GetMapping("/order")
    public ResponseEntity<Object> getAllOrders() {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(orderServiceUrl + "/order", Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengambil data orders");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan detail order berdasarkan ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable String orderId) {
        try {
            Order order = restTemplate.getForObject(orderServiceUrl + "/{orderId}", Order.class, orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Order Tidak Ditemukan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Order Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menambah order
    @PostMapping("/order/addOrder")
    public ResponseEntity<Object> addOrder(@RequestBody Order order) {
        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(orderServiceUrl + "/addOrder", order,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Order berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan order");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus order berdasarkan kodeTransaksi
    @DeleteMapping("/order/deleteOrder/{kodeTransaksi}")
    public ResponseEntity<Object> deleteOrder(@PathVariable String kodeTransaksi) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    orderServiceUrl + "/deleteOrder/{kodeTransaksi}",
                    HttpMethod.DELETE,
                    null,
                    Boolean.class,
                    kodeTransaksi);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody()) {
                return ResponseEntity.ok("Order berhasil dihapus");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menghapus order");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<Object> getAllCarts() {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(orderServiceUrl + "/cart", Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengambil data carts");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk mendapatkan detail cart berdasarkan kodeKeranjang
    @GetMapping("/cart/{kodeKeranjang}")
    public ResponseEntity<Object> getCartDetails(@PathVariable String kodeKeranjang) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(orderServiceUrl + "/cart/{kodeKeranjang}",
                    Object.class, kodeKeranjang);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Cart Tidak Ditemukan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengambil data cart");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Cart Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menambah cart
    @PostMapping("/cart/addCart")
    public ResponseEntity<Object> addCart(@RequestBody Cart cart) {
        try {
            // Check if product exists and get product details
            String productUrl = productServiceUrl + "/" + cart.getKodeProduk();
            ResponseEntity<Product> productResponse = restTemplate.getForEntity(productUrl, Product.class);
            if (!productResponse.getStatusCode().is2xxSuccessful() || productResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produk dengan kode " + cart.getKodeProduk() + " tidak ditemukan");
            }

            // Get product price
            double hargaBarang = productResponse.getBody().getHarga();
            cart.setHargaBarang(hargaBarang);

            // Continue with adding cart logic
            ResponseEntity<Object> response = restTemplate.postForEntity(orderServiceUrl + "/cart/addCart", cart, Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Cart berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan cart");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }


    @PutMapping("/cart/updateCart/{kodeKeranjang}")
    public ResponseEntity<Object> updateCart(@PathVariable String kodeKeranjang, @RequestBody Cart cart) {
        try {
            // Validasi apakah produk dengan kode yang diberikan tersedia
            ResponseEntity<Product> productResponse = restTemplate.getForEntity(productServiceUrl + "/product/{id}",
                    Product.class, cart.getKodeProduk());
            if (productResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Produk dengan kode " + cart.getKodeProduk() + " tidak ditemukan");
            }

            // Mengambil harga produk dari respons layanan produk
            double hargaProduk = productResponse.getBody().getHarga();

            // Lanjutkan dengan operasi update cart
            ResponseEntity<Object> response = restTemplate.exchange(
                    orderServiceUrl + "/updateCart/{kodeKeranjang}?hargaProduk={hargaProduk}",
                    HttpMethod.PUT,
                    null,
                    Object.class,
                    kodeKeranjang, hargaProduk);

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Cart berhasil diupdate");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengupdate cart");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produk dengan kode " + cart.getKodeProduk() + " tidak ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus cart berdasarkan kodeKeranjang
    @DeleteMapping("/cart/deleteCart/{kodeKeranjang}")
    public ResponseEntity<Object> deleteCart(@PathVariable String kodeKeranjang) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    orderServiceUrl + "/deleteCart/{kodeKeranjang}",
                    HttpMethod.DELETE,
                    null,
                    Boolean.class,
                    kodeKeranjang);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody()) {
                return ResponseEntity.ok("Cart berhasil dihapus");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menghapus cart");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }
}
