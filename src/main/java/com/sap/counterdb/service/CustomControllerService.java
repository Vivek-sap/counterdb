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

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomControllerService {

	 private @Setter RestTemplate restTemplate;

	    public CustomControllerService(@Value("${custom.controller.url}") String controllerUrl) {
	        log.info("controllerUrl={}", controllerUrl);

	        RestTemplateBuilder restTemplateBuilder =
	            new RestTemplateBuilder().requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))	                
	                .rootUri(controllerUrl);
	        
	        this.restTemplate = restTemplateBuilder.build();
	    }
	    
	  public ControllerData getControllerInfo(String namespace, String labelkey, String labelValue) {
		  
		  ControllerData res = null;
		  
		  log.info("Getting custom controller deployment information by namespace, labelkey and labelValue {}", namespace, labelkey, labelValue);
		  try {
			 res =  restTemplate.getForObject("namespace/{namespace}/label/labelValue/{labelkey}/labelValue/{labelValue}", ControllerData.class, namespace,
					  labelkey, labelValue);
			validateResult(res);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("{}", e.getMessage(), e);
		}
		 
		  return res;
	  }
	
	  private void validateResult(ControllerData result) {
	        if (result == null || result.getDeploymentCount() == null) {
	            throw new BadRequestException(ErrorSource.SERVICE, ErrorCode.INVALID_DATA, "Some information related to pod is invalid");
	        }
	  }
	    

}
