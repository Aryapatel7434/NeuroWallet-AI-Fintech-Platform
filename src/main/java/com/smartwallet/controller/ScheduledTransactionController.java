package com.smartwallet.controller;

import com.smartwallet.dto.ScheduleTransactionRequest;
import com.smartwallet.service.ScheduledTransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 //This controller handles Scheduled Transaction APIs
@RestController  //Tells Spring This class handles REST APIS
@RequestMapping("/api/scheduled-transactions")//Base URL
public class ScheduledTransactionController {

    private final ScheduledTransactionService service;//service object

    public ScheduledTransactionController(
            ScheduledTransactionService service) {

        this.service = service;
    }//spring Automatically inject ScheduledTransactionService object

    @PostMapping("/schedule")//Http post request
    @PreAuthorize("hasAnyRole('USER','ADMIN')")//only user having role can access this API
    public String scheduleTransaction(
            @RequestBody ScheduleTransactionRequest request) {
          //This tells spring incomming json->java object
        return service.scheduleTransaction(request);
    }
}