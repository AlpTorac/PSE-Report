package gelf.model.elements;

import java.util.ArrayList;

import gelf.model.elements.attributes.PowerGroup;

public abstract class Pin extends Element {
	 protected Cell parentCell;
     protected float capacitance;
     protected ArrayList<PowerGroup> availablePower;
     
     public Pin() {
    	 
     }
     public Cell getParent() {
    	 return parentCell;
     }
     
     public void setParent(Cell parentCell) {
    	 
     }
     public float getCapacitance() {
    	 return capacitance;
     }
     public void setCapacitance(float capacitance) {
    	 
     }
     public ArrayList<PowerGroup> getAvailablePower() {
    	 return availablePower;
     }
     
     public void setAvailablePower(ArrayList<PowerGroup> availablePower) {
    	 
     }
     
     @Override
 	public String getName() {
 		return super.getName();
 	}
 	
 	@Override
 	public void setName(String name) {
 		super.setName(name);
 	}
 	
 	@Override
 	public boolean getFiltered() {
 		return super.getFiltered();
 	}
 	
 	@Override
 	public void setFiltered(boolean filtered) {
 		super.setFiltered(filtered);
 	}
 	
 	@Override
 	public boolean getSearched() {
 		return super.getSearched();
 	}
 	
 	@Override
 	public void setSearched(boolean searched) {
 		super.setSearched(searched);
 	}
     
}

