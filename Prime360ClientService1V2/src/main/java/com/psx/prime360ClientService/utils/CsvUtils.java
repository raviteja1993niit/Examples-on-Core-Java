package com.psx.prime360ClientService.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.psx.prime360ClientService.entity.Customer;

public class CsvUtils {

	private final Log logger = LogFactory.getLog(this.getClass());

	
	public static <T> List<T> read(Class<Customer> class1, java.io.InputStream inputStream) throws IOException {
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(class1).withHeader().withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(class1).with(schema);
	
		return reader.<T>readValues(inputStream).readAll();
	}

}
