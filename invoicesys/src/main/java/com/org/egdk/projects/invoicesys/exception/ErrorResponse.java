package com.org.egdk.projects.invoicesys.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	Long errorCode;
	List<String> errorMessage;

}
