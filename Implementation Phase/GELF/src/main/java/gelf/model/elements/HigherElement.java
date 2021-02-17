package gelf.model.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingKey;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

public abstract class HigherElement extends Element {
	protected ArrayList<TimingSense> availableTimSen = 
			new ArrayList<TimingSense>();
    protected ArrayList<TimingGroup> availableTimGr =
    		new ArrayList<TimingGroup>();
    protected ArrayList<TimingType> availableTimType =
    		new ArrayList<TimingType>();
    protected ArrayList<PowerGroup> availableOutputPower = 
    		new ArrayList<PowerGroup>();
    protected ArrayList<PowerGroup> availableInputPower =
    		new ArrayList<PowerGroup>();
    // protected Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> timingStat;
    protected Map<TimingKey, Stat> timingStat = 
    		new HashMap<TimingKey, Stat>();
    protected Map<PowerGroup, Stat> inPowerStat = 
    		new HashMap<PowerGroup, Stat>();
    protected Map<PowerGroup, Stat> outPowerStat =
    		new HashMap<PowerGroup, Stat>();
    protected boolean hasShownElements;
    protected Stat leakage;
    
	public ArrayList<TimingSense> getAvailableTimSen() {
		return availableTimSen;
	}
	
	public abstract void setAvailableTimSen();
	
	public ArrayList<TimingGroup> getAvailableTimGr() {
		return availableTimGr;
	}
	
	public abstract void setAvailableTimGr();
	
	public ArrayList<TimingType> getAvailableTimType() {
		return availableTimType;
	}
	
	public abstract void setAvailableTimType();
	
	public ArrayList<PowerGroup> getAvailableOutputPower() {
		return availableOutputPower;
	}
	
	public abstract void setAvailableOutputPower();
	
	public ArrayList<PowerGroup> getAvailableInputPower() {
		return availableInputPower;
	}
	
	public abstract void setAvailableInputPower();
/**
	public Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> getTimingStat() {
		return timingStat;
	}

	public void setTimingStat(Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> timingStat) {
		this.timingStat = timingStat;
	}
**/
	public Map<TimingKey, Stat> getTimingStat() {
		return timingStat;
	}

	public void setTimingStat(Map<TimingKey, Stat> timingStat) {
		this.timingStat = timingStat;
	}
	
	public Map<PowerGroup, Stat> getInPowerStat() {
		return inPowerStat;
	}

	public void setInPowerStat(Map<PowerGroup, Stat> inPowerStat) {
		this.inPowerStat = inPowerStat;
	}

	public Map<PowerGroup, Stat> getOutPowerStat() {
		return outPowerStat;
	}

	public void setOutPowerStat(Map<PowerGroup, Stat> outPowerStat) {
		this.outPowerStat = outPowerStat;
	}

	public boolean isHasShownElements() {
		return hasShownElements;
	}

	public void setHasShownElements(boolean hasShownElements) {
		this.hasShownElements = hasShownElements;
	}

	public Stat getLeakage() {
		return leakage;
	}

	public void setLeakage(Stat leakage) {
		this.leakage = leakage;
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
	public String getElementContent() {
		return super.getElementContent();
	}
	
	@Override
	public void setElementContent(String content) {
		super.setElementContent(content);
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
	public int compareTo(Element element) {
		return super.compareTo(element);
	}
}
