package com.sap.counterdb.entities;


import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "counter")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CounterEntity extends AbstractEntity {	
	
    private int counterValue;	

}
