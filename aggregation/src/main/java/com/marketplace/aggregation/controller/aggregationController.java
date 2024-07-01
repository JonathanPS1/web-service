package com.marketplace.aggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.aggregation.dto.AggregatedOrderDetails;
import com.marketplace.aggregation.service.aggregationService;

@RestController
@RequestMapping("/aggregation")
public class aggregationController {

    @Autowired
    private aggregationService aggregationService;

    @GetMapping("/order-details/{kodeTransaksi}")
    public ResponseEntity<AggregatedOrderDetails> getOrderDetails(@PathVariable String kodeTransaksi) {
        AggregatedOrderDetails aggregatedOrderDetails = aggregationService.getAggregatedOrderDetails(kodeTransaksi);
        if (aggregatedOrderDetails == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok(aggregatedOrderDetails);
    }
}
