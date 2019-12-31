package com.psx.prime360ClientService.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.psx.prime360ClientService.entity.ColMetaDataInfo;
import com.psx.prime360ClientService.entity.InputTableMetaInfo;


public class Prime360ProductServiceUtils {
	

	public static Object[] buildData(String csvContent) {
		HashMap<String, List<ColMetaDataInfo>> data = new HashMap<String, List<ColMetaDataInfo>>();
		HashMap<String, ColMetaDataInfo> slColMetadataMap = new HashMap<String, ColMetaDataInfo>();

		String tableName = null;
		List<String> tableAliasName=new ArrayList<>();
		String[] lines = csvContent.split(";");
		String[] header = lines[0].split(",");
		for (int lc = 1; lc < lines.length; lc++) {
			// splitting by considering empty trailing characters in the end
			String[] recordArray = lines[lc].split(",", -1);
			HashMap<String, String> record = new HashMap<String, String>();
			for (int i = 0; i < recordArray.length; i++) {
				record.put(header[i].toUpperCase(), recordArray[i]!=null?recordArray[i].trim():recordArray[i]);
			}
			tableName = record.get("TABLE_NAME");
			if(!tableAliasName.isEmpty()) {
				if(!(tableAliasName.indexOf(record.get("TABLE_ALIAS_NAME"))>-1)) {
					tableAliasName.add(record.get("TABLE_ALIAS_NAME"));
				}
			}else {
				tableAliasName.add(record.get("TABLE_ALIAS_NAME"));
			}
			
			ColMetaDataInfo columnInfo = new ColMetaDataInfo();
			columnInfo.setSlNo(record.get("SL"));
			columnInfo.setColumnName(record.get("COLUMN_NAME"));
			columnInfo.setDataType(record.get("DATA_TYPE"));
			columnInfo.setNullable(record.get("NULLABLE"));
			
			columnInfo.setIsPrimarykey(record.get("ISPRIMARYKEY"));
			columnInfo.setIsDedupe(record.get("ISDEDUPE"));
			columnInfo.setDedupeType(record.get("DEDUPETYPE"));
			columnInfo.setCleanProfile(record.get("CLEANPROFILE"));
			columnInfo.setDisplayName(record.get("DISPLAYNAME"));
			columnInfo.setAssociationParameters(
					record.get("ASSOCIATIONPARAMETERS") != null && !(record.get("ASSOCIATIONPARAMETERS").isEmpty())
							? record.get("ASSOCIATIONPARAMETERS").split("~")
							: null);

			
			if (data.get(tableName) != null) {
				List<ColMetaDataInfo> value = data.get(tableName);
				value.add(columnInfo);
				data.put(tableName, value);
			} else {
				List<ColMetaDataInfo> newTableInfo = new ArrayList<ColMetaDataInfo>();
				newTableInfo.add(columnInfo);
				data.put(tableName, newTableInfo);
			}
			slColMetadataMap.put(record.get("SL"), columnInfo);
		}
		Set<String> colmetaDataSet = data.keySet();
		Iterator<String> tempMetadataItr = colmetaDataSet.iterator();
		InputTableMetaInfo[] inputTablemetainfoArray = new InputTableMetaInfo[colmetaDataSet.size()];
		int index = 0;
		while (tempMetadataItr.hasNext()) {
			String tabName = tempMetadataItr.next();
			List<ColMetaDataInfo> value = data.get(tabName);
			List<String> colNames = new ArrayList<String>();
			for (ColMetaDataInfo colmetadataInfo : value) {
				colNames.add(colmetadataInfo.getColumnName());
			}

			InputTableMetaInfo ipInfo = new InputTableMetaInfo();
			ipInfo.setTableName(tabName);
			ipInfo.setTableAliasName(tableAliasName.get(index));
			Collections.sort(value, new Comparator<ColMetaDataInfo>() {
				@Override
				public int compare(ColMetaDataInfo o1, ColMetaDataInfo o2) {
					return Integer.parseInt(o1.getSlNo()) - Integer.parseInt(o2.getSlNo());
				}
			});					
			ipInfo.setColNames(colNames);
			inputTablemetainfoArray[index] = ipInfo;
			index++;

		}
		return new Object[] { inputTablemetainfoArray, slColMetadataMap };

	}

		
	public static void writeFile(String fileName, String content, boolean append) throws IOException {
		File file = new File(fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(fileName, append);
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bw.close();
		fw.close();
	}


	

	public static Date getFormattedDate(Long timeMillis) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		calendar.setTimeInMillis(timeMillis);
		String stringDate = dateFormat.format(calendar.getTime());
		Date d = new Date();
		try {
			d= dateFormat.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d ;
	}
	

	
}