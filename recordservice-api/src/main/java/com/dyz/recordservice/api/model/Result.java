package com.dyz.recordservice.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Result {

	@Builder.Default
	private int code = 1;
	
	@Builder.Default
	private String message = "Success";
	
	private Object content;
}
