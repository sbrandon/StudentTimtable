package implementation;

import entity.Offering;

public class OfferingImpl {
	
	public String toString(Offering offering){
		return "Offering " + offering.getId() + ": " + offering.getCourse() + " meeting " + offering.getDaysTimes();
	}
	
}
