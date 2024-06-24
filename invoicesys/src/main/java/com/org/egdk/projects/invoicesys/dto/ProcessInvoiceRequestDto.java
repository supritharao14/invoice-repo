package com.org.egdk.projects.invoicesys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessInvoiceRequestDto {
	@NotNull
	private double lateFee;
    private int overdueDays;
}
