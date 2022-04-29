package com.example.demo.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Exception.ApiException;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class SalesService {

	
	@Autowired
	ObjectMapper mapper;
	


	public Sales getItemsByItem(String itemName,List<Sales> list ) throws Exception {
System.out.println("fdsaf   d");	
		Sales sales =new Sales();

	for(Sales it:list ){
			if(itemName.equals(it.Item)) {
			sales =it;
			break;
			}
		}
		if(sales.Item ==null) {throw new ApiException("Not found ,no data present in excel sheet against given name ->"+ itemName);}
		return sales;
	}
	
	public Sales getItemsByDate(Date date,MultipartFile file ) throws Exception {
	
		Sales sales =new Sales();
		Path tempDir =Files.createTempDirectory("");
		File t =tempDir.resolve(file.getOriginalFilename()).toFile();
		file.transferTo(t);
		Workbook book = WorkbookFactory.create(t);
		Sheet sheet =book.getSheetAt(0);
		Stream<Row> st =StreamSupport.stream(sheet.spliterator(), false);
		st.skip(1).forEach(row ->{
			if(date.equals(row.getCell(0).getDateCellValue())) {
					sales.setItem(row.getCell(3).getStringCellValue() );
					sales.setUnits(row.getCell(4).getNumericCellValue());
					sales.setTotal(row.getCell(6).getNumericCellValue());
			}
		});
		if(sales.Item ==null) {throw new ApiException("Not found ,no data present in excel sheet against given date ->"+ date.toString());}
		return sales;
	}
	
	public String getRevenueByRegion(MultipartFile file ) throws Exception {
		HashMap<String, Double> map =new HashMap<String, Double>();
		Path tempDir =Files.createTempDirectory("");
		File t =tempDir.resolve(file.getOriginalFilename()).toFile();
		file.transferTo(t);
		Workbook book = WorkbookFactory.create(t);
		Sheet sheet =book.getSheetAt(0);
		Stream<Row> st =StreamSupport.stream(sheet.spliterator(), false);
		st.skip(1).forEach(row ->{
			double rev;
			if(map.containsKey(row.getCell(1).getStringCellValue())) {
			rev =(double) map.get(row.getCell(1).getStringCellValue());	
			rev =rev+row.getCell(6).getNumericCellValue();
				}
			else {rev =row.getCell(6).getNumericCellValue(); }
			if(rev>0.0) {
			map.put(row.getCell(1).getStringCellValue(),rev);
			}
		});
	
		return map.toString();
	}
	
	public String getRevenueByRegionName(MultipartFile file ,String name) throws Exception {
		ArrayNode arr= mapper.createArrayNode();
		Path tempDir =Files.createTempDirectory("");
		File t =tempDir.resolve(file.getOriginalFilename()).toFile();
		file.transferTo(t);
		Workbook book = WorkbookFactory.create(t);
		Sheet sheet =book.getSheetAt(0);
		Stream<Row> st =StreamSupport.stream(sheet.spliterator(), false);
		
		st.skip(1).forEach(row ->{
			double revenue=row.getCell(6).getNumericCellValue();
			if(name.equals(row.getCell(1).getStringCellValue())) {
			
			ObjectNode obj =mapper.createObjectNode();
			obj.put("revenue", revenue);
			obj.put("date",row.getCell(0).getDateCellValue().toString());
			arr.add(obj);
			}
			});
		if(arr.isEmpty()) {throw new ApiException("Not found, no data present in excel sheet for region name ->"+name);}
		return arr.toPrettyString();
	}
	
	
	
		
}
