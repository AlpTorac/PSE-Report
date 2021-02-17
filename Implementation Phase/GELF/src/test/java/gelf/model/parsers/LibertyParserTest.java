package gelf.model.parsers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
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
	
	String outputPowerExample = "fall_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.001009, 0.001043, 0.001056, 0.001058\",\\\r\n" + 
			"                 \"0.0009569, 0.0009777, 0.0009895, 0.0009899\",\\\r\n" + 
			"                 \"0.0009458, 0.0009591, 0.0009767, 0.0009837\");\r\n" + 
			"        }";
	String inPowerExample = "rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"-1.642e-05, -4.167e-05, \\\r\n" + 
			"                  -5.856e-05\");\r\n" + 
			"        }";
	String timingExample = "cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.003091, 0.00493, 0.01122, 0.02329\",\\\r\n" + 
			"                 \"0.006286, 0.008379, 0.01472, 0.02668\",\\\r\n" + 
			"                 \"0.01125, 0.01586, 0.02594, 0.03874\");\r\n" +  
			
			"        }";
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
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"0.0002351, 0.0002652, \\\r\n" + 
			"                  0.0002713\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"-9.968e-05, -0.0001898, \\\r\n" + 
			"                  -0.0002075\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    ";
	
	String outputPinExample = "pin(Z) { \r\n" + 
			"      direction : output ; \r\n" + 
			"      function : \"(A1&A2)\" ; \r\n" + 
			"      max_capacitance : 0.1 ; \r\n" + 
			"      min_capacitance : 0.0004 ; \r\n" + 
			"      output_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.001009, 0.001043, 0.001056, 0.001058\",\\\r\n" + 
			"                 \"0.0009569, 0.0009777, 0.0009895, 0.0009899\",\\\r\n" + 
			"                 \"0.0009458, 0.0009591, 0.0009767, 0.0009837\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"        related_pin : \"A2\" ; \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.001348, 0.001359, 0.001382, 0.001384\",\\\r\n" + 
			"                 \"0.00127, 0.00129, 0.001305, 0.001308\",\\\r\n" + 
			"                 \"0.001262, 0.001283, 0.001304, 0.001312\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"      timing() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"        timing_sense : positive_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.01207, 0.01484, 0.0232, 0.03865\",\\\r\n" + 
			"                 \"0.0158, 0.01856, 0.02694, 0.04247\",\\\r\n" + 
			"                 \"0.02738, 0.03029, 0.03877, 0.05432\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        cell_rise(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.01719, 0.02063, 0.03052, 0.04812\",\\\r\n" + 
			"                 \"0.02006, 0.0235, 0.03342, 0.0511\",\\\r\n" + 
			"                 \"0.03119, 0.03472, 0.04474, 0.06239\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"      timing() { \r\n" + 
			"        related_pin : \"A2\" ; \r\n" + 
			"        timing_sense : positive_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        fall_transition(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.004809, 0.007747, 0.01831, 0.03945\",\\\r\n" + 
			"                 \"0.004813, 0.007732, 0.01844, 0.03944\",\\\r\n" + 
			"                 \"0.005411, 0.008155, 0.01854, 0.03939\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_transition(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.006176, 0.00962, 0.02188, 0.04625\",\\\r\n" + 
			"                 \"0.006214, 0.009715, 0.02183, 0.04611\",\\\r\n" + 
			"                 \"0.006525, 0.009905, 0.02194, 0.04615\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"  }\r\n" + 
			"";
	
	String cellExample = " cell(AND2_X1) { \r\n" + 
			"    cell_leakage_power : 0.005445 ; \r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&!A2\" ; \r\n" + 
			"      value : \"0.002177\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&A2\" ; \r\n" + 
			"      value : \"0.003654\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"A1&!A2\" ; \r\n" + 
			"      value : \"0.003458\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"A1&A2\" ; \r\n" + 
			"      value : \"0.005445\" ; \r\n" + 
			"    }\r\n" + "hallo : welt ;" +
			"\r\n" + 
			"    pin(A1) { \r\n" + 
			"      capacitance : 0.001439 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"0.0001932, 0.0002476, \\\r\n" + 
			"                  0.0002503\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"    pin(A2) { \r\n" + 
			"      capacitance : 0.001396 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"-0.0001385, -0.0001735, \\\r\n" + 
			"                  -0.0001812\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"}\r\n" +
			"  }\r\n"+
			"    pin(Z) { \r\n" + 
			"      direction : output ; \r\n" + 
			"      function : \"(A1&A2)\" ; \r\n" + 
			"      max_capacitance : 0.1 ; \r\n" + 
			"      min_capacitance : 0.0004 ; \r\n" + 
			"      output_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.001009, 0.001043, 0.001056, 0.001058\",\\\r\n" + 
			"                 \"0.0009569, 0.0009777, 0.0009895, 0.0009899\",\\\r\n" + 
			"                 \"0.0009458, 0.0009591, 0.0009767, 0.0009837\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"      }\r\n" + 
			"\r\n" + 
			"      timing() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"        timing_sense : positive_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.01207, 0.01484, 0.0232, 0.03865\",\\\r\n" + 
			"                 \"0.0158, 0.01856, 0.02694, 0.04247\",\\\r\n" + 
			"                 \"0.02738, 0.03029, 0.03877, 0.05432\");\r\n" + 
			"        }\r\n" +
			"  }\r\n" +
			"  }\r\n" +
			"  }\r\n";
	
	String libraryExample = "library(typical) { \r\n" + 
			"  delay_model : table_lookup ; \r\n" + 
			"  library_features(report_delay_calculation, report_power_calculation);\r\n" + 
			"  time_unit : 1ns ; \r\n" + 
			"  voltage_unit : 1V ; \r\n"+
			"  input_voltage(default) { \r\n" + 
			"    vil : 0 ; \r\n" + 
			"    vih : 0.6 ; \r\n" + 
			"    vimin : 0 ; \r\n" + 
			"    vimax : 0.6 ; \r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  output_voltage(default) { \r\n" + 
			"    vol : 0 ; \r\n" + 
			"    voh : 0.6 ; \r\n" + 
			"    vomin : 0 ; \r\n" + 
			"    vomax : 0.6 ; \r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  power_lut_template(pwr_tin_oload_7x7) { \r\n" + 
			"    variable_1 : input_transition_time ; \r\n" + 
			"    variable_2 : total_output_net_capacitance ; \r\n" + 
			"    index_1(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"    index_2(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"  }\r\n" + 
			"  normalized_driver_waveform(ndw_ntin_nvolt_7x2) { \r\n" + 
			"    index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"    index_2(\"0, 1\");\r\n" + 
			"    values(\"0, 0.0006667\",\\\r\n" + 
			"           \"0, 0.01505\",\\\r\n" + 
			"           \"0, 0.06552\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  cell(AND2_X1) { \r\n" + 
			"    cell_leakage_power : 0.005445 ; \r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&!A2\" ; \r\n" + 
			"      value : \"0.002177\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&A2\" ; \r\n" + 
			"      value : \"0.003654\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"A1&!A2\" ; \r\n" + 
			"      value : \"0.003458\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"A1&A2\" ; \r\n" + 
			"      value : \"0.005445\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    pin(A1) { \r\n" + 
			"      capacitance : 0.001439 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"0.0001932, 0.0002476, \\\r\n" + 
			"                  0.0002503\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"      }\r\n" + 
			"    }\\r\\n" +
			"    pin(A2) { \r\n" + 
			"      capacitance : 0.001396 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"-0.0001385, -0.0001735, \\\r\n" + 
			"                  -0.0001812\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" +
			"    pin(Z) { \r\n" + 
			"      direction : output ; \r\n" + 
			"      function : \"(A1&A2)\" ; \r\n" + 
			"      max_capacitance : 0.1 ; \r\n" + 
			"      min_capacitance : 0.0004 ; \r\n" + 
			"      output_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.001009, 0.001043, 0.001056, 0.001058\",\\\r\n" + 
			"                 \"0.0009569, 0.0009777, 0.0009895, 0.0009899\",\\\r\n" + 
			"                 \"0.0009458, 0.0009591, 0.0009767, 0.0009837\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"      }\r\n" + 
			"\r\n" + 
			"      timing() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"        timing_sense : positive_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.01207, 0.01484, 0.0232, 0.03865\",\\\r\n" + 
			"                 \"0.0158, 0.01856, 0.02694, 0.04247\",\\\r\n" + 
			"                 \"0.02738, 0.03029, 0.03877, 0.05432\");\r\n" + 
			"        }\r\n" +
			"  }\r\n" +
			"  }\r\n" +
			"  }\r\n" +
			"  }";
	
	float[] exampleIndex1 = new float[] {0.0004f , 0.009027f , 0.03931f};
	float[] exampleIndex2 = new float[] {0.0004f , 0.002192f , 0.008481f , 0.02049f};
	
	@Test
	void parseLibraryTest() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		assertEquals("typical", library.getName());
		Cell cell1 = library.getCells().get(0);
		assertEquals("AND2_X1", cell1.getName());
		assertEquals(library, cell1.getParentLibrary());
	}
	
	@Test
	void parseCellTest() throws InvalidFileFormatException {
		Cell cell = LibertyParser.parseCell(cellExample, "library");
		assertEquals("AND2_X1", cell.getName());
		assertEquals(0.005445f, cell.getDefaultLeakage());
		assertArrayEquals(new float[] {0.002177f, 0.003654f, 0.003458f, 0.005445f}, cell.getLeakages().getValues());
		InputPin pinA1 = cell.getInPins().get(0);
		InputPin pinA2 = cell.getInPins().get(1);
		OutputPin pinZ = cell.getOutPins().get(0);
		assertEquals(cell, pinA1.getParent());
		assertEquals(cell, pinA2.getParent());
		assertEquals(cell, pinZ.getParent());
		Timing testedTiming = pinZ.getTimings().get(0);
		assertArrayEquals(exampleIndex1, testedTiming.getIndex1());
		assertArrayEquals(exampleIndex2, testedTiming.getIndex2());
		assertEquals(TimingSense.POSITIVE_UNATE, testedTiming.getTimSense());
		assertEquals(TimingType.COMBINATIONAL, testedTiming.getTimType());
		assertEquals(TimingGroup.CELL_FALL, testedTiming.getTimGroup());
		assertEquals(pinA1, testedTiming.getRelatedPin());
	}
	
	@Test
	void parsePinTest2() throws InvalidFileFormatException {
		InputPin pinA1 = new InputPin("A1", null, new ArrayList<InputPower>());
		InputPin pinA2 = new InputPin("A2", null, new ArrayList<InputPower>());
		ArrayList<InputPin> relatedPins = new ArrayList<InputPin>();
		relatedPins.add(pinA1);
		relatedPins.add(pinA2);
		OutputPin pin = (OutputPin) LibertyParser.parsePin(outputPinExample, relatedPins, "library/cell");
		assertEquals(0.1f, pin.getMaxCapacitance());
		assertEquals(0.0004f, pin.getMinCapacitance());
		assertEquals("Z", pin.getName());
		assertEquals("A1&A2", pin.getOutputFunction());
		ArrayList<OutputPower> powers = pin.getOutputPowers();
		ArrayList<Timing> timings = pin.getTimings();
		for (Timing tim : timings) {
			assertArrayEquals(exampleIndex1, tim.getIndex1());
			assertArrayEquals(exampleIndex2, tim.getIndex2());
		}
		for (OutputPower pow : powers) {
			assertArrayEquals(exampleIndex1, pow.getIndex1());
			assertArrayEquals(exampleIndex2, pow.getIndex2());
		}
		Timing testedTiming = timings.get(0);
		assertEquals(pinA1, testedTiming.getRelatedPin());
		assertEquals(TimingSense.POSITIVE_UNATE, testedTiming.getTimSense());
		assertEquals(TimingType.COMBINATIONAL, testedTiming.getTimType());
		assertEquals(TimingGroup.CELL_FALL, testedTiming.getTimGroup());
		float[][] expValues = new float[][] {{0.01207f, 0.01484f, 0.0232f, 0.03865f},
			{0.0158f, 0.01856f, 0.02694f, 0.04247f},
			{0.02738f, 0.03029f, 0.03877f, 0.05432f}};
		assertTrue(Arrays.deepEquals(expValues, testedTiming.getValues()));
		assertEquals(pin, testedTiming.getParentOutPin());
		OutputPower testedPower = powers.get(1);
		assertEquals(pinA2.getName(), testedPower.getRelatedPin().getName());
		assertEquals(PowerGroup.FALL_POWER, testedPower.getPowGroup());
		assertEquals(pin, testedPower.getParentOutPin());
	}
	
	@Test
	void parsePinTest1() throws InvalidFileFormatException { 
		InputPin pin = (InputPin) LibertyParser.parsePin(inputPinExample, new ArrayList<InputPin>(), "library/cell"); 
		assertEquals(pin.getCapacitance(), 0.001469f);
		assertEquals(pin.getName(), "A3");
		ArrayList<InputPower> powers = pin.getInputPowers();
		assertArrayEquals(exampleIndex1, powers.get(0).getIndex1());
		assertArrayEquals(exampleIndex1, powers.get(1).getIndex1());
		assertArrayEquals(new float[] {0.0002351f, 0.0002652f, 0.0002713f}, powers.get(0).getValues());
		assertArrayEquals(new float[] {-9.968e-05f, -0.0001898f, -0.0002075f}, powers.get(1).getValues());
		assertEquals(PowerGroup.FALL_POWER, powers.get(0).getPowGroup());
		assertEquals(PowerGroup.RISE_POWER, powers.get(1).getPowGroup());
		assertEquals(pin, powers.get(0).getParentInPin());
	}
	
	@Test 
	void parseOutPowerTest() throws InvalidFileFormatException {
		OutputPower outPower = LibertyParser.parseOutPower(outputPowerExample, "library/cell/pin");
		float[][] values = new float[][] {{0.001009f, 0.001043f, 0.001056f, 0.001058f}, 
									{0.0009569f, 0.0009777f, 0.0009895f, 0.0009899f},
									{0.0009458f, 0.0009591f, 0.0009767f, 0.0009837f}};
		assertArrayEquals(exampleIndex1, outPower.getIndex1());
		assertArrayEquals(exampleIndex2, outPower.getIndex2());
		assertTrue(Arrays.deepEquals(outPower.getValues(), values));
	}
	
	@Test 
	void parseInPowerTest() throws InvalidFileFormatException {
		InputPower inPower = LibertyParser.parseInPower(inPowerExample, "library/cell/pin");
		assertEquals(PowerGroup.RISE_POWER, inPower.getPowGroup());
		assertArrayEquals(exampleIndex1, inPower.getIndex1());
		assertArrayEquals(new float[] {-1.642e-05f, -4.167e-05f, -5.856e-05f}, inPower.getValues());
	}
	
	@Test
	void parseOutTimingTest() throws InvalidFileFormatException {
		Timing timing = LibertyParser.parseOutTiming(timingExample, TimingSense.NEGATIVE_UNATE, TimingType.COMBINATIONAL_RISE, "library/cell/pin");
		float[][] values = new float[][] {{0.003091f, 0.00493f, 0.01122f, 0.02329f},
										{0.006286f, 0.008379f, 0.01472f, 0.02668f},
										{0.01125f, 0.01586f, 0.02594f, 0.03874f}};
		assertEquals(TimingGroup.CELL_FALL, timing.getTimGroup());
		assertArrayEquals(exampleIndex1, timing.getIndex1());
		assertArrayEquals(exampleIndex2, timing.getIndex2());
		assertEquals(TimingSense.NEGATIVE_UNATE, timing.getTimSense());
		assertEquals(TimingType.COMBINATIONAL_RISE, timing.getTimType());
		assertTrue(Arrays.deepEquals(timing.getValues(), values));
	}
	
	
	@Test
	void parseArrayTest() {
		float[] actualResult = LibertyParser.parseArray(outputPowerExample, "index_1");
		assertArrayEquals(exampleIndex1, actualResult);
    }

	@Test
	void parseDoubleArrayTest() throws InvalidFileFormatException {
		float[][] desiredResult = new float[][] {{0.001009f, 0.001043f, 0.001056f, 0.001058f}, 
												{0.0009569f, 0.0009777f, 0.0009895f, 0.0009899f},
												{0.0009458f, 0.0009591f, 0.0009767f, 0.0009837f}};
	    float[][] actualResult = LibertyParser.parseDoubleArray(outputPowerExample, "values", "library/cell/pin");
	    assertTrue(Arrays.deepEquals(desiredResult, actualResult));
	}
	
	@Test
	void stringToArrayTest() {
		String input = "7.376e-05,7.418e-05,9.276e-05,9.269e-05,8.746e-05,8.816e-05,\\9.114e-05";
        float[] result = LibertyParser.stringToArray(input);
        assertArrayEquals(new float[] {7.376e-05f, 7.418e-05f, 9.276e-05f, 9.269e-05f, 8.746e-05f, 8.816e-05f, 9.114e-05f}, result);
	}
}
