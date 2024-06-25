package com.org.egdk.projects.invoicesys.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequestDto {
	
	@NotNull(message = "amount field is mandatory")
	private double amount;
	@NotNull(message = "date field is mandatory")
	private LocalDate dueDate;

}
