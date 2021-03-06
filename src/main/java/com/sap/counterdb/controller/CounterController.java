 package com.sap.counterdb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.counterdb.data.ControllerData;
import com.sap.counterdb.service.CustomControllerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1/deployment")
@Slf4j
@Validated
public class CounterController {
	
	@Autowired
	private CustomControllerService customControllerService;


	@GetMapping(path = "namespace/{namespace}/label/labelkey/{labelkey}/labelValue/{labelValue}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ControllerData> getDeploymentByNamespaceAndLabel(@Valid @PathVariable String namespace,
			@Valid @PathVariable String labelkey, @Valid @PathVariable String labelValue) {
		log.info("Fetching the pod deployment information by namespace={} and labelkey={} and labelValue={}"  +  namespace);
		return ResponseEntity.ok(customControllerService.getControllerInfo(namespace, labelkey, labelValue));
	}
}
