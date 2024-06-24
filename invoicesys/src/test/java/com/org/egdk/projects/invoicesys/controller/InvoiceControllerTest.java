package com.org.egdk.projects.invoicesys.controller;

import java.time.LocalDate; 
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.org.egdk.projects.invoicesys.constants.AppConstants;
import com.org.egdk.projects.invoicesys.dto.InvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.InvoiceResponseDto;
import com.org.egdk.projects.invoicesys.dto.ProcessInvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.ResponseDto;
import com.org.egdk.projects.invoicesys.dto.SavedInvoiceResponseDto;
import com.org.egdk.projects.invoicesys.exception.InvoiceNotFoundException;
import com.org.egdk.projects.invoicesys.exception.PaidamountGreterThanAmountException;
import com.org.egdk.projects.invoicesys.service.InvoiceService;

@ExtendWith(MockitoExtension.class)
public class InvoiceControllerTest {

	@Mock
	private InvoiceService invoiceService;
	
	@InjectMocks
	InvoiceController invoiceController;
	
	SavedInvoiceResponseDto savedInvoiceResponseDto;
	InvoiceRequestDto invoiceRequestDto;
	InvoiceResponseDto invoiceResponseDto;
	ResponseDto responseDto;
	ProcessInvoiceRequestDto processInvoiceRequestDto;
	@BeforeEach
	public void setup() {
		savedInvoiceResponseDto=new SavedInvoiceResponseDto();
		savedInvoiceResponseDto.setStatusCode(902);
		invoiceRequestDto =new InvoiceRequestDto();
		invoiceResponseDto= new InvoiceResponseDto();
		responseDto=new ResponseDto();
		processInvoiceRequestDto=new ProcessInvoiceRequestDto();
		
		invoiceRequestDto.setAmount(3000);
		invoiceRequestDto.setDueDate(LocalDate.of(2025, 1, 10));
		invoiceResponseDto.setStatusCode(902);
		responseDto.setStatusCode(902);
		processInvoiceRequestDto.setLateFee(100.0);
		processInvoiceRequestDto.setOverdueDays(10);
	}
	
	@Test
	void insertInvoice() {
	Mockito.when(invoiceService.insertInvoice(Mockito.any(InvoiceRequestDto.class))).thenReturn(savedInvoiceResponseDto);
	ResponseEntity<SavedInvoiceResponseDto> result = invoiceController.insertInvoice(invoiceRequestDto);
	Assertions.assertEquals(AppConstants.TEST_CODE,result.getBody().getStatusCode() );
	}
	
	@Test
	void retriveInvoices() throws InvoiceNotFoundException {
	Mockito.when(invoiceService.retriveInvoices()).thenReturn(invoiceResponseDto);
	ResponseEntity<InvoiceResponseDto> result = invoiceController.retriveInvoices();
	Assertions.assertEquals(AppConstants.TEST_CODE,result.getBody().getStatusCode() );
	}
	
	@Test
	void updateInvoices() throws InvoiceNotFoundException, PaidamountGreterThanAmountException {
	Mockito.when(invoiceService.updateInvoices(Mockito.anyLong(), Mockito.any(Double.class))).thenReturn(responseDto);
	ResponseEntity<ResponseDto> result = invoiceController.updateInvoices(1234l,200.0);
	Assertions.assertEquals(AppConstants.TEST_CODE,result.getBody().getStatusCode() );
	}
	
	@Test
	void processOverdueInvoice() throws InvoiceNotFoundException, PaidamountGreterThanAmountException {
	Mockito.when(invoiceService.processOverdueInvoice(Mockito.any(ProcessInvoiceRequestDto.class))).thenReturn(responseDto);
	ResponseEntity<ResponseDto> result = invoiceController.processOverdueInvoice(processInvoiceRequestDto);
	Assertions.assertEquals(AppConstants.TEST_CODE,result.getBody().getStatusCode() );
	}
}
