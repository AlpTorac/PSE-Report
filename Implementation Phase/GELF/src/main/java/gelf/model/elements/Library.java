package gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import gelf.model.elements.attributes.PowerGroup;

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
		calculateLeakage();
		calculateInPow();
		calculateOutPow();
		calculateTiming();
		calculateDefaultLeakage();
	}
	
	public void calculateLeakage() {
		float min = 0;
		float max = 0;
		float avg = 0;
		float med = 0;
		
		// traverse the cells to calculate leakage stats of the library
		Iterator<Cell> cellsIt = cells.iterator();
		while(cellsIt.hasNext()) {
			min = Math.min(min, cellsIt.next().leakage.getMin());
			max = Math.max(max, cellsIt.next().leakage.getMax());
			avg += cellsIt.next().leakage.getAvg();
		}
		avg = avg / (float)cells.size();
		
		if (leakage != null) {
			leakage.setAvg(avg);
			leakage.setMax(max);
			leakage.setMin(min);
			leakage.setMed(med);
		}
		else {
			leakage = new Stat(min, max, avg, med);
		}	
	}
	
	public void calculateInPow() {
		
		Iterator<PowerGroup> avPowGrIt = availableInputPower.iterator();
		
		Iterator<Cell> cellsIt = cells.iterator();
		
		
		
		while(avPowGrIt.hasNext()) {
			
			// ArrayList toCalc to put Stats of the same PowerGroup in the same place
			ArrayList<Stat> toCalc = new ArrayList<Stat>();
			Iterator<Stat> toCalcIt = toCalc.iterator();
			
			while(cellsIt.hasNext()) {
				for (Map.Entry<PowerGroup, Stat> entry : cellsIt.next().
						inPowerStat.entrySet())  
		            if(entry.getKey() == avPowGrIt.next()) {
		            	toCalc.add(entry.getValue());
		            }
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				min = Math.min(min, toCalcIt.next().getMin());
				max = Math.max(max, toCalcIt.next().getMax());
				avg += toCalcIt.next().getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			inPowerStat.put(avPowGrIt.next(), stat);	
		}
	}
	
	public void calculateOutPow() {
		Iterator<PowerGroup> avPowGrIt = availableOutputPower.iterator();
		
		Iterator<Cell> cellsIt = cells.iterator();
		
		
		
		while(avPowGrIt.hasNext()) {
			
			// ArrayList toCalc to put Stats of the same PowerGroup in the same place
			ArrayList<Stat> toCalc = new ArrayList<Stat>();
			Iterator<Stat> toCalcIt = toCalc.iterator();
			
			while(cellsIt.hasNext()) {
				for (Map.Entry<PowerGroup, Stat> entry : cellsIt.next().
						outPowerStat.entrySet())  
		            if(entry.getKey() == avPowGrIt.next()) {
		            	toCalc.add(entry.getValue());
		            }
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				min = Math.min(min, toCalcIt.next().getMin());
				max = Math.max(max, toCalcIt.next().getMax());
				avg += toCalcIt.next().getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			outPowerStat.put(avPowGrIt.next(), stat);	
		}
	}
	
	public void calculateTiming() {
		
	}
	
	public void calculateDefaultLeakage() {
		
	}
	
	public static void saveLibrary() {
		
	}
	
	public static void saveLibraryAs() {
		
	}
	
	
}
