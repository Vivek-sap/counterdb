package com.sap.counterdb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sap.counterdb.data.ControllerData;
import com.sap.counterdb.exceptions.BadRequestException;
import com.sap.counterdb.exceptions.ErrorCode;
import com.sap.counterdb.exceptions.ErrorSource;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomControllerService {

	 private RestTemplate restTemplate;

	    public CustomControllerService(@Value("${custom.controller.url}") String controllerUrl) {
	        log.debug("controllerUrl={}", controllerUrl);

	        RestTemplateBuilder restTemplateBuilder =
	            new RestTemplateBuilder().requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))	                
	                .rootUri(controllerUrl);
	        
	        this.restTemplate = restTemplateBuilder.build();
	    }
	    
	  public ControllerData getControllerInfo(String namespace, String labelkey, String labelValue) {
		  
		  String res = null;
		  
		  log.info("Getting custom controller deployment information by namespace, labelkey and label Value {}", namespace, labelkey, labelValue);
		  try {
			 res =  restTemplate.getForObject("namespace/{namespace}/label/labelValue/{labelkey}/labelValue/{labelValue}", String.class, namespace,
					  labelkey, labelValue);
			validateResult(res);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("{}", e.getMessage(), e);
		}
		 
		  return new ControllerData(res);
	  }
	
	  private void validateResult(String result) {
	        if (result == null) {
	            throw new BadRequestException(ErrorSource.SERVICE, ErrorCode.INVALID_DATA, "Some information related to pod is invalid");
	        }
	  }
	    

}
