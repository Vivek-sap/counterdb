package com.sap.counterdb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.counterdb.entities.CounterEntity;

public interface CounterRepository extends JpaRepository<CounterEntity, String> {
	
	Optional<CounterEntity> findTopByOrderByCounterValue();

}
