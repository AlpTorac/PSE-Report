package gelf.model.elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gelf.model.compilers.LibertyCompiler;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingKey;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.project.FileManager;

public class Library extends HigherElement {
	private float[] index1;
    private float[] index2;
    private String path;
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private Stat defaultLeakage;
    private File libraryFile;
    
    public Library(String name, float[] index1, float[] index2, 
    		String path, ArrayList<Cell> cells) {
    	super.setName(name);
    	this.index1 = index1;
    	this.index2 = index2;
    	this.path = path;
    	this.cells = cells;
    	
    	this.setAvailableInputPower();
    	this.setAvailableOutputPower();
    	this.setAvailableTimGr();
    	this.setAvailableTimSen();
    	this.setAvailableTimType();
    }
    
	/**
	 * Returns a deep copy of the Library object
	 * @return the deep copy of the Library object
	 * @author Xhulio Pernoca
	 */
	@Override
	public Library clone() {
    	ArrayList<Cell> clonedCells = new ArrayList<Cell>();
    	Iterator<Cell> cellsIt = cells.iterator();
    	while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			clonedCells.add(curCell.clone());
		}
    	Library clonedLibrary = new Library(name, index1, index2, path, clonedCells);
    	cellsIt = clonedCells.iterator();
    	while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			curCell.setParentLibrary(clonedLibrary);
		}
    	return clonedLibrary;
    }

	/**
	 * Replaces all the attributes that change while reparsing, so that there is no need
	 * to change reference from the object in the view
	 * @param dataLib the library object with the necessary data
	 * @author Xhulio Pernoca
	 */
	public void replaceData(Library dataLib) {
        setName(dataLib.getName());
        setSearched(dataLib.getSearched());
		setHasShownElements(dataLib.isHasShownElements());
    	setCells(dataLib.getCells());
        setIndex1(dataLib.getIndex1());
	    setIndex2(dataLib.getIndex2());
        setElementContent(dataLib.getElementContent());
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
		if (cells == null) {
			return;
		}
		float min = cells.get(0).getLeakage().getMin();
		float max = cells.get(0).getLeakage().getMax();
		float avg = 0;
		float med = 0;
		
		// traverse the cells to calculate leakage stats of the library
		Iterator<Cell> cellsIt = cells.iterator();
		while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			min = Math.min(min, curCell.leakage.getMin());
			max = Math.max(max, curCell.leakage.getMax());
			avg += curCell.leakage.getAvg();
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
		inPowerStat = new HashMap<PowerGroup, Stat>();
		Iterator<PowerGroup> avPowGrIt = availableInputPower.iterator();
		
		
		while(avPowGrIt.hasNext()) {
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put Stats of the same PowerGroup in the same place
			ArrayList<Stat> toCalc = new ArrayList<Stat>();
			
			Iterator<Cell> cellsIt = cells.iterator();
			while(cellsIt.hasNext()) {
				for (Map.Entry<PowerGroup, Stat> entry : cellsIt.next().
						inPowerStat.entrySet())  
		            if(entry.getKey() == curPowGr) {
		            	toCalc.add(entry.getValue());
		            }
			}
			if(toCalc.isEmpty()) {
				return;
			}
			float min = toCalc.get(0).getMin();
			float max = toCalc.get(0).getMax();
			float avg = 0;
			float med = 0;
			
			Iterator<Stat> toCalcIt = toCalc.iterator();
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
		outPowerStat = new HashMap<PowerGroup, Stat>();
		Iterator<PowerGroup> avPowGrIt = availableOutputPower.iterator();
		
		
		
		
		while(avPowGrIt.hasNext()) {
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put Stats of the same PowerGroup in the same place
			ArrayList<Stat> toCalc = new ArrayList<Stat>();
			
			Iterator<Cell> cellsIt = cells.iterator();
			while(cellsIt.hasNext()) {
				for (Map.Entry<PowerGroup, Stat> entry : cellsIt.next().
						outPowerStat.entrySet())  
		            if(entry.getKey() == curPowGr) {
		            	toCalc.add(entry.getValue());
		            }
			}
			if(toCalc.isEmpty()) {
				return;
			}
			float min = toCalc.get(0).getMin();
			float max = toCalc.get(0).getMax();
			float avg = 0;
			float med = 0;
			
			Iterator<Stat> toCalcIt = toCalc.iterator();
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
		timingStat = new HashMap<TimingKey, Stat>();
		Iterator<TimingSense> avTimSenIt = availableTimSen.iterator();
		
		
		while(avTimSenIt.hasNext()) {
			TimingSense curTimSen = avTimSenIt.next();
			
			Iterator<TimingType> avTimTypeIt = availableTimType.iterator();
			
			while(avTimTypeIt.hasNext()) {
				TimingType curTimType = avTimTypeIt.next();
				
				Iterator<TimingGroup> avTimGrIt = availableTimGr.iterator();
				
				while(avTimGrIt.hasNext()) {
					TimingGroup curTimGr = avTimGrIt.next();
					
					// ArrayList toCalc to put Stats of the same Timing in the same place
					ArrayList<Stat> toCalc = new ArrayList<Stat>();
					
					Iterator<Cell> cellsIt = cells.iterator();
					while(cellsIt.hasNext()) {
						for (Map.Entry<TimingKey, Stat> entry : cellsIt.next().
								timingStat.entrySet())  
				            if(entry.getKey().getTimSense() == curTimSen &&
				            entry.getKey().getTimGroup() == curTimGr &&
				            entry.getKey().getTimType() == curTimType) {
				            	toCalc.add(entry.getValue());
				            }
					}
					if(toCalc.isEmpty()) {
						return;
					}
					float min = toCalc.get(0).getMin();
					float max = toCalc.get(0).getMax();
					float avg = 0;
					float med = 0;
					
					Iterator<Stat> toCalcIt = toCalc.iterator();
					// calculate the stats for the desired Timing
					while(toCalcIt.hasNext()) {
						Stat curStat = toCalcIt.next();
						min = Math.min(min, curStat.getMin());
						max = Math.max(max, curStat.getMax());
						avg += curStat.getAvg();
					}
					avg = avg / (float) toCalc.size();
					
					Stat stat = new Stat(min, max, avg, med);
					TimingKey key = new TimingKey(curTimSen, curTimType, curTimGr);
					this.timingStat.put(key, stat);
				}
			}	
		}
	}
	
	public void calculateDefaultLeakage() {
		Iterator<Cell> cellsIt = cells.iterator();
		
		ArrayList<Float> toCalc = new ArrayList<Float>();
		
		while(cellsIt.hasNext()) {
			toCalc.add(cellsIt.next().getDefaultLeakage());
		}
		if(toCalc.isEmpty()) {
			return;
		}
		float min = toCalc.get(0);
		float max = toCalc.get(0);
		float avg = 0;
		float med = 0;
		
		Iterator<Float> toCalcIt = toCalc.iterator();
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
	
	public void saveLibrary() throws IOException {
		FileManager.saveFileToPath(LibertyCompiler.compile(this), ".lib", path);
	}
	
	public void saveLibraryAs() throws IOException {
		FileManager.saveFile(LibertyCompiler.compile(this), ".lib");
	}

	@Override
	public void setAvailableTimSen() {
		if (cells == null) {
			return;
		}
		Iterator<Cell> cellsIt = cells.iterator();
		while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			Iterator<TimingSense> cellsTimSenIt = curCell.getAvailableTimSen()
					.iterator();
			
			while(cellsTimSenIt.hasNext()) {
				TimingSense curTimSen = cellsTimSenIt.next();
				if(!availableTimSen.contains(curTimSen)) {
					availableTimSen.add(curTimSen);
				}
			}
		}	
		
	}

	@Override
	public void setAvailableTimGr() {
		if (cells == null) {
			return;
		}
		Iterator<Cell> cellsIt = cells.iterator();
		
		while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			Iterator<TimingGroup> cellsTimGrIt = curCell.getAvailableTimGr()
					.iterator();
			
			while(cellsTimGrIt.hasNext()) {
				TimingGroup curTimGr = cellsTimGrIt.next();
				if(!availableTimGr.contains(curTimGr)) {
					availableTimGr.add(curTimGr);
				}
			}
		}	
	}

	@Override
	public void setAvailableTimType() {
		if (cells == null) {
			return;
		}
		Iterator<Cell> cellsIt = cells.iterator();
		
		while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			Iterator<TimingType> cellsTimTypeIt = curCell.getAvailableTimType()
					.iterator();
			
			while(cellsTimTypeIt.hasNext()) {
				TimingType curTimType = cellsTimTypeIt.next();
				if(!availableTimType.contains(curTimType)) {
					availableTimType.add(curTimType);
				}
			}
		}		
		
	}

	@Override
	public void setAvailableOutputPower() {
		if (cells == null) {
			return;
		}
		Iterator<Cell> cellsIt = cells.iterator();
		
		while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			Iterator<PowerGroup> cellsOutPowIt = curCell.getAvailableOutputPower()
					.iterator();
			
			while(cellsOutPowIt.hasNext()) {
				PowerGroup curPowGr = cellsOutPowIt.next();
				if(!availableOutputPower.contains(curPowGr)) {
					availableOutputPower.add(curPowGr);
				}
			}
		}		
		
	}

	@Override
	public void setAvailableInputPower() {
		if (cells == null) {
			return;
		}
		Iterator<Cell> cellsIt = cells.iterator();
		
		while(cellsIt.hasNext()) {
			Cell curCell = cellsIt.next();
			Iterator<PowerGroup> cellsInPowIt = curCell.getAvailableInputPower()
					.iterator();
			
			while(cellsInPowIt.hasNext()) {
				PowerGroup curPowGr = cellsInPowIt.next();
				if(!availableInputPower.contains(curPowGr)) {
					availableInputPower.add(curPowGr);
				}
			}
		}		
	}
	
	
}
