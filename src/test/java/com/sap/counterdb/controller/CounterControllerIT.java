package com.sap.counterdb.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.sap.counterdb.CdbsApplication;
import com.sap.counterdb.entities.CounterEntity;
import com.sap.counterdb.repository.CounterRepository;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { CdbsApplication.class })
public class CounterControllerIT {

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private TransactionTemplate txTemplate;

	@Autowired
	private CounterRepository counterRepository;

	@Test
	public void getCounter() throws Exception {
		int request = 2;
		createCounterInDb(new CounterEntity(2));

		given().port(randomServerPort).when().log().all().accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(ContentType.JSON).get("/v1/counter").then().log().all().statusCode(HttpStatus.OK.value())
				.contentType(MediaType.APPLICATION_JSON_VALUE).body("counterValue", equalTo(request));

	}

	@Test
	public void updateCounter() throws Exception {

		given().port(randomServerPort).when().contentType(ContentType.JSON).log().all().pathParam("val", 5)
				.patch("/v1/counter/{val}").then().log().all().statusCode(HttpStatus.OK.value())
				.body("counterValue", equalTo(5));
	}

	private CounterEntity createCounterInDb(CounterEntity entity) {

		return txTemplate.execute(status -> {
			Optional<CounterEntity> found = counterRepository.findTopByOrderByCounterValue();
			CounterEntity en = found.get();
			en.setCounterValue(2);
			return counterRepository.save(entity);
		});
	}

}
