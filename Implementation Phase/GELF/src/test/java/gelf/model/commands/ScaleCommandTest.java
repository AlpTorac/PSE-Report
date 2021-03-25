package gelf.model.commands;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.Leakage;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

class ScaleCommandTest {
	
	float[] values = {0.000302f, 0.0003592f, 0.0003661f, 0.0003644f, 
			  0.000366f, 0.0003663f, 0.0003662f};
	InputPower inPow = new InputPower(PowerGroup.FALL_POWER, values);
	InputPin inPin = new InputPin(null, null, null);
	
	float[] values1 = {0.000302f, 0.0003592f, 0.0003661f, 0.0003644f, 
			  0.000366f, 0.0003663f, 0.0003662f};
	
	float[] values2 = {-0.000201f, -0.0002455f, -0.0002576f, -0.0002582f,
			-0.0002586f, -0.0002589f, -0.0002599f};
	
	float[][] values3 = 
  	{{0.001731f, 0.001764f, 0.001785f, 0.001795f, 0.001791f, 0.001791f, 0.001789f},
      {0.001662f, 0.001692f, 0.001718f, 0.001724f, 0.001719f, 0.001719f, 0.001716f},
      {0.001704f, 0.001702f, 0.001726f, 0.001741f, 0.001746f, 0.001751f, 0.001752f},
      {0.001877f, 0.001877f, 0.00191f, 0.001903f, 0.001914f, 0.001918f, 0.001919f},
      {0.002263f, 0.00224f, 0.002261f, 0.002264f, 0.002266f, 0.002264f, 0.002266f},
      {0.002854f, 0.002841f, 0.002812f, 0.002804f, 0.002803f, 0.002807f, 0.002809f},
      {0.003668f, 0.00363f, 0.00358f, 0.003572f, 0.003549f, 0.003553f, 0.003553f}};
	
  float[][] values4 = 
  	{{0.001411f, 0.00144f, 0.001459f, 0.001467f, 0.001459f, 0.001457f, 0.001444f},
      {0.00131f, 0.001331f, 0.001353f, 0.001344f, 0.001355f, 0.001355f, 0.001353f},
      {0.001362f, 0.001369f, 0.00138f, 0.001396f, 0.001409f, 0.001415f, 0.001413f},
      {0.001581f, 0.001583f, 0.001605f, 0.001613f, 0.001621f, 0.001622f, 0.001627f},
      {0.002f, 0.001988f, 0.002008f, 0.00201f, 0.002021f, 0.002015f, 0.002018f},
      {0.002659f, 0.002623f, 0.00261f, 0.002612f, 0.002612f, 0.00262f, 0.002616f},
      {0.003528f, 0.003478f, 0.003449f, 0.00343f, 0.003427f, 0.003431f, 0.003435f}};
  
	InputPower inPow1 = new InputPower(PowerGroup.FALL_POWER, values2);
  InputPower inPow2 = new InputPower(PowerGroup.RISE_POWER, values2);
  
  OutputPower outPow1 = new OutputPower(PowerGroup.FALL_POWER, values3);
  OutputPower outPow2 = new OutputPower(PowerGroup.RISE_POWER, values4);
  
  Timing timing1 = new Timing(TimingSense.NEGATIVE_UNATE,
  		TimingType.COMBINATIONAL, TimingGroup.CELL_FALL, values3);
  Timing timing2 = new Timing(TimingSense.NEGATIVE_UNATE,
  		TimingType.COMBINATIONAL, TimingGroup.CELL_RISE, values4);
  
  
  ArrayList<InputPower> inPowList = new ArrayList<InputPower>();
  ArrayList<OutputPower> outPowList = new ArrayList<OutputPower>();
  ArrayList<Timing> timingList = new ArrayList<Timing>();
  
  ArrayList<InputPin> inPinList = new ArrayList<InputPin>();
  ArrayList<OutputPin> outPinList = new ArrayList<OutputPin>();
  ArrayList<Cell> cellList = new ArrayList<Cell>();
  ArrayList<Cell> cellList2 = new ArrayList<Cell>();
	
  
	
	@Test
	void executeTest() {
		inPowList.add(inPow1);
		inPowList.add(inPow2);
		
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
    	float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
    	outPow1.setIndex1(index1);
    	outPow1.setIndex2(index2);
    	outPow2.setIndex1(index1);
    	outPow2.setIndex2(index2);
    	
    	timing1.setIndex1(index1);
    	timing1.setIndex2(index2);
    	timing2.setIndex1(index1);
    	timing2.setIndex2(index2);
    	
    	timingList.add(timing1);
		timingList.add(timing2);
    	
    	outPowList.add(outPow1);
		outPowList.add(outPow2);
		
		OutputPin outPin1 = new OutputPin("OutPin1", null, outPowList, timingList);
		OutputPin outPin2 = new OutputPin("OutPin2", null, outPowList, timingList);
		
		outPinList.add(outPin1);
		outPinList.add(outPin2);
		
		InputPin inPin1 = new InputPin("InPin1", null, inPowList);
		InputPin inPin2 = new InputPin("InPin2", null, inPowList);
		
		inPinList.add(inPin1);
		inPinList.add(inPin2);
		
		float[] values = {0.00291f, 0.003117f, 0.003117f, 0.005393f, 
    			0.003113f, 0.005081f, 0.004988f, 0.01069f};
    	Leakage leakage = new Leakage(values);
		
		Cell cell1 = new Cell("cell1", index1, index2, null, inPinList, outPinList, leakage, 1);
		Cell cell2 = new Cell("cell2", index1, index2, null, inPinList, outPinList, leakage, 1);
		
		inPin1.setParent(cell1);
		inPin2.setParent(cell1);
		outPin1.setParent(cell1);
		outPin2.setParent(cell1);
		outPow1.setRelatedPin(inPin1);
		outPow2.setRelatedPin(inPin1);
		timing1.setRelatedPin(inPin1);
		timing2.setRelatedPin(inPin1);
		
		cellList.add(cell1);
		cellList.add(cell2);
		Library library = new Library("LibName", index1, index2, "abc", cellList);
		
		cell1.setParentLibrary(library);
		cell2.setParentLibrary(library);
		
		ScaleCommand scale = new ScaleCommand(5f, inPin, null, 
	    		inPow, null, null);
		ScaleCommand scale2 = new ScaleCommand(5f, library, "Timing", 
	    		null, null, null);
		ScaleCommand scale3 = new ScaleCommand(5f, library, "Default Leakage", 
	    		null, null, null);
		ScaleCommand scale4 = new ScaleCommand(5f, library, "Leakage", 
	    		null, null, null);
		ScaleCommand scale5 = new ScaleCommand(5f, library, "Output Power", 
	    		null, null, null);
		ScaleCommand scale6 = new ScaleCommand(5f, library, "Input Power", 
	    		null, null, null);
		
		
		ScaleCommand scale7 = new ScaleCommand(5f, cell1, "Timing", 
	    		null, null, null);
		ScaleCommand scale8 = new ScaleCommand(5f, cell1, "Leakage", 
	    		null, null, null);
		ScaleCommand scale9 = new ScaleCommand(5f, cell1, "Output Power", 
	    		null, null, null);
		ScaleCommand scale10 = new ScaleCommand(5f, cell1, "Input Power", 
	    		null, null, null);
		
		ScaleCommand scale11 = new ScaleCommand(5f, outPin1, "Output Power", 
	    		null, outPow1, null);
		
		ScaleCommand scale12 = new ScaleCommand(5f, outPin1, "Timing", 
	    		null, null, timing1);
		
	
		scale.execute();
		scale2.execute();
		scale3.execute();
		scale4.execute();
		scale5.execute();
		scale6.execute();
		scale7.execute();
		scale8.execute();
		scale9.execute();
		scale10.execute();
		scale11.execute();
		scale12.execute();
		scale2.replaceElementData(library, library);
		scale2.replaceElementData(cell1, cell1);
		scale2.replaceElementData(inPin1, inPin1);
		scale2.replaceElementData(outPin1, outPin1);
		
		float[] expected = {0.000302f * 5f, 0.0003592f * 5f, 0.0003661f * 5f, 0.0003644f * 5f, 
				  0.000366f * 5f, 0.0003663f * 5f, 0.0003662f * 5f};
		
		Assertions.assertArrayEquals(expected, inPow.getValues());
	}
	
	@Test
	void undoTest() {
		ScaleCommand scale = new ScaleCommand(5f, inPin, null, 
	    		inPow, null, null);
		scale.execute();
		scale.undo();
		Assertions.assertArrayEquals(values, inPow.getValues());
	}

}
