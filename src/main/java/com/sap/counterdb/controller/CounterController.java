package com.sap.counterdb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.counterdb.data.CounterData;
import com.sap.counterdb.service.CounterService;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1/counter")
@Slf4j
@Validated
public class CounterController {
	
	@Autowired
    private CounterService counterService;
	
	   // @Operation(summary = "update the counter in database")
	    @PatchMapping(path = "{val}", consumes = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<CounterData> getFaultClearanceOrder(@Valid @PathVariable int val) {
	    	return ResponseEntity.ok(counterService.updateCounter(val));
	    }
	    
	   // @Operation(summary = "Retrieve information about counter.")
	    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<CounterData> getCounter() {
	            log.debug("Fetching the counter information ");
	            return ResponseEntity.ok(counterService.getCounterData());
	    }

}
