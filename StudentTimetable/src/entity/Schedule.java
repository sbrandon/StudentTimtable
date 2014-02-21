package entity;

import java.util.*;
import java.sql.*;

import dao.ConnectionFactory;

public class Schedule {
	
	private String name;
	private int credits = 0;
	
	//Constructor
	public Schedule(String name) {
		this.name = name;
	}
	
	//Empty Constructor
	public Schedule(){
		
	}
	
	/*
	public String toString() {
		return "Schedule " + name + ": " + schedule;
	}
	*/
	
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
	
}
