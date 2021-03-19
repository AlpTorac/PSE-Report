package gelf.model.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;

class TextEditCommandTest {
	
	String libraryExample = "library(typical) {" + 
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
	
	String libraryExample2 = "library(typical) {" + 
			"  cell(AND8_X1) { \r\n" + 
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
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"        related_pin : \"A2\" ; \r\n" + 
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
	
	String inputPinExample2 = "pin(A3) { \r\n" + 
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
			"                  0.004\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          values(\"-9.968e-05, -0.0001898, \\\r\n" + 
			"                  -0.022075\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"\r\n";
	
    String outputPinExample = "    pin(Z) { \r\n" + 
			"      direction : output ; \r\n" + 
			"      function : \"(A1&A2)\" ; \r\n" + 
			"      max_capacitance : 0.1 ; \r\n" + 
			"      min_capacitance : 0.0004 ; \r\n" + 
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
			"  }\r\n";
    
    String outputPinExample2 = "    pin(Z) { \r\n" + 
			"      direction : output ; \r\n" + 
			"      function : \"(A1&A2)\" ; \r\n" + 
			"      max_capacitance : 1 ; \r\n" + 
			"      min_capacitance : 0.0004 ; \r\n" + 
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
			"        timing_sense : negative_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.01207, 0.01484, 0.0232, 0.03865\",\\\r\n" + 
			"                 \"0.0158, 0.01856, 0.02694, 0.04247\",\\\r\n" + 
			"                 \"0.02738, 0.03029, 0.03877, 0.05432\");\r\n" + 
			"        }\r\n" +
			"  }\r\n";
    
    String outputPinExample3 = "    pin(Z) { \r\n" + 
			"      direction : throughput ; \r\n" + 
			"      function : \"(A1&A2)\" ; \r\n" + 
			"      max_capacitance : 1 ; \r\n" + 
			"      min_capacitance : 0.0004 ; \r\n" + 
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
			"        timing_sense : negative_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049\");\r\n" + 
			"          values(\"0.01207, 0.01484, 0.0232, 0.03865\",\\\r\n" + 
			"                 \"0.0158, 0.01856, 0.02694, 0.04247\",\\\r\n" + 
			"                 \"0.02738, 0.03029, 0.03877, 0.05432\");\r\n" + 
			"        }\r\n" +
			"  }\r\n";
    
    String cellExample = "  cell(AND2_X1) { \r\n" + 
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
			"  }\r\n";
    
    String cellExample2 = "  cell(AND2_X1) { \r\n" + 
			"    cell_leakage_power : 0.005445 ; \r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&!A2\" ; \r\n" + 
			"      value : \"0.6\" ; \r\n" + 
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
			"                 \"0.02738, 0.03029, 0.03877, 7\");\r\n" + 
			"        }\r\n" +
			"  }\r\n" +
			"  }\r\n";
	
	@BeforeAll
	static void buildUp() {
		LibertyParser.setUp();
	}
	
	@Test
	void TextEditCommandTestInputPin() throws InvalidFileFormatException {
		InputPin inPin = (InputPin) LibertyParser.parsePin(inputPinExample, new ArrayList<InputPin>(), "library/cell");
		Library library = LibertyParser.parseLibrary(libraryExample);
		inPin.setParent(library.getCells().get(0));
		TextEditCommand command = new TextEditCommand(inputPinExample, inputPinExample2, inPin);
		command.execute();
		assertEquals(0.004f, inPin.getInputPowers().get(0).getValues()[2]);
		assertEquals(-0.022075f, inPin.getInputPowers().get(1).getValues()[2]);
	}
	
	@Test
	void TextEditCommandTestOutputPin() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		OutputPin outPin = (OutputPin) LibertyParser.parsePin(outputPinExample, library.getCells().get(0).getInPins(), "library/cell");
		outPin.setParent(library.getCells().get(0));
		TextEditCommand command = new TextEditCommand(outputPinExample, outputPinExample2, outPin);
		command.execute();
		assertEquals(1f, outPin.getMaxCapacitance());
		assertEquals(TimingSense.NEGATIVE_UNATE, outPin.getTimings().get(0).getTimSense());
	}
	
	@Test
	void TextEditCommandTestChangeType() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		OutputPin outPin = (OutputPin) LibertyParser.parsePin(outputPinExample, library.getCells().get(0).getInPins(), "library/cell");
		outPin.setParent(library.getCells().get(0));
		InvalidFileFormatException thrown = assertThrows(
				InvalidFileFormatException.class,
		           () -> new TextEditCommand(outputPinExample, inputPinExample, outPin)
		    );
		assertEquals("Element type change not possible within the Element visualiser", thrown.getMessage());
	}
	
	@Test
	void TextEditCommandTestInvalidFileFormat() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		OutputPin outPin = (OutputPin) LibertyParser.parsePin(outputPinExample, library.getCells().get(0).getInPins(), "library/cell");
		outPin.setParent(library.getCells().get(0));
		InvalidFileFormatException thrown = assertThrows(
				InvalidFileFormatException.class,
		           () -> new TextEditCommand(outputPinExample, "", outPin)
		    );
		assertEquals("File format is invalid", thrown.getMessage());
	}
	
	@Test
	void TextEditCommandTestInvalidFileFormat2() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		OutputPin outPin = (OutputPin) LibertyParser.parsePin(outputPinExample, library.getCells().get(0).getInPins(), "library/cell");
		outPin.setParent(library.getCells().get(0));
		InvalidFileFormatException thrown = assertThrows(
				InvalidFileFormatException.class,
		           () -> new TextEditCommand(outputPinExample, outputPinExample3, outPin)
		    );
		assertEquals("Direction \"throughput\" in pin \"typical/AND2_X1/Z\" not supported" + 
				"", thrown.getMessage());
	}
	
	@Test
	void TextEditCommandTestCell() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		Cell cell = LibertyParser.parseCell(cellExample, "library");
		cell.setParentLibrary(library);
		TextEditCommand command = new TextEditCommand(cellExample, cellExample2, cell);
		command.execute();
		assertEquals(7f, cell.getOutPins().get(0).getTimings().get(0).getValues()[2][3]);
		assertEquals(0.6f, cell.getLeakages().getValues()[0]);
	}
	
	@Test
	void TextEditCommandTestLibrary() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		TextEditCommand command = new TextEditCommand(libraryExample, libraryExample2, library);
		command.execute();
		assertEquals(library.getCells().get(0).getInPins().get(1), library.getCells().get(0).getOutPins().get(0).getOutputPowers().get(0).getRelatedPin());
		assertEquals("AND8_X1", library.getCells().get(0).getName());
	}
	
	@Test
	void TextEditCommandTestUndo() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample);
		TextEditCommand command = new TextEditCommand(libraryExample, libraryExample2, library);
		command.execute();
		assertEquals(library.getCells().get(0).getInPins().get(1), library.getCells().get(0).getOutPins().get(0).getOutputPowers().get(0).getRelatedPin());
		assertEquals("AND8_X1", library.getCells().get(0).getName());
		command.undo();
		assertEquals(library.getCells().get(0).getInPins().get(0), library.getCells().get(0).getOutPins().get(0).getOutputPowers().get(0).getRelatedPin());
		assertEquals("AND2_X1", library.getCells().get(0).getName());
	}

}
