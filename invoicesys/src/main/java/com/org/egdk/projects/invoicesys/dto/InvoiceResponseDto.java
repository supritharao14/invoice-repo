package com.org.egdk.projects.invoicesys.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponseDto extends ResponseDto {

	private List<InvoiceDto> invoiceDtoList;

}
