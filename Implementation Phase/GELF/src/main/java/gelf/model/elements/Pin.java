package gelf.model.elements;

import java.util.ArrayList;

import gelf.model.elements.attributes.PowerGroup;

public abstract class Pin extends Element {
	 protected Cell parentCell;
     protected ArrayList<PowerGroup> availablePower 
     	= new ArrayList<PowerGroup>();
     
     public Pin() {	 
    	 
     }
     
     public Cell getParent() {
    	 return parentCell;
     }
     
     public void setParent(Cell parentCell) {
    	 this.parentCell = parentCell;
     }
     
     public ArrayList<PowerGroup> getAvailablePower() {
    	 return availablePower;
     }
     
     public abstract void setAvailablePower();
     
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
 	
 	@Override
	public String getElementContent() {
		return super.getElementContent();
	}
	
	@Override
	public void setElementContent(String content) {
		super.setElementContent(content);
	}
	
	public String getInnerPath() {
		return parentCell.getInnerPath() + "/" + name;
	}
     
}

