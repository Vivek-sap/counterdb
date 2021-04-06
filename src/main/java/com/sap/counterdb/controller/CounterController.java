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

import com.sap.counterdb.data.ControllerData;
import com.sap.counterdb.data.CounterData;
import com.sap.counterdb.service.CounterService;
import com.sap.counterdb.service.CustomControllerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1/counter")
@Slf4j
@Validated
public class CounterController {

	@Autowired
	private CounterService counterService;
	
	@Autowired
	private CustomControllerService customControllerService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CounterData> getCounter() {
		log.debug("Fetching the counter information ");
		return ResponseEntity.ok(counterService.getCounterData());
	}

	@PatchMapping(path = "increment/{val}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CounterData> incrementCounter(@Valid @PathVariable int val) {
		return ResponseEntity.ok(counterService.updateCounter(val,true));
	}

	@PatchMapping(path = "decrement/{val}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CounterData> decrementCounter(@Valid @PathVariable int val) {
		return ResponseEntity.ok(counterService.updateCounter(val,false));
	}

	@GetMapping(path = "namespace/{namespace}/label/labelValue/{labelkey}/labelValue/{labelValue}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ControllerData> getDeploymentByNamespaceAndLabel(@Valid @PathVariable String namespace,
			@Valid @PathVariable String labelkey, @Valid @PathVariable String labelValue) {
		log.info("Fetching the pod deployment information by namespace={} and labelkey={} and labelValue={}"  +  namespace);
		return ResponseEntity.ok(customControllerService.getControllerInfo(namespace, labelkey, labelValue));
	}
}
