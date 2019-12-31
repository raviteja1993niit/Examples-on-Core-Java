package com.psx.prime360ClientService.entity;

import java.util.Arrays;

public class InputMetaInfo {
	InputTableMetaInfo[] inputTableMetaInfos;
	
	public InputMetaInfo() {
	}

	public InputTableMetaInfo[] getInputTableMetaInfos() {
		return inputTableMetaInfos;
	}

	public void setInputTableMetaInfos(InputTableMetaInfo[] inputTableMetaInfos) {
		this.inputTableMetaInfos = inputTableMetaInfos;
	}

	@Override
	public String toString() {
		return "InputMetaInfo [inputTableMetaInfos=" + Arrays.toString(inputTableMetaInfos) + "]";
	}
	
	
}
