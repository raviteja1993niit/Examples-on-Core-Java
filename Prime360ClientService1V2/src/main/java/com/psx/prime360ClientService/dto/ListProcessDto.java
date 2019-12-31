package com.psx.prime360ClientService.dto;

import java.util.List;


public class ListProcessDto {
	
	private List<ProcessDto> processDtolst;

	public List<ProcessDto> getProcessDtolst() {
		return processDtolst;
	}

	public void setProcessDtolst(List<ProcessDto> processDtolst) {
		this.processDtolst = processDtolst;
	}

	@Override
	public String toString() {
		return "ListProcessDto [processDtolst=" + processDtolst + "]";
	}
	
	

}
