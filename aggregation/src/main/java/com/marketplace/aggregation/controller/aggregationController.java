package com.marketplace.aggregation.controller;

import com.marketplace.aggregation.dto.Cart;
import com.marketplace.aggregation.dto.Customer;
import com.marketplace.aggregation.dto.Order;
import com.marketplace.aggregation.dto.Product;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    @GetMapping("/getpelanggan/{kodeCustomer}")
    public ResponseEntity<Object> getPelanggan(@PathVariable Integer kodeCustomer) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(
                    orderServiceUrl + "/getpelanggan/{kodeCustomer}",
                    Object.class, kodeCustomer);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Pelanggan Tidak Ditemukan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengambil data pelanggan");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Pelanggan Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @GetMapping("/gettransaksi/{tanggalPembelian}")
    public ResponseEntity<Object> getTransaksi(@PathVariable String tanggalPembelian) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(
                    orderServiceUrl + "/gettransaksi/{tanggalPembelian}",
                    Object.class, tanggalPembelian);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Transaksi Tidak Ditemukan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengambil data transaksi");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Transaksi Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @GetMapping("/order/{kodeTransaksi}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable String kodeTransaksi) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(orderServiceUrl + "/order/{kodeTransaksi}",
                    Object.class, kodeTransaksi);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Order Tidak Ditemukan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengambil data order");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Order Tidak Ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @PostMapping("/order/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            // Periksa keberadaan customer
            ResponseEntity<Customer> customerResponse = restTemplate
                    .getForEntity(customerServiceUrl + "/" + order.getKodeCustomer(), Customer.class);
            if (customerResponse.getStatusCode() != HttpStatus.OK || customerResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Customer dengan kode tersebut tidak ditemukan");
            }
            ResponseEntity<String> response = restTemplate.postForEntity(orderServiceUrl + "/order/addOrder", order,
                    String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Order berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan order");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer dengan kode tersebut tidak ditemukan");
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @PutMapping("/order/updateOrder/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable String orderId, @RequestBody Order order) {
        try {
            // Periksa keberadaan customer
            ResponseEntity<Customer> customerResponse = restTemplate
                    .getForEntity(customerServiceUrl + "/" + order.getKodeCustomer(), Customer.class);
            if (customerResponse.getStatusCode() != HttpStatus.OK || customerResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Customer dengan kode tersebut tidak ditemukan");
            }
            restTemplate.put(orderServiceUrl + "/order/updateOrder/{orderId}", order, orderId);
            return ResponseEntity.ok("Order berhasil diperbarui");
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer dengan kode tersebut tidak ditemukan");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus order berdasarkan kodeTransaksi
    @DeleteMapping("/order/deleteOrder/{kodeTransaksi}")
    public ResponseEntity<Object> deleteOrder(@PathVariable String kodeTransaksi) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    orderServiceUrl + "/order/deleteOrder/{kodeTransaksi}",
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
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Produk dengan kode " + cart.getKodeProduk() + " tidak ditemukan");
            }

            // Get product price
            double hargaBarang = productResponse.getBody().getHarga();
            cart.setHargaBarang(hargaBarang);

            // Continue with adding cart logic
            ResponseEntity<Object> response = restTemplate.postForEntity(orderServiceUrl + "/cart/addCart", cart,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Cart berhasil ditambahkan");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan cart");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    @PutMapping("/cart/updateCart/{id}")
    public ResponseEntity<Object> updateCart(@PathVariable String id, @RequestBody Cart cart) {
        try {
            // Validasi apakah produk dengan kode yang diberikan tersedia
            ResponseEntity<Product> productResponse = restTemplate
                    .getForEntity(productServiceUrl + "/" + cart.getKodeProduk(), Product.class);
            if (productResponse.getStatusCode() != HttpStatus.OK || productResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Produk dengan kode " + cart.getKodeProduk() + " tidak ditemukan");
            }

            // Mengambil harga produk dari respons layanan produk
            double hargaProduk = productResponse.getBody().getHarga();
            cart.setHargaBarang(hargaProduk);

            // Membuat HttpEntity dengan body request
            HttpEntity<Cart> requestEntity = new HttpEntity<>(cart);

            // Lanjutkan dengan operasi update cart
            ResponseEntity<Object> response = restTemplate.exchange(orderServiceUrl + "/cart/updateCart/{id}",
                    HttpMethod.PUT, requestEntity, Object.class, id);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Cart berhasil diupdate");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengupdate cart");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus cart berdasarkan kodeKeranjang
    @DeleteMapping("/cart/deleteCart/{kodeKeranjang}")
    public ResponseEntity<Object> deleteCart(@PathVariable String kodeKeranjang) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    orderServiceUrl + "/cart/deleteCart/{kodeKeranjang}",
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
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }

    // Endpoint untuk menghapus cart berdasarkan kodeKeranjang
    @DeleteMapping("/cart/deleteItem/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable String id) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    orderServiceUrl + "/cart/deleteItem/{id}",
                    HttpMethod.DELETE,
                    null,
                    Boolean.class,
                    id);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody()) {
                return ResponseEntity.ok("Item berhasil dihapus");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menghapus Item");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses permintaan");
        }
    }
}
