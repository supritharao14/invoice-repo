package com.org.egdk.projects.invoicesys.dto;

import java.time.LocalDate; 

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceDto {

	private long invoiceId;

	private double amount;

	private double paidAmount;

	private LocalDate dueDate;

	String status;
}
