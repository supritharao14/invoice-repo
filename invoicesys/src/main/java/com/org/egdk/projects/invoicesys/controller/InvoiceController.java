package com.org.egdk.projects.invoicesys.controller;

import org.slf4j.Logger; 

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.egdk.projects.invoicesys.dto.InvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.InvoiceResponseDto;
import com.org.egdk.projects.invoicesys.dto.ProcessInvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.ResponseDto;
import com.org.egdk.projects.invoicesys.dto.SavedInvoiceResponseDto;
import com.org.egdk.projects.invoicesys.exception.InvoiceNotFoundException;
import com.org.egdk.projects.invoicesys.exception.PaidamountGreterThanAmountException;
import com.org.egdk.projects.invoicesys.service.InvoiceService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;

/**
 * This is controller class which is used for invoice operations.
 * @author Supritha P
 */
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	
@Autowired
private InvoiceService invoiceService;


/**
 * 
 * @param invoiceRequestDto
 * @return SavedInvoiceResponseDto
 */
	@PostMapping("")
	@ApiOperation("creating invoice")
	@ApiResponses(value = {@ApiResponse(responseContainer = "900",code = 900,message = "Date should be greter than current date")})
	public ResponseEntity<SavedInvoiceResponseDto> insertInvoice(@Valid @RequestBody InvoiceRequestDto invoiceRequestDto)
	{
		logger.info("insertInvoice controller method excecution started");
		return new ResponseEntity<>(invoiceService.insertInvoice(invoiceRequestDto), HttpStatus.OK);
	
	}
	
	
	/**
	 * 
	 * @return InvoiceResponseDto
	 * @throws InvoiceNotFoundException
	 */
	@GetMapping("")
	@ApiOperation("get all invoice")
	@ApiResponses(value = {@ApiResponse(responseContainer = "905",code = 900,message = "Invoice not found")})
	public ResponseEntity<InvoiceResponseDto> retriveInvoices() throws InvoiceNotFoundException
	{
		logger.info("retriveInvoices controller method excecution started");
		return new ResponseEntity<>(invoiceService.retriveInvoices(), HttpStatus.OK);
		
	}
	
	
	/**
	 * 
	 * @param id
	 * @param paidAmount
	 * @return ResponseDto
	 * @throws InvoiceNotFoundException
	 * @throws PaidamountGreterThanAmountException
	 */
	@PutMapping("/payments/{id}")
	@ApiOperation("update paid ammount in invoice")
	@ApiResponses(value = {@ApiResponse(responseContainer = "905",code = 900,message = "Invoice not found")})
	public ResponseEntity<ResponseDto> updateInvoices(@PathVariable Long id, @RequestParam Double paidAmount) throws InvoiceNotFoundException, PaidamountGreterThanAmountException
	{
		logger.info("updateInvoices controller method excecution started");
		return new ResponseEntity<>(invoiceService.updateInvoices(id,paidAmount), HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * @param processInvoiceRequestDto
	 * @return ResponseDto
	 */
	@PostMapping("/process-overdue")
	@ApiOperation("process all pending invoices that are overdue")
	@ApiResponses(value = {@ApiResponse(responseContainer = "905",code = 900,message = "Invoice not found")})
	public ResponseEntity<ResponseDto> processOverdueInvoice(@Valid @RequestBody ProcessInvoiceRequestDto processInvoiceRequestDto )
	{
		logger.info("processOverdueInvoice controller method excecution started");
		return new ResponseEntity<>(invoiceService.processOverdueInvoice(processInvoiceRequestDto), HttpStatus.OK);
	
	}
	
}
