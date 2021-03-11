package gelf.model.elements;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

class OutputPinTest {

    float[][] values1 = 
    	{{0.001731f, 0.001764f, 0.001785f, 0.001795f, 0.001791f, 0.001791f, 0.001789f},
        {0.001662f, 0.001692f, 0.001718f, 0.001724f, 0.001719f, 0.001719f, 0.001716f},
        {0.001704f, 0.001702f, 0.001726f, 0.001741f, 0.001746f, 0.001751f, 0.001752f},
        {0.001877f, 0.001877f, 0.00191f, 0.001903f, 0.001914f, 0.001918f, 0.001919f},
        {0.002263f, 0.00224f, 0.002261f, 0.002264f, 0.002266f, 0.002264f, 0.002266f},
        {0.002854f, 0.002841f, 0.002812f, 0.002804f, 0.002803f, 0.002807f, 0.002809f},
        {0.003668f, 0.00363f, 0.00358f, 0.003572f, 0.003549f, 0.003553f, 0.003553f}};
	
    float[][] values2 = 
    	{{0.001411f, 0.00144f, 0.001459f, 0.001467f, 0.001459f, 0.001457f, 0.001444f},
        {0.00131f, 0.001331f, 0.001353f, 0.001344f, 0.001355f, 0.001355f, 0.001353f},
        {0.001362f, 0.001369f, 0.00138f, 0.001396f, 0.001409f, 0.001415f, 0.001413f},
        {0.001581f, 0.001583f, 0.001605f, 0.001613f, 0.001621f, 0.001622f, 0.001627f},
        {0.002f, 0.001988f, 0.002008f, 0.00201f, 0.002021f, 0.002015f, 0.002018f},
        {0.002659f, 0.002623f, 0.00261f, 0.002612f, 0.002612f, 0.00262f, 0.002616f},
        {0.003528f, 0.003478f, 0.003449f, 0.00343f, 0.003427f, 0.003431f, 0.003435f}};
	
    OutputPower outPow1 = new OutputPower(PowerGroup.FALL_POWER, values1);
	OutputPower outPow2 = new OutputPower(PowerGroup.RISE_POWER, values2);
	
	Timing timing1 = new Timing(TimingSense.POSITIVE_UNATE, 
			TimingType.COMBINATIONAL, TimingGroup.CELL_FALL, values1);
	Timing timing2 = new Timing(TimingSense.POSITIVE_UNATE, 
			TimingType.COMBINATIONAL, TimingGroup.CELL_RISE, values2);
  
	ArrayList<OutputPower> outPowList = new ArrayList<OutputPower>();
	ArrayList<Timing> timingList = new ArrayList<Timing>();
	
	@Test
	void calculateTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		outPow1.setIndex1(index1);
		outPow1.setIndex2(index2);
		outPow2.setIndex1(index1);
		outPow2.setIndex2(index2);
	    outPowList.add(outPow1);
		outPowList.add(outPow2);
		
		timing1.setIndex1(index1);
		timing1.setIndex2(index2);
		timing2.setIndex1(index1);
		timing2.setIndex2(index2);
		
		timingList.add(timing1);
		timingList.add(timing2);
			
		OutputPin outPin = new OutputPin(null, null, outPowList, timingList);
		
		outPin.calculate();
			
		float powmin = 0.00131f;
		float powmax = 0.003528f;
		float powavg = 0.097124f / 49f;
		float powmed = 0.001613f;
		
		float timmin = 0.00131f;
		float timmax = 0.003528f;
		float timavg = 0.097124f / 49f;
		float timmed = 0.001613f;
			
		Assertions.assertEquals(powmin, outPow2.getStats().getMin());
		Assertions.assertEquals(powmax, outPow2.getStats().getMax());
		Assertions.assertEquals(powavg, outPow2.getStats().getAvg());
		Assertions.assertEquals(powmed, outPow2.getStats().getMed());
		
		Assertions.assertEquals(timmin, timing2.getStats().getMin());
		Assertions.assertEquals(timmax, timing2.getStats().getMax());
		Assertions.assertEquals(timavg, timing2.getStats().getAvg());
		Assertions.assertEquals(timmed, timing2.getStats().getMed());
	}

	@Test
	void calculatePowerTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		outPow1.setIndex1(index1);
		outPow1.setIndex2(index2);
		outPow2.setIndex1(index1);
		outPow2.setIndex2(index2);
		outPowList.add(outPow1);
		outPowList.add(outPow2);
		OutputPin outPin = new OutputPin(null, null, outPowList, timingList);
		outPin.calculatePower();
			
		float powmin = 0.00131f;
		float powmax = 0.003528f;
		float powavg = 0.097124f / 49f;
		float powmed = 0.001613f;
			
		Assertions.assertEquals(powmin, outPow2.getStats().getMin());
		Assertions.assertEquals(powmax, outPow2.getStats().getMax());
		Assertions.assertEquals(powavg, outPow2.getStats().getAvg());
		Assertions.assertEquals(powmed, outPow2.getStats().getMed());
	}
	
	@Test
	void calculateTimingTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		timing1.setIndex1(index1);
		timing1.setIndex2(index2);
		timing2.setIndex1(index1);
		timing2.setIndex2(index2);
		timingList.add(timing1);
		timingList.add(timing2);
		OutputPin outPin = new OutputPin(null, null, outPowList, timingList);
		outPin.calculateTiming();
			
		float timmin = 0.00131f;
		float timmax = 0.003528f;
		float timavg = 0.097124f / 49f;
		float timmed = 0.001613f;
			
		Assertions.assertEquals(timmin, timing2.getStats().getMin());
		Assertions.assertEquals(timmax, timing2.getStats().getMax());
		Assertions.assertEquals(timavg, timing2.getStats().getAvg());
		Assertions.assertEquals(timmed, timing2.getStats().getMed());
	}
	
	@Test
	void replaceDataTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		timing1.setIndex1(index1);
		timing1.setIndex2(index2);
		timing2.setIndex1(index1);
		timing2.setIndex2(index2);
		timingList.add(timing1);
		timingList.add(timing2);
		OutputPin outPin1 = new OutputPin("OutPin1", null, null, null);
		OutputPin outPin2 = new OutputPin("OutPin2", null, outPowList, timingList);
		outPin1.replaceData(outPin2);
		Assertions.assertEquals("OutPin2", outPin1.getTimings().get(0).getParentOutPin().getName());
	}

  
  }
