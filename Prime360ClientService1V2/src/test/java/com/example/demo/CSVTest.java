package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.psx.core.join.util.VDNDataProvider;
import com.psx.core.join.util.VirtualDenormalizer;
import com.psx.prime360ClientService.customersearch.CustomerSearchDTO;
import com.psx.prime360ClientService.customersearch.CustomerSearchParameter;
import com.psx.prime360ClientService.customersearch.CustomerSearchTableDTO;

/**
 *
 * @author jayantronald
 *
 */

public class CSVTest {

	public static void main(String[] args) throws Exception {

		String[] fileNames=new String[] {"sampleData/FileA.csv","sampleData/FileB.csv"};
		String[] tableTags=new String[] {"DG","ADDRESS"};
		String[] rowDist=new String[tableTags.length];
		Arrays.fill(rowDist, "DEFAULT");
		String primaryKey="PSX_ID";

		VDNDataProvider[] provider=VDNDataProvider.getFileVDNDataProviders(0, 1, fileNames,primaryKey, 1, false, tableTags,rowDist,System.currentTimeMillis());
		VirtualDenormalizer virtualDenormalizer=new VirtualDenormalizer(provider);
		HashMap<String,ArrayList<HashMap<String,Object>>> buffer=null;
		while((buffer=virtualDenormalizer.getNextRecordSets())!=null) {
			CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO();
			List<CustomerSearchTableDTO> listOfCustomerSearchTableDTO = new ArrayList<CustomerSearchTableDTO>();
			
			for (Entry<String, ArrayList<HashMap<String, Object>>> entry : buffer.entrySet()) {
				CustomerSearchTableDTO customerSearchTableDTO = new CustomerSearchTableDTO();
				customerSearchTableDTO.setTableName(entry.getKey());
				
				List<CustomerSearchParameter> customerSearchParameters = new ArrayList<CustomerSearchParameter>();
	            for (HashMap<String,Object> row : entry.getValue()) {
	            	for (Entry<String, Object> entry1 : row.entrySet()) {
	            		CustomerSearchParameter customerSearchParameter = new CustomerSearchParameter();
	            		customerSearchParameter.setEngineName(entry1.getKey());
	            		customerSearchParameter.setParameterValue(entry1.getValue().toString());
	            		customerSearchParameters.add(customerSearchParameter);
	            	}
	            }
	            customerSearchTableDTO.setCustomerSearchParameters(customerSearchParameters);
	            listOfCustomerSearchTableDTO.add(customerSearchTableDTO);
	        }
			customerSearchDTO.setTables(listOfCustomerSearchTableDTO);
			System.out.println(customerSearchDTO.toJSON());
			//call submitCustomerSearchRequest()
		}
	}

}
