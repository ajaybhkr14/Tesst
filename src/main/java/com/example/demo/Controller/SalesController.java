package com.example.demo.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Exception.ApiException;
import com.example.demo.Exception.HandleException;
import com.example.demo.Service.SalesService;
import com.example.demo.Service.Sales;

@RestController
public class SalesController {
	@Autowired
	SalesService robotService;
	

	@PostMapping("/sales/by_name")
	public ResponseEntity<Sales> sales (@RequestParam("file") List<Sales> sale,@RequestParam("date") String date)  {
		try {
			Sales sales= robotService.getItemsByItem(date,sale);
				return ResponseEntity.status(HttpStatus.OK).body(sales);
			} catch (Exception e) {
			throw new ApiException(e.getMessage());
		}
	}
	
	@PostMapping("/sales/by_date")
	public ResponseEntity<Sales> upload (@RequestParam("file") MultipartFile file,@RequestParam("date") Date date)  {
		try {
			Sales sales= robotService.getItemsByDate(date,file);
				return ResponseEntity.status(HttpStatus.OK).body(sales);
			} catch (Exception e) {
			throw new ApiException(e.getMessage());
		}
	
		
	}
	@PostMapping("/revenue/by/region")
	public String  getRvenueByRegion(@RequestParam("file") MultipartFile file) {
		try {
			return robotService.getRevenueByRegion(file);
		} catch (Exception e) {
			throw new ApiException(e.getMessage());
		}
	}
	
	@PostMapping("revenue/by/region/name")
	public String  getRvenueByRegion(@RequestParam("file") MultipartFile file,@RequestParam("name") String name) {
		try {
			return robotService.getRevenueByRegionName(file,name);
		} catch (Exception e) {
			throw new ApiException(e.getMessage());
			}
	}	
}
