package com.sap.counterdb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.sap.counterdb.data.ControllerData;

@RunWith(SpringRunner.class)
public class ControllerServiceTest {

	private static final String NOT_NULL = "I am so full of content!";

	@TestConfiguration
	static class AdapterserviceTestContextConfiguration {

		@MockBean
		private static RestTemplate restTemplate;

		@Bean
		public CustomControllerService customerService() {
			return new CustomControllerService(NOT_NULL);
		}
	}

	@Autowired
	private CustomControllerService customControllerService;


	@Before
	public void init() {
		customControllerService.setRestTemplate(AdapterserviceTestContextConfiguration.restTemplate);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetCustomerInfoByLineIdWithSuccess() {
	
		Mockito.when(AdapterserviceTestContextConfiguration.restTemplate.getForObject(any(String.class),
				any(Class.class), any(String.class), any(String.class), any(String.class))).thenReturn(new ControllerData("5"));

		String namespace = "abcd";
		String labelkey = "db";
		String labelValue = "db";
		ControllerData output = customControllerService.getControllerInfo(namespace, labelkey, labelValue);
		assertThat(output.getDeploymentCount()).isEqualTo("5");
	}
}
