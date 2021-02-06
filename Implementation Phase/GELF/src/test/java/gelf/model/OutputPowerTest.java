package gelf.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;

class OutputPowerTest {

	float[][] values = 
			{{0.001731f, 0.001764f, 0.001785f, 0.001795f, 0.001791f, 0.001791f, 0.001789f},
            {0.001662f, 0.001692f, 0.001718f, 0.001724f, 0.001719f, 0.001719f, 0.001716f},
            {0.001704f, 0.001702f, 0.001726f, 0.001741f, 0.001746f, 0.001751f, 0.001752f},
            {0.001877f, 0.001877f, 0.00191f, 0.001903f, 0.001914f, 0.001918f, 0.001919f},
            {0.002263f, 0.00224f, 0.002261f, 0.002264f, 0.002266f, 0.002264f, 0.002266f},
            {0.002854f, 0.002841f, 0.002812f, 0.002804f, 0.002803f, 0.002807f, 0.002809f},
            {0.003668f, 0.00363f, 0.00358f, 0.003572f, 0.003549f, 0.003553f, 0.003553f}};
	OutputPower outPow = new OutputPower(PowerGroup.FALL_POWER, values);
	



	@Test
	void scaleTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		outPow.setIndex1(index1);
		outPow.setIndex2(index2);
		outPow.scale(5f);
		float[][] expected = 
			{{0.001731f * 5f, 0.001764f* 5f, 0.001785f * 5f, 0.001795f * 5f, 0.001791f * 5f, 0.001791f * 5f, 0.001789f * 5f},
		    {0.001662f * 5f, 0.001692f * 5f, 0.001718f * 5f, 0.001724f * 5f, 0.001719f * 5f, 0.001719f * 5f, 0.001716f * 5f},
		    {0.001704f * 5f, 0.001702f * 5f, 0.001726f * 5f, 0.001741f * 5f, 0.001746f * 5f, 0.001751f * 5f, 0.001752f * 5f},
		    {0.001877f * 5f, 0.001877f * 5f, 0.00191f * 5f, 0.001903f * 5f, 0.001914f * 5f, 0.001918f * 5f, 0.001919f * 5f},
		    {0.002263f * 5f, 0.00224f * 5f, 0.002261f * 5f, 0.002264f * 5f, 0.002266f * 5f, 0.002264f * 5f, 0.002266f * 5f},
		    {0.002854f * 5f, 0.002841f * 5f, 0.002812f * 5f, 0.002804f * 5f, 0.002803f * 5f, 0.002807f * 5f, 0.002809f * 5f},
		    {0.003668f * 5f, 0.00363f * 5f, 0.00358f * 5f, 0.003572f * 5f, 0.003549f * 5f, 0.003553f * 5f, 0.003553f * 5f}};

		Assertions.assertArrayEquals(expected, outPow.getValues());
	}
	
	
	@Test
	void calculateTest() {
		float[] index1 = {0.0004f, 0.009027f, 0.03931f, 0.09714f, 0.1872f, 0.3137f, 0.48f};
		float[] index2 = {0.0004f, 0.002192f, 0.008481f, 0.02049f, 0.0392f, 0.06545f, 0.1f};
		outPow.setIndex1(index1);
		outPow.setIndex2(index2);
		outPow.calculate();
		float med = 0.00191f;
		float min =	0.001662f;
		float max = 0.003668f;
		float avg = 0.110495f / 49f;
		Assertions.assertEquals(min, outPow.getStats().getMin());
		Assertions.assertEquals(max, outPow.getStats().getMax());
		Assertions.assertEquals(avg, outPow.getStats().getAvg());
		Assertions.assertEquals(med, outPow.getStats().getMed());
	}

}
