package com.example.demo.Service;

import org.springframework.lang.NonNull;

public class Sales {
	double total;
	@NonNull
	String Item;
	double units;
	
	
	public Sales(double total, String item, double units) {
		super();
		this.total = total;
		Item = item;
		this.units = units;
	}

	public Sales() {
		// TODO Auto-generated constructor stub
	}

	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getItem() {
		return Item;
	}
	public void setItem(String item) {
		Item = item;
	}
	public double getUnits() {
		return units;
	}
	public void setUnits(double units) {
		this.units = units;
	}
	

}
