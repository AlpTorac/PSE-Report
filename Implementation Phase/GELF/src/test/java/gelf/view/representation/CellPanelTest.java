package gelf.view.representation;

import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.view.composites.SubWindow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CellPanelTest {
	
	private JFrame frame = new JFrame();
	float[] testFl = {0.1f, 0.2f};
	Library lib = new Library(null, testFl, testFl, null, null);
	
	SubWindow window = new SubWindow(null, null, 0, 0);
	DataPanel dpanel = new DataPanel(0, 0, cell);
	
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
	
  
  
  Cell cell = new Cell("cell", testFl, testFl, "lib", null, null, null, 0);
	InputPin inPin = new InputPin("In1", cell, inPowList);
	OutputPin outPin = new OutputPin("Out1", cell, outPowList, timingList);
	
	
	
	@Test
	void initTest() {
		CellPanel panel = new CellPanel(300, 300, cell, window, dpanel);
		frame.add(panel);
	}
}
