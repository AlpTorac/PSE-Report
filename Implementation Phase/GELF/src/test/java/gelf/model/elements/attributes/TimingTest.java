package gelf.model.elements.attributes;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

class TimingTest {
	
	
	float[][] values = 
		{{0.011f, 0.01342f, 0.02028f, 0.03246f, 0.05142f, 0.07744f, 0.1121f},
        {0.01448f, 0.0169f, 0.02377f, 0.03588f, 0.05474f, 0.08106f, 0.1164f},
        {0.02403f, 0.02667f, 0.03376f, 0.04593f, 0.0647f, 0.09118f, 0.1258f},
        {0.03379f, 0.03706f, 0.04474f, 0.05712f, 0.07582f, 0.1019f, 0.1366f},
        {0.04266f, 0.04657f, 0.05553f, 0.06833f, 0.08652f, 0.1129f, 0.1475f},
        {0.05047f, 0.05512f, 0.06559f, 0.07908f, 0.09782f, 0.1241f, 0.1589f},
        {0.05716f, 0.06251f, 0.07447f, 0.08917f, 0.1088f, 0.1344f, 0.1692f}};
	
	Timing timing = new Timing(TimingSense.POSITIVE_UNATE, TimingType.COMBINATIONAL,
			TimingGroup.CELL_FALL, values);
	@Test
	void scaleTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		timing.setIndex1(index1);
		timing.setIndex2(index2);
		timing.scale(5f);
		float[][] expected = 
			{{0.011f * 5f, 0.01342f * 5f, 0.02028f * 5f, 0.03246f * 5f, 0.05142f * 5f, 0.07744f * 5f, 0.1121f * 5f},
			{0.01448f * 5f, 0.0169f * 5f, 0.02377f * 5f, 0.03588f * 5f, 0.05474f * 5f, 0.08106f * 5f, 0.1164f * 5f},
			{0.02403f * 5f, 0.02667f * 5f, 0.03376f * 5f, 0.04593f * 5f, 0.0647f * 5f, 0.09118f * 5f, 0.1258f * 5f},
			{0.03379f * 5f, 0.03706f * 5f, 0.04474f * 5f, 0.05712f * 5f, 0.07582f * 5f, 0.1019f * 5f, 0.1366f * 5f},
			{0.04266f * 5f, 0.04657f * 5f, 0.05553f * 5f, 0.06833f * 5f, 0.08652f * 5f, 0.1129f * 5f, 0.1475f * 5f},
			{0.05047f * 5f, 0.05512f * 5f, 0.06559f * 5f, 0.07908f * 5f, 0.09782f * 5f, 0.1241f * 5f, 0.1589f * 5f},
			{0.05716f * 5f, 0.06251f * 5f, 0.07447f * 5f, 0.08917f * 5f, 0.1088f * 5f, 0.1344f * 5f, 0.1692f * 5f}};

		Assertions.assertArrayEquals(expected, timing.getValues());
	}
	
	@Test
	void calculateTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		timing.setIndex1(index1);
		timing.setIndex2(index2);
		timing.calculate();
		float med = 0.06251f;
		float min =	0.011f;
		float max = 0.1692f;
		float avg = 0.07035203f; //3.44725f / 49f;
		Assertions.assertEquals(min, timing.getStats().getMin());
		Assertions.assertEquals(max, timing.getStats().getMax());
		Assertions.assertEquals(avg, timing.getStats().getAvg());
		Assertions.assertEquals(med, timing.getStats().getMed());
	}

}