package com.org.egdk.projects.invoicesys.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.org.egdk.projects.invoicesys.entity.Invoice;
import com.org.egdk.projects.invoicesys.exception.InvoiceNotFoundException;
import com.org.egdk.projects.invoicesys.exception.PaidamountGreterThanAmountException;
import com.org.egdk.projects.invoicesys.repository.InvoiceRepository;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {

	@Mock
	private InvoiceRepository invoiceRepository;

	@InjectMocks
	private InvoiceServiceImpl invoiceServiceImpl;

	Invoice invoice;
	InvoiceRequestDto invoiceRequestDto;
	List<Invoice> invoiceList;
	ResponseDto responseDto;
	ProcessInvoiceRequestDto processInvoiceRequestDto;;

	@BeforeEach
	public void setup() {
		invoice = new Invoice();
		invoiceRequestDto = new InvoiceRequestDto();
		invoiceList = new ArrayList<Invoice>();
		responseDto =new ResponseDto();
		processInvoiceRequestDto=new ProcessInvoiceRequestDto();
		
		invoice.setInvoiceId(902);
		invoice.setAmount(1290);
		invoice.setDueDate(LocalDate.of(2024, 6, 21));
		invoice.setStatus(AppConstants.PENDING);
		invoiceList.add(invoice);
		invoiceList.add(invoice);
		invoiceRequestDto.setAmount(3000);
		invoiceRequestDto.setDueDate(LocalDate.of(2025, 1, 10));
		responseDto.setStatusCode(902);
		processInvoiceRequestDto.setLateFee(100.0);
		processInvoiceRequestDto.setOverdueDays(10);
	}

	@Test
	void insertInvoice() {
		Mockito.when(invoiceRepository.save(Mockito.any(Invoice.class))).thenReturn(invoice);
		SavedInvoiceResponseDto result = invoiceServiceImpl.insertInvoice(invoiceRequestDto);
		Assertions.assertEquals(AppConstants.TEST_CODE, result.getInvoiceId());
	}

	@Test
	void retriveInvoices() throws InvoiceNotFoundException {
		Mockito.when(invoiceRepository.findAll()).thenReturn(invoiceList);
		InvoiceResponseDto result = invoiceServiceImpl.retriveInvoices();
		Assertions.assertEquals(2, result.getInvoiceDtoList().size());
	}
	
	@Test
	void updateInvoices() throws InvoiceNotFoundException, PaidamountGreterThanAmountException {
	Mockito.when(invoiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(invoice));
	ResponseDto result = invoiceServiceImpl.updateInvoices(902l, 200.0);
	Assertions.assertEquals(AppConstants.UPDATE_SUCCESS_CODE,result.getStatusCode() );
	}
	
	@Test
	void processOverdueInvoice() throws InvoiceNotFoundException, PaidamountGreterThanAmountException {
	Mockito.when(invoiceRepository.findByDueDateLessThanAndStatusNot(Mockito.any(LocalDate.class),Mockito.anyString())).thenReturn(invoiceList);
	ResponseDto result = invoiceServiceImpl.processOverdueInvoice(processInvoiceRequestDto);
	Assertions.assertEquals(AppConstants.PROCESS_SUCCESS_CODE,result.getStatusCode() );
	}
}
