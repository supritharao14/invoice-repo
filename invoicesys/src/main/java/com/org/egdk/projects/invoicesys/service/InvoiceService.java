package com.org.egdk.projects.invoicesys.service;

import org.springframework.stereotype.Service; 

import com.org.egdk.projects.invoicesys.dto.InvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.InvoiceResponseDto;
import com.org.egdk.projects.invoicesys.dto.ProcessInvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.ResponseDto;
import com.org.egdk.projects.invoicesys.dto.SavedInvoiceResponseDto;
import com.org.egdk.projects.invoicesys.exception.InvoiceNotFoundException;
import com.org.egdk.projects.invoicesys.exception.PaidamountGreterThanAmountException;

import jakarta.validation.Valid;

@Service
public interface InvoiceService {

	SavedInvoiceResponseDto insertInvoice(@Valid InvoiceRequestDto invoiceRequestDto);

	InvoiceResponseDto retriveInvoices() throws InvoiceNotFoundException;

	ResponseDto updateInvoices(Long id, Double paidAmount) throws InvoiceNotFoundException, PaidamountGreterThanAmountException;

	ResponseDto processOverdueInvoice(@Valid ProcessInvoiceRequestDto processInvoiceRequestDto);

}
