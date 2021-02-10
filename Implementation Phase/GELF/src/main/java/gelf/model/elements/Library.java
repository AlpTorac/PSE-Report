package main.java.gelf.model.elements;

import java.util.ArrayList;

public class Library extends HigherElement {
	private float[] index1;
    private float[] index2;
    private String path;
    private String[] fileData;
    private ArrayList<Cell> cells;
    private Stat defaultLeakage;
    
    public Library(String name, float[] index1, float[] index2, 
    		String path, ArrayList<Cell> cells) {
    	super.setName(name);
    	this.index1 = index1;
    	this.index2 = index2;
    	this.path = path;
    	this.cells = cells;
    }
    
	public float[] getIndex1() {
		return index1;
	}
	
	public void setIndex1(float[] index1) {
		this.index1 = index1;
	}
	
	public float[] getIndex2() {
		return index2;
	}
	
	public void setIndex2(float[] index2) {
		this.index2 = index2;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String[] getFileData() {
		return fileData;
	}
	
	public void setFileData(String[] fileData) {
		this.fileData = fileData;
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}
	
	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
	
	public Stat getDefaultLeakage() {
		return defaultLeakage;
	}
	
	public void setDefaultLeakage(Stat defaultLeakage) {
		this.defaultLeakage = defaultLeakage;
	}

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}
}
