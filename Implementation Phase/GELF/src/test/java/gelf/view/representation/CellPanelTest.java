package gelf.view.representation;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

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
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.SubWindow;
import gelf.view.composites.Visualizer;

public class CellPanelTest {
	
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
  
  
  ArrayList<InputPower> inputPowers = new ArrayList<InputPower>();
  ArrayList<OutputPower> outputPowers = new ArrayList<OutputPower>();
  ArrayList<Timing> timings = new ArrayList<Timing>();
  
  ArrayList<InputPin> inPins = new ArrayList<InputPin>();
  ArrayList<OutputPin> outPins = new ArrayList<OutputPin>();
  ArrayList<Cell> cells = new ArrayList<Cell>();
	
	
	
	JFrame frame = new JFrame();
	
	
	@Test
	void initWithCellTest() {
		inputPowers.add(inPow1);
		outputPowers.add(outPow1);
		timings.add(timing1);
		inputPowers.add(inPow2);
		outputPowers.add(outPow2);
		timings.add(timing2);
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
		Leakage leakages = new Leakage(new float[2]);
		
		
		
		InputPin inPin = new InputPin("A1", null, inputPowers);
		outPow1.setRelatedPin(inPin);
    	outPow2.setRelatedPin(inPin);
    	timing1.setRelatedPin(inPin);
    	timing2.setRelatedPin(inPin);
		
		OutputPin outPin = new OutputPin("Z", null, outputPowers, timings);
		inPins.add(inPin);
		outPins.add(outPin);
		Cell cell = new Cell("cell", new float[2], new float[2], null, inPins, outPins, leakages, 0.1f);
		inPin.setParent(cell);
		outPin.setParent(cell);
		cells.add(cell);
		Library library = new Library("library", new float[2], new float[2], null, cells);
		
		cell.setParentLibrary(library);
		frame.setLayout(null);
	
		Project p = Model.getInstance().getCurrentProject();
		SubWindow s = new SubWindow(cell, p, null, null, 0, 0);
		Visualizer v = new Visualizer(cell, s, p, 0, 0);
		CellPanel panel = new CellPanel(100, 100, cell, s, v, null);
		frame.add(panel);
		panel.mouseClicked(new MouseEvent(panel.buttonMap.get(outPin), 0, 0, 0, 0, 0, 1, false));
		panel.mouseClicked(new MouseEvent(panel.cellButton, 0, 0, 0, 0, 0, 1, false));
		panel.mouseClicked(new MouseEvent(panel.libButton, 0, 0, 0, 0, 0, 1, false));
		
	}
	
	@Test
	void initWithPinTest() {
		inputPowers.add(inPow1);
		outputPowers.add(outPow1);
		timings.add(timing1);
		inputPowers.add(inPow2);
		outputPowers.add(outPow2);
		timings.add(timing2);
		
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
    	
    	
		Leakage leakages = new Leakage(new float[2]);
		
		Library library = new Library("library", new float[2], new float[2], "//", cells);
		
		InputPin inPin = new InputPin("A1", null, inputPowers);
		outPow1.setRelatedPin(inPin);
    	outPow2.setRelatedPin(inPin);
    	timing1.setRelatedPin(inPin);
    	timing2.setRelatedPin(inPin);
    	
    	OutputPin outPin = new OutputPin("Z", null, outputPowers, timings);
		inPins.add(inPin);
		outPins.add(outPin);
		Cell cell = new Cell("cell", new float[2], new float[2], library, inPins, outPins, leakages, 0.1f);
		inPin.setParent(cell);
		outPin.setParent(cell);
		cells.add(cell);
		frame.setLayout(null);
		
	
		Project p = Model.getInstance().getCurrentProject();
		SubWindow s = new SubWindow(outPin, p, null, null, 0, 0);
		Visualizer v = new Visualizer(outPin, s, p, 0, 0);
		CellPanel panel2 =  new CellPanel(100, 100, outPin, s, v, new DataPanel(0, 0, outPin));
		frame.setLayout(null);
		frame.add(panel2);
		panel2.itemStateChanged(new ItemEvent(panel2.checkboxes.get(0), 0, panel2, 0));
	}
	
	@Test
	void cellTest1() {
		inputPowers.add(inPow1);
		outputPowers.add(outPow1);
		timings.add(timing1);
		inputPowers.add(inPow2);
		outputPowers.add(outPow2);
		timings.add(timing2);
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
		Leakage leakages = new Leakage(new float[2]);
		
		InputPin inPin = new InputPin("A1", null, inputPowers);
		outPow1.setRelatedPin(inPin);
    	outPow2.setRelatedPin(inPin);
    	timing1.setRelatedPin(inPin);
    	timing2.setRelatedPin(inPin);
		
		OutputPin outPin = new OutputPin("Z", null, outputPowers, timings);
		for (int i = 0; i < 3; i++) {
			
			inPins.add(inPin);
			outPins.add(outPin);
		}
		Cell cell = new Cell("cell", new float[2], new float[2], null, inPins, outPins, leakages, 0.1f);
		inPin.setParent(cell);
		outPin.setParent(cell);
		cells.add(cell);
		Library library = new Library("library", new float[2], new float[2], null, cells);
		
		cell.setParentLibrary(library);
		frame.setLayout(null);
		
		Project p = Model.getInstance().getCurrentProject();
		SubWindow s = new SubWindow(cell, p, null, null, 0, 0);
		Visualizer v = new Visualizer(cell, s, p, 0, 0);
		CellPanel panel = new CellPanel(100, 100, cell, s, null, null);
		frame.add(panel);
	}
	
	@Test
	void cellTest2() {
		inputPowers.add(inPow1);
		outputPowers.add(outPow1);
		timings.add(timing1);
		inputPowers.add(inPow2);
		outputPowers.add(outPow2);
		timings.add(timing2);
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
		Leakage leakages = new Leakage(new float[2]);
		
		InputPin inPin = new InputPin("A1", null, inputPowers);
		outPow1.setRelatedPin(inPin);
    	outPow2.setRelatedPin(inPin);
    	timing1.setRelatedPin(inPin);
    	timing2.setRelatedPin(inPin);
		
		OutputPin outPin = new OutputPin("Z", null, outputPowers, timings);
		for (int i = 0; i < 6; i++) {
			
			inPins.add(inPin);
			outPins.add(outPin);
		}
		Cell cell = new Cell("cell", new float[2], new float[2], null, inPins, outPins, leakages, 0.1f);
		inPin.setParent(cell);
		outPin.setParent(cell);
		cells.add(cell);
		Library library = new Library("library", new float[2], new float[2], null, cells);
		
		cell.setParentLibrary(library);
		frame.setLayout(null);
		
		
		Project p = Model.getInstance().getCurrentProject();
		SubWindow s = new SubWindow(cell, p, null, null, 0, 0);
		Visualizer v = new Visualizer(cell, s, p, 0, 0);
		CellPanel panel = new CellPanel(100, 100, cell, s, null, null);
		frame.add(panel);
	}
	
	
}