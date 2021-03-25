package gelf.controller.listeners;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.attributes.InputPower;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.view.composites.TextEditor;

class EditListenerTest {
	String libraryExample = "library(typical) { \r\n" + 
			"  delay_model : table_lookup ; \r\n" + 
			"  library_features(report_delay_calculation, report_power_calculation);\r\n" + 
			"  time_unit : 1ns ; \r\n" + 
			"  voltage_unit : 1V ; \r\n" + 
			"  current_unit : 1mA ; \r\n" + 
			"  capacitive_load_unit(1, pf);\r\n" + 
			"  pulling_resistance_unit : 1ohm ; \r\n" + 
			"  leakage_power_unit : 1uW ; " +
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
	
	String libraryExample2 = "library(hallo) { \r\n" + 
			"  delay_model : table_lookup ; \r\n" + 
			"  library_features(report_delay_calculation, report_power_calculation);\r\n" + 
			"  time_unit : 1ns ; \r\n" + 
			"  voltage_unit : 1V ; \r\n" + 
			"  current_unit : 1mA ; \r\n" + 
			"  capacitive_load_unit(1, pf);\r\n" + 
			"  pulling_resistance_unit : 1ohm ; \r\n" + 
			"  leakage_power_unit : 1uW ; " +
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

	@Test
	void actionPerformedTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvalidFileFormatException {
		ArrayList<InputPower> ip = new ArrayList<InputPower>();
		LibertyParser.setUp();
		Library lib = LibertyParser.parseLibrary(libraryExample);
		TextEditor e = new TextEditor(lib, Model.getInstance().getCurrentProject(), 0, 0);
		Field m = e.getClass().getDeclaredField("textArea");
				m.setAccessible(true);
		JTextArea a = (JTextArea) m.get(e);
		a.setText(libraryExample2);
		EditListener lis = new EditListener(e);
		lis.actionPerformed(new ActionEvent(lis, 0, null));
		assertEquals("hallo", lib.getName());
	}
	
	@Test
	void actionPerformedTest2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvalidFileFormatException {
		ArrayList<InputPower> ip = new ArrayList<InputPower>();
		LibertyParser.setUp();
		Library lib = LibertyParser.parseLibrary(libraryExample);
		TextEditor e = new TextEditor(lib, Model.getInstance().getCurrentProject(), 0, 0);
		Field m = e.getClass().getDeclaredField("textArea");
				m.setAccessible(true);
		JTextArea a = (JTextArea) m.get(e);
		a.setText("");
		EditListener lis = new EditListener(e);
		lis.actionPerformed(new ActionEvent(lis, 0, null));
	}
}
