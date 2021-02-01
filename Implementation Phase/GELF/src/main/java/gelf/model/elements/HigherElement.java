package main.java.gelf.model.elements;

import java.util.ArrayList;
import java.util.Map;

import main.java.gelf.model.elements.attributes.PowerGroup;
import main.java.gelf.model.elements.attributes.TimingGroup;
import main.java.gelf.model.elements.attributes.TimingSense;
import main.java.gelf.model.elements.attributes.TimingType;

public abstract class HigherElement extends Element {
	protected ArrayList<TimingSense> availableTimSen;
    protected ArrayList<TimingGroup> availableTimGr;
    protected ArrayList<TimingType> availableTimType;
    protected ArrayList<PowerGroup> availableOutputPower;
    protected ArrayList<PowerGroup> availableInputPower;
    protected Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> timingStat;
    protected Map<PowerGroup, Stat> inPowerStat;
    protected Map<PowerGroup, Stat> outPowerStat;
    protected boolean hasShownElements;
    protected Stat leakage;
    
	public ArrayList<TimingSense> getAvailableTimSen() {
		return availableTimSen;
	}
	
	public void setAvailableTimSen(ArrayList<TimingSense> availableTimSen) {
		this.availableTimSen = availableTimSen;
	}
	
	public ArrayList<TimingGroup> getAvailableTimGr() {
		return availableTimGr;
	}
	
	public void setAvailableTimGr(ArrayList<TimingGroup> availableTimGr) {
		this.availableTimGr = availableTimGr;
	}
	
	public ArrayList<TimingType> getAvailableTimType() {
		return availableTimType;
	}
	
	public void setAvailableTimType(ArrayList<TimingType> availableTimType) {
		this.availableTimType = availableTimType;
	}
	
	public ArrayList<PowerGroup> getAvailableOutputPower() {
		return availableOutputPower;
	}
	
	public void setAvailableOutputPower(ArrayList<PowerGroup> availableOutputPower) {
		this.availableOutputPower = availableOutputPower;
	}
	
	public ArrayList<PowerGroup> getAvailableInputPower() {
		return availableInputPower;
	}
	
	public void setAvailableInputPower(ArrayList<PowerGroup> availableInputPower) {
		this.availableInputPower = availableInputPower;
	}

	public Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> getTimingStat() {
		return timingStat;
	}

	public void setTimingStat(Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> timingStat) {
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
