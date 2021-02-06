package gelf.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Stat;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.PowerGroup;

class InputPowerTest {

	
	float[] values = {0.000302f, 0.0003592f, 0.0003661f, 0.0003644f, 
					  0.000366f, 0.0003663f, 0.0003662f};
	InputPower inPow = new InputPower(PowerGroup.FALL_POWER, values);

	

	@Test
	void scaleTest() {
		inPow.scale(5f);
		float[] expected = {0.000302f * 5f, 0.0003592f * 5f, 0.0003661f * 5f, 0.0003644f * 5f, 
				  0.000366f * 5f, 0.0003663f * 5f, 0.0003662f * 5f};
		
		Assertions.assertArrayEquals(expected, inPow.getValues());
	}
	
	@Test 
	void calculateTest() {
		inPow.calculate();
		Stat stats = inPow.getStats();
		float expectedSum = 0.000302f + 0.0003592f + 0.0003661f + 0.0003644f + 
				  0.000366f + 0.0003663f + 0.0003662f;
		float expectedAvg = expectedSum / 7;
		Stat expectedStat = new Stat(0.000302f, 0.0003663f, expectedAvg, 0.000366f);
		Assertions.assertEquals(expectedStat.getMin(), stats.getMin());
		Assertions.assertEquals(expectedStat.getMax(), stats.getMax());
		Assertions.assertEquals(expectedStat.getAvg(), stats.getAvg());
		Assertions.assertEquals(expectedStat.getMed(), stats.getMed());
	}

}
