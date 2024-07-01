package com.marketplace.order.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketplace.order.model.cart;
import com.marketplace.order.model.order;
import com.marketplace.order.repository.cartRepository;
import com.marketplace.order.repository.orderRepository;

@Controller
@RequestMapping("/transactions")
public class transactionsController {
    @Autowired
    private orderRepository or;

    @Autowired
    private cartRepository cr;

    @GetMapping("/order")
    public @ResponseBody ResponseEntity<Object> getAllOrders() {
        Iterable<order> orders = or.findAll();
        if (!orders.iterator().hasNext()) {
            return ResponseEntity.status(204).body("Tidak ada data");
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @GetMapping("/order/{kodeTransaksi}")
    public @ResponseBody ResponseEntity<Object> getOrderByKodeKeranjang(@PathVariable String kodeTransaksi) {
        if (or.existsByKodeTransaksi(kodeTransaksi)) {
            Iterable<order> orders = or.findByKodeTransaksi(kodeTransaksi);
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(404).body("Data tidak ditemukan");
        }
    }

    @PostMapping("/order/addOrder")
    public @ResponseBody ResponseEntity<Object> addOrder(@RequestBody order o) {
        if (or.existsByKodeTransaksi(o.getKodeTransaksi())) {
            return ResponseEntity.status(400).body("Transaksi sudah selesai");
        } else {
            // Mengambil list cart berdasarkan kodeKeranjang
            List<cart> carts = cr.findByKodeKeranjang(o.getKodeKeranjang());

            if (!carts.isEmpty()) {// cek apakah transaksi keranjang sudah pernah dilakukan
                if (cr.findByKodeKeranjang(o.getKodeKeranjang()).getFirst().isStatus() == false) {
                    // Menghitung total
                    double total = carts.stream().mapToDouble(cart::getSubtotal).sum();

                    if (o.getPembayaran() >= total) {
                        // update status cart
                        for (cart c : carts) {
                            c.setStatus(true);
                            cr.save(c);
                        }

                        // Mendapatkan tanggal hari ini
                        LocalDate today = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String formattedDate = today.format(formatter);

                        o.setTanggalPembelian(formattedDate);
                        o.setDetailCart(carts);
                        o.setTotal(total);
                        o.setKembalian(o.getPembayaran() - total);

                        or.insert(o);
                        return ResponseEntity.status(200).body(true);
                    } else {
                        return ResponseEntity.status(400)
                                .body("Uang anda tidak mencukupi. Total belanja anda Rp" + total);
                    }
                } else {
                    return ResponseEntity.status(400)
                            .body("Transaksi keranjang " + o.getKodeKeranjang() + " sudah kadaluarsa");
                }
            } else {
                return ResponseEntity.status(400).body("Keranjang tidak ditemukan");
            }
        }
    }

    @DeleteMapping("/order/deleteOrder/{kodeTransaksi}")
    public @ResponseBody boolean deleteOrder(@PathVariable String kodeTransaksi) {
        if (or.existsByKodeTransaksi(kodeTransaksi)) {
            or.deleteByKodeTransaksi(kodeTransaksi);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/getpelanggan")
    public @ResponseBody Iterable<order> getPelanggan(@RequestParam String kodePelanggan) {
        return or.findByPelanggan(kodePelanggan);
    }

    @PostMapping("/gettransaksi")
    public @ResponseBody Iterable<order> getTransaksi(@RequestParam String tanggalPembelian) {
        return or.findByTglPembelian(tanggalPembelian);
    }

    // O O O O - -O O- - O O O- -O O O O O
    // O - - - - O - O - O- - -O- - -O
    // O - - - -O- - -O- O O O- - - -O
    // O - - - -O O O O- O- -O- - - -O
    // O O O O--O- - -O- O- - O - - -O

    @GetMapping("/cart")
    public @ResponseBody ResponseEntity<Object> getAllCarts() {
        Iterable<cart> carts = cr.findAll();
        if (!carts.iterator().hasNext()) {
            return ResponseEntity.status(204).body("Tidak ada data");
        } else {
            return ResponseEntity.ok(carts);
        }
    }

    @GetMapping("/cart/{kodeKeranjang}")
    public @ResponseBody ResponseEntity<Object> getCartByKodeKeranjang(@PathVariable String kodeKeranjang) {
        if (cr.existsByKodeKeranjang(kodeKeranjang)) {
            Iterable<cart> carts = cr.findByKodeKeranjang(kodeKeranjang);
            return ResponseEntity.ok(carts);
        } else {
            return ResponseEntity.status(404).body("Data tidak ditemukan");
        }
    }

    @PostMapping("/cart/addCart")
    public @ResponseBody ResponseEntity<Object> addCart(@RequestBody cart c, @RequestParam double hargaBarang) {
        try {
            if (cr.existsByKodeKeranjang(c.getKodeKeranjang())) {
                List<cart> carts = cr.findByKodeKeranjang(c.getKodeKeranjang());
                for (cart oldCart : carts) {
                    if (oldCart.isStatus() == true) {
                        return ResponseEntity.status(400).body("Keranjang sudah dibayar");
                    }
                    if (cr.existsByKodeProduk(c.getKodeProduk())) {
                        return ResponseEntity.status(400).body("Produk sudah masuk keranjang");
                    }
                }
            }
            c.setSubtotal(c.getJumlahBarang() * hargaBarang);
            c.setStatus(false);
            cr.insert(c);
            return ResponseEntity.status(200).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Terjadi kesalahan sistem");
        }
    }

    @PutMapping("/cart/updateCart/{kodeKeranjang}")
    public @ResponseBody Boolean updateCart(@PathVariable String kodeKeranjang, @RequestBody cart newData,
            @RequestParam double hargaBarang) {
        if (cr.existsByKodeKeranjang(kodeKeranjang)) {
            cart oldData = cr.findByKodeKeranjang(kodeKeranjang).getFirst();
            if (oldData.isStatus() == false) {
                oldData.setKodeKeranjang(kodeKeranjang);
                oldData.setKodeProduk(newData.getKodeProduk());
                oldData.setJumlahBarang(newData.getJumlahBarang());
                oldData.setSubtotal(hargaBarang * newData.getJumlahBarang());
                oldData.setStatus(false);

                cr.save(oldData);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @DeleteMapping("/cart/deleteCart/{kodeKeranjang}")
    public @ResponseBody Boolean deleteCart(@PathVariable String kodeKeranjang) {
        if (cr.existsByKodeKeranjang(kodeKeranjang)) {
            cr.deleteByKodeKeranjang(kodeKeranjang);
            return true;
        } else {
            return false;
        }
    }
}
