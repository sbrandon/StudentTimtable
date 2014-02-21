/*
 * OfferingImpl.java
 * Manages business logic methods for offering objects.
 */
package implementation;

import entity.Offering;

public class OfferingImpl {
	
	public String toString(Offering offering){
		return "Offering " + offering.getId() + ": " + offering.getCourse() + " meeting " + offering.getDaysTimes();
	}
	
}
