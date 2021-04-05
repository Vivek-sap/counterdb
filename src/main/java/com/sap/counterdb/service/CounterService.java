package com.sap.counterdb.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import com.sap.counterdb.data.CounterData;
import com.sap.counterdb.entities.CounterEntity;
import com.sap.counterdb.exceptions.ErrorCode;
import com.sap.counterdb.exceptions.ErrorSource;
import com.sap.counterdb.exceptions.ResourceNotFoundException;
import com.sap.counterdb.repository.CounterRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CounterService {

	private CounterRepository counterRepository;

	private RetryTemplate transientDataAccessExceptionRetryTemplate;

	public CounterService(CounterRepository counterRepository,
			@Qualifier("TransientDataAccessExceptionRetryTemplate") RetryTemplate transientDataAccessExceptionRetryTemplate) {
		this.counterRepository = counterRepository;
		this.transientDataAccessExceptionRetryTemplate = transientDataAccessExceptionRetryTemplate;
	}

	// Flag Modifier is used to differentiate between increment and decrement
	@Transactional
	public CounterData updateCounter(int val, boolean flagModifier) {
		log.debug("Started updating counter Entity......{}", val);

		return transientDataAccessExceptionRetryTemplate.execute(retryContext -> {
			CounterEntity en = getCounter();
			int newValue = flagModifier?en.getCounterValue()+val:en.getCounterValue()-val;
			en.setCounterValue(newValue);
		    counterRepository.save(en);
			return convertEntityToData(en);
		});
	}
	
	public CounterData getCounterData() {
		log.debug("Started fetching counter Entity......");
		CounterEntity en = getCounter();
		return convertEntityToData(en);
	}

	private CounterEntity getCounter() {
		log.debug("Started fetching counter Entity......");
		return counterRepository.findTopByOrderByCounterValue()
				.orElseThrow(() -> new ResourceNotFoundException(ErrorSource.SERVICE, ErrorCode.TECHNICAL_PROBLEM,
						"Unable to find counter data from database"));
	}
	
	private CounterData convertEntityToData(CounterEntity en) {
		return new CounterData(en.getCounterValue());
	}

}
