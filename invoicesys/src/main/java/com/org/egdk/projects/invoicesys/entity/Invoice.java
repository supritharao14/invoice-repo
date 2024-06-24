package com.org.egdk.projects.invoicesys.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long invoiceId;
	
	private double amount;
	
	private double paidAmount;
	
	private LocalDate dueDate;
	
	String status;
	
}
