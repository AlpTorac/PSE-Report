package gelf.model.parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import gelf.model.elements.InputPin;
import gelf.model.elements.Pin;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.exceptions.InvalidFileFormatException;

class LibertyParserTest {
	String outputPowerExample = "fallpower(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.001009, 0.001043, 0.001056, 0.001058\",\\\r\n" + 
			"                 \"0.0009569, 0.0009777, 0.0009895, 0.0009899\",\\\r\n" + 
			"                 \"0.0009458, 0.0009591, 0.0009767, 0.0009837\");\r\n" + 
			"        }".replaceAll("\\s+", "");
	String inPowerExample = "rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"-1.642e-05, -4.167e-05, \\\r\n" + 
			"                  -5.856e-05\");\r\n" + 
			"        }".replaceAll("\\s+", "");
	String timingExample = "cellfall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.003091, 0.00493, 0.01122, 0.02329\",\\\r\n" + 
			"                 \"0.006286, 0.008379, 0.01472, 0.02668\",\\\r\n" + 
			"                 \"0.01125, 0.01586, 0.02594, 0.03874\");\r\n" +  
			
			"        }".replaceAll("\\s+", "");
	String inputPinExample = "pin(A3) { \r\n" + 
			"      capacitance : 0.001469 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          values(\"0.0002351, 0.0002652, 0.0002713, 0.000275, 0.0002691, 0.0002695, \\\r\n" + 
			"                  0.000268\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          values(\"-9.968e-05, -0.0001898, -0.0002075, -0.0002139, -0.0002171, -0.0002173, \\\r\n" + 
			"                  -0.0002191\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    ".replaceAll("\\s+", "");
	
	float[] exampleIndex1 = new float[] {0.0004f , 0.009027f , 0.03931f};
	float[] exampleIndex2 = new float[] {0.0004f , 0.002192f , 0.008481f , 0.02049f};
	
	@Test
	void parsePinTest() throws InvalidFileFormatException {
		InputPin pin = (InputPin) LibertyParser.parsePin(inputPinExample, new ArrayList<InputPin>()); 
		
	}
	
	@Test 
	void parseOutPowerTest() throws InvalidFileFormatException {
		OutputPower outPower = LibertyParser.parseOutPower(outputPowerExample);
		float[][] values = new float[][] {{0.001009f, 0.001043f, 0.001056f, 0.001058f}, 
									{0.0009569f, 0.0009777f, 0.0009895f, 0.0009899f},
									{0.0009458f, 0.0009591f, 0.0009767f, 0.0009837f}};
		assertArrayEquals(outPower.getIndex1(), exampleIndex1);
		assertArrayEquals(outPower.getIndex2(), exampleIndex2);
		assertTrue(Arrays.deepEquals(outPower.getValues(), values));
	}
	
	@Test 
	void parseInPowerTest() throws InvalidFileFormatException {
		InputPower inPower = LibertyParser.parseInPower(inPowerExample);
		assertEquals(inPower.getPowGroup(),PowerGroup.RISE_POWER);
		assertArrayEquals(inPower.getIndex1(), exampleIndex1);
		assertArrayEquals(inPower.getValues(), new float[] {-1.642e-05f, -4.167e-05f, -5.856e-05f});
	}
	
	@Test
	void parseOutTimingTest() throws InvalidFileFormatException {
		Timing timing = LibertyParser.parseOutTiming(timingExample, TimingSense.NEGATIVE, TimingType.COMBRISE);
		float[][] values = new float[][] {{0.003091f, 0.00493f, 0.01122f, 0.02329f},
										{0.006286f, 0.008379f, 0.01472f, 0.02668f},
										{0.01125f, 0.01586f, 0.02594f, 0.03874f}};
		assertEquals(timing.getTimGroup(),TimingGroup.CELLFALL);
		assertArrayEquals(timing.getIndex1(), exampleIndex1);
		assertArrayEquals(timing.getIndex2(), exampleIndex2);
		assertEquals(timing.getTimSense(), TimingSense.NEGATIVE);
		assertEquals(timing.getTimType(), TimingType.COMBRISE);
		assertTrue(Arrays.deepEquals(timing.getValues(), values));
	}
	
	
	@Test
	void parseArrayTest() {
		float[] desiredResult = exampleIndex1;
		float[] actualResult = LibertyParser.parseArray(outputPowerExample, "index_1");
		assertArrayEquals(desiredResult, actualResult);
    }

	@Test
	void parseDoubleArrayTest() {
		float[][] desiredResult = new float[][] {{0.001009f, 0.001043f, 0.001056f, 0.001058f}, 
												{0.0009569f, 0.0009777f, 0.0009895f, 0.0009899f},
												{0.0009458f, 0.0009591f, 0.0009767f, 0.0009837f}};
	    float[][] actualResult = LibertyParser.parseDoubleArray(outputPowerExample, "values");
	    assertTrue(Arrays.deepEquals(desiredResult, actualResult));
	}
	
	@Test
	void stringToArrayTest() {
		String input = "7.376e-05,7.418e-05,9.276e-05,9.269e-05,8.746e-05,8.816e-05,\\9.114e-05";
        float[] result = LibertyParser.stringToArray(input);
        assertArrayEquals(new float[] {7.376e-05f, 7.418e-05f, 9.276e-05f, 9.269e-05f, 8.746e-05f, 8.816e-05f, 9.114e-05f}, result);
	}
}
