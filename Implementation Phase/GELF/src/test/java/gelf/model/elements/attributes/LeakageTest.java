package gelf.model.elements.attributes;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Stat;
import gelf.model.elements.attributes.Leakage;

class LeakageTest {
	
	float[] values = {0.00291f, 0.003117f, 0.003117f, 0.005393f, 
			0.003113f, 0.005081f, 0.004988f, 0.01069f};
	Leakage leakage = new Leakage(values);
	
	@Test
	void scaleTest() {
		leakage.scale(5f);
		float[] expected = {0.00291f * 5f, 0.003117f * 5f, 0.003117f * 5f, 
				0.005393f * 5f, 0.003113f * 5f, 0.005081f * 5f, 0.004988f * 5f, 
				0.01069f * 5f};
		
		Assertions.assertArrayEquals(expected, leakage.getValues());
	}
	
	@Test 
	void calculateTest() {
		leakage.calculate();
		Stat stats = leakage.getStats();
		float min = 0.00291f;
		float max = 0.01069f;
		float avg = 0.038409f / 8f;
		float med = 0.0040525f;
		
		Assertions.assertEquals(min, stats.getMin());
		Assertions.assertEquals(max, stats.getMax());
		Assertions.assertEquals(avg, stats.getAvg());
		Assertions.assertEquals(med, stats.getMed());
	}
}