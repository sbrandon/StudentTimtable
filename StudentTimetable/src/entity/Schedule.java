package entity;

import java.util.*;

public class Schedule {
	
	private String name;
	private int credits = 0;
	private ArrayList<Offering> offerings = new ArrayList<Offering>();

	//Constructor
	public Schedule(String name) {
		this.name = name;
	}
	
	//Empty Constructor
	public Schedule(){
		
	}
	
	/*
	 * Getters and Setters
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public ArrayList<Offering> getOfferings() {
		return offerings;
	}

	public void setOfferings(ArrayList<Offering> offerings) {
		this.offerings = offerings;
	}
	
}
