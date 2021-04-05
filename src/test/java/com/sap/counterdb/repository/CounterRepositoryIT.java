package com.sap.counterdb.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.sap.counterdb.TestDataSourceHandler;
import com.sap.counterdb.entities.CounterEntity;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { com.sap.counterdb.CdbsApplication.class })

@Slf4j
public class CounterRepositoryIT {

	@Autowired
	private TransactionTemplate txTemplate;

	@Autowired
	private CounterRepository counterRepository;

	@Autowired
	private TestDataSourceHandler testDataSourceHandler;

	private CounterEntity en;

	@Before
	public void setup() throws IOException, InterruptedException {
		testDataSourceHandler.saveDbState();

		txTemplate.execute(status -> {
			Optional<CounterEntity> found = counterRepository.findTopByOrderByCounterValue();
			en = found.get();
			en.setCounterValue(2);
			return counterRepository.save(en);
		});
	}

	@After
	public void tearDown() throws IOException, InterruptedException {
		testDataSourceHandler.restoreDbState();
	}

	@Test
	public void testGetCounter() {
		log.info("Fetch counter from database:::");
		Optional<CounterEntity> found = counterRepository.findTopByOrderByCounterValue();
		assertThat(found.get().getCounterValue()).isEqualTo(en.getCounterValue());
	}

}
