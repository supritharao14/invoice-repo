package com.org.egdk.projects.invoicesys.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.egdk.projects.invoicesys.constants.AppConstants;
import com.org.egdk.projects.invoicesys.dto.InvoiceDto;
import com.org.egdk.projects.invoicesys.dto.InvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.InvoiceResponseDto;
import com.org.egdk.projects.invoicesys.dto.ProcessInvoiceRequestDto;
import com.org.egdk.projects.invoicesys.dto.ResponseDto;
import com.org.egdk.projects.invoicesys.dto.SavedInvoiceResponseDto;
import com.org.egdk.projects.invoicesys.entity.Invoice;
import com.org.egdk.projects.invoicesys.exception.InvoiceNotFoundException;
import com.org.egdk.projects.invoicesys.exception.PaidamountGreterThanAmountException;
import com.org.egdk.projects.invoicesys.repository.InvoiceRepository;

import jakarta.validation.Valid;

/**
 * @author Supritha P
 * 
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	InvoiceRepository invoiceRepository;

	
	@Override
	public SavedInvoiceResponseDto insertInvoice(@Valid InvoiceRequestDto invoiceRequestDto) {

		Invoice invoice = new Invoice();
		invoice.setAmount(invoiceRequestDto.getAmount());
		invoice.setDueDate(invoiceRequestDto.getDueDate());
		invoice.setStatus(AppConstants.VOID);

		logger.info("Saving incoice to DB");
		Invoice savedInvoice = invoiceRepository.save(invoice);
		
		SavedInvoiceResponseDto savedInvoiceResponseDto = new SavedInvoiceResponseDto();
		savedInvoiceResponseDto.setInvoiceId(savedInvoice.getInvoiceId());
		savedInvoiceResponseDto.setStatusCode(AppConstants.SAVE_SUCCESS_CODE);
		savedInvoiceResponseDto.setStatusMsg(AppConstants.SAVE_SUCCESS_MSG);

		return savedInvoiceResponseDto;
	}

	@Override
	public InvoiceResponseDto retriveInvoices() throws InvoiceNotFoundException {

		List<Invoice> invoiceList = invoiceRepository.findAll();

		if (invoiceList.isEmpty())
			throw new InvoiceNotFoundException(AppConstants.INVOICE_NOT_AVAILABLE);

		List<InvoiceDto> invoiceDtoList = new ArrayList<>();

		invoiceList.forEach(temp -> {
			InvoiceDto invoiceDto = new InvoiceDto();
			BeanUtils.copyProperties(temp, invoiceDto);
			invoiceDtoList.add(invoiceDto);

		});

		InvoiceResponseDto invoiceResponseDto = new InvoiceResponseDto();
		invoiceResponseDto.setInvoiceDtoList(invoiceDtoList);
		invoiceResponseDto.setStatusMsg(AppConstants.RETRIVE_SUCESS_MSG);
		invoiceResponseDto.setStatusCode(AppConstants.RETRIVE_SUCESS_CODE);

		return invoiceResponseDto;
	}

	@Override
	public ResponseDto updateInvoices(Long id, Double paidAmount)
			throws InvoiceNotFoundException, PaidamountGreterThanAmountException {

		Optional<Invoice> invoice = invoiceRepository.findById(id);

		if (!invoice.isPresent())
			throw new InvoiceNotFoundException(AppConstants.INVOICE_NOT_AVAILABLE);

		double previousAmount = invoice.get().getAmount();

		if (paidAmount < previousAmount) {
			invoice.get().setAmount(previousAmount - paidAmount);
			invoice.get().setPaidAmount(paidAmount);
			invoice.get().setStatus(AppConstants.PENDING);
		} else if (paidAmount == previousAmount) {
			invoice.get().setAmount(previousAmount - paidAmount);
			invoice.get().setPaidAmount(paidAmount);
			invoice.get().setStatus(AppConstants.PAID);
		} else {
			throw new PaidamountGreterThanAmountException(AppConstants.PAID_AMOUNT_GRETER);
		}

		invoiceRepository.save(invoice.get());

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusMsg(AppConstants.UPDATE_SUCCESS_MSG);
		responseDto.setStatusCode(AppConstants.UPDATE_SUCCESS_CODE);

		return responseDto;
	}

	@Override
	public ResponseDto processOverdueInvoice(@Valid ProcessInvoiceRequestDto processInvoiceRequestDto) {
	
		LocalDate currentDate= LocalDate.now();
		LocalDate overdueDate=currentDate.plusDays(processInvoiceRequestDto.getOverdueDays());
		
		List<Invoice> overdueInvoiceList = invoiceRepository.findByDueDateLessThanAndStatusNot(currentDate,AppConstants.PAID);
		
		if (!overdueInvoiceList.isEmpty())
		{
		overdueInvoiceList.forEach(invoice -> {
		        if (invoice.getStatus().equalsIgnoreCase(AppConstants.PENDING))
		        	invoice.setStatus(AppConstants.PAID);
	            invoiceRepository.save(invoice);
	            
	            Invoice newInvoice = new Invoice();
	            newInvoice.setAmount(invoice.getAmount()+processInvoiceRequestDto.getLateFee());
	            newInvoice.setDueDate(overdueDate); // Example for creating a new record
	            newInvoice.setStatus(AppConstants.VOID);// Example status for new record
	            invoiceRepository.save(newInvoice);
	        });
		}
		
		ResponseDto responseDto=new ResponseDto();
		responseDto.setStatusCode(AppConstants.PROCESS_SUCCESS_CODE);
		responseDto.setStatusMsg(AppConstants.PROCESS_SUCCESS_MSG);
		return responseDto;
	}

}
