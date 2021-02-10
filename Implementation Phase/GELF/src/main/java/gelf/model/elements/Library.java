package gelf.model.elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingKey;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

public class Library extends HigherElement {
	private float[] index1;
    private float[] index2;
    private String path;
    private String[] fileData;
    private ArrayList<Cell> cells;
    private Stat defaultLeakage;
    private File libraryFile;
    
    
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
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put Stats of the same PowerGroup in the same place
			ArrayList<Stat> toCalc = new ArrayList<Stat>();
			Iterator<Stat> toCalcIt = toCalc.iterator();
			
			while(cellsIt.hasNext()) {
				for (Map.Entry<PowerGroup, Stat> entry : cellsIt.next().
						inPowerStat.entrySet())  
		            if(entry.getKey() == curPowGr) {
		            	toCalc.add(entry.getValue());
		            }
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				Stat curStat = toCalcIt.next();
				min = Math.min(min, curStat.getMin());
				max = Math.max(max, curStat.getMax());
				avg += curStat.getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			inPowerStat.put(curPowGr, stat);	
		}
	}
	
	public void calculateOutPow() {
		Iterator<PowerGroup> avPowGrIt = availableOutputPower.iterator();
		
		Iterator<Cell> cellsIt = cells.iterator();
		
		
		
		while(avPowGrIt.hasNext()) {
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put Stats of the same PowerGroup in the same place
			ArrayList<Stat> toCalc = new ArrayList<Stat>();
			Iterator<Stat> toCalcIt = toCalc.iterator();
			
			while(cellsIt.hasNext()) {
				for (Map.Entry<PowerGroup, Stat> entry : cellsIt.next().
						outPowerStat.entrySet())  
		            if(entry.getKey() == curPowGr) {
		            	toCalc.add(entry.getValue());
		            }
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				Stat curStat = toCalcIt.next();
				min = Math.min(min, curStat.getMin());
				max = Math.max(max, curStat.getMax());
				avg += curStat.getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			outPowerStat.put(curPowGr, stat);	
		}
	}
	
	public void calculateTiming() {
		Iterator<TimingSense> avTimSenIt = availableTimSen.iterator();
		Iterator<TimingGroup> avTimGrIt = availableTimGr.iterator();
		Iterator<TimingType> avTimTypeIt = availableTimType.iterator();
		
		Iterator<Cell> cellsIt = cells.iterator();
		
		
		
		while(avTimSenIt.hasNext()) {
			TimingSense curTimSen = avTimSenIt.next();
			while(avTimGrIt.hasNext()) {
				TimingGroup curTimGr = avTimGrIt.next();
				while(avTimTypeIt.hasNext()) {
					TimingType curTimType = avTimTypeIt.next();
					
					// ArrayList toCalc to put Stats of the same Timing in the same place
					ArrayList<Stat> toCalc = new ArrayList<Stat>();
					Iterator<Stat> toCalcIt = toCalc.iterator();
					
					while(cellsIt.hasNext()) {
						for (Map.Entry<TimingKey, Stat> entry : cellsIt.next().
								timingStat.entrySet())  
				            if(entry.getKey().getTimSense() == curTimSen &&
				            entry.getKey().getTimGroup() == curTimGr &&
				            entry.getKey().getTimType() == curTimType) {
				            	toCalc.add(entry.getValue());
				            }
					}
					
					float min = 0;
					float max = 0;
					float avg = 0;
					float med = 0;
					
					// calculate the stats for the desired Timing
					while(toCalcIt.hasNext()) {
						Stat curStat = toCalcIt.next();
						min = Math.min(min, curStat.getMin());
						max = Math.max(max, curStat.getMax());
						avg += curStat.getAvg();
					}
					avg = avg / (float) toCalc.size();
					
					Stat stat = new Stat(min, max, avg, med);
					TimingKey key = new TimingKey(curTimSen, curTimGr, curTimType);
					this.timingStat.put(key, stat);
				}
			}	
		}
	}
	
	public void calculateDefaultLeakage() {
		Iterator<Cell> cellsIt = cells.iterator();
		
		ArrayList<Float> toCalc = new ArrayList<Float>();
		Iterator<Float> toCalcIt = toCalc.iterator();
		
		while(cellsIt.hasNext()) {
			toCalc.add(cellsIt.next().getDefaultLeakage());
		}
		float min = 0;
		float max = 0;
		float avg = 0;
		float med = 0;
		
		// calculate the stats for the desired Timing
		while(toCalcIt.hasNext()) {
			float curLeak = toCalcIt.next();
			min = Math.min(min, curLeak);
			max = Math.max(max, curLeak);
			avg += curLeak;
		}
		avg = avg / (float) toCalc.size();
		
		defaultLeakage = new Stat(min, max, avg, med);
	}

	public File getLibraryFile() {
		return libraryFile;
	}

	public void setLibraryFile(File libraryFile) {
		this.libraryFile = libraryFile;
	}
	
	public void saveLibrary() {
		FileManager.saveFile(super.getName(), fileData, path);
	}
	
	public void saveLibraryAs() {
	    /*
		 JFileChooser chooser = new JFileChooser(); 
		 chooser.setCurrentDirectory(new java.io.File("."));
		 chooser.setDialogTitle("Select a directory to save the library");
		 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 chooser.setAcceptAllFileFilterUsed(false);

		 if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			 System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
		     System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			 String dirName = chooser.getCurrentDirectory().getPath();
			 File dir = new File (dirName);
			 // not sure what to do after this
			 File actualFile = new File (dir, libraryFile.getPath());
			 Writer output = null;
		     output = new BufferedWriter(new FileWriter(actualFile));
		     output.close();
		 } 
		 else {
		      System.out.println("No Selection ");
		 }
		 */
		FileManager.saveFile(super.getName(), fileData, path);
	}
	
	
}
