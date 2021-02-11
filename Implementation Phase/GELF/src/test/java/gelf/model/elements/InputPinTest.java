package gelf.model.elements;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.PowerGroup;

class InputPinTest {
	
	float[] values1 = {0.000302f, 0.0003592f, 0.0003661f, 0.0003644f, 
			  0.000366f, 0.0003663f, 0.0003662f};
	float[] values2 = {-0.000201f, -0.0002455f, -0.0002576f, -0.0002582f,
			-0.0002586f, -0.0002589f, -0.0002599f};
	InputPower inPow1 = new InputPower(PowerGroup.FALL_POWER, values1);
    InputPower inPow2 = new InputPower(PowerGroup.RISE_POWER, values2);
    
    ArrayList<InputPower> inPowList = new ArrayList<InputPower>();
    
    @Test
    void calculateTest() {
    	inPowList.add(inPow1);
		inPowList.add(inPow2);
		
		InputPin inPin = new InputPin(null, null, inPowList);
		inPin.calculate();
		
		float min = -0.0002599f;
		float max = -0.000201f;
		float avg = -0.0017397f / 7f;
		float med = -0.0002582f;
		
		Assertions.assertEquals(min, inPow2.getStats().getMin());
		Assertions.assertEquals(max, inPow2.getStats().getMax());
		Assertions.assertEquals(avg, inPow2.getStats().getAvg());
		Assertions.assertEquals(med, inPow2.getStats().getMed());
    }

	@Test
	void calculatePowertest() {
		inPowList.add(inPow1);
		inPowList.add(inPow2);
		InputPin inPin = new InputPin(null, null, inPowList);
		inPin.calculatePower();
		
		float min = -0.0002599f;
		float max = -0.000201f;
		float avg = -0.0017397f / 7f;
		float med = -0.0002582f;
		
		Assertions.assertEquals(min, inPow2.getStats().getMin());
		Assertions.assertEquals(max, inPow2.getStats().getMax());
		Assertions.assertEquals(avg, inPow2.getStats().getAvg());
		Assertions.assertEquals(med, inPow2.getStats().getMed());
	}
}
