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
	String test = "	cell(INV_X1) {\r\n" + 
			"		cell_leakage_power : 0.002748 ;\r\n" + 
			"		leakage_power() {\r\n" + 
			"			when : \"!I\" ;\r\n" + 
			"			value : \"0.002748\" ;\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"		leakage_power() {\r\n" + 
			"			when : \"I\" ;\r\n" + 
			"			value : \"0.002645\" ;\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"		pin(I) {\r\n" + 
			"			capacitance : 0.001444 ;\r\n" + 
			"			direction : input ;\r\n" + 
			"\r\n" + 
			"		}\r\n" + 
			"		pin(ZN) {\r\n" + 
			"			direction : output ;\r\n" + 
			"			function : \"(!I)\" ;\r\n" + 
			"			max_capacitance : 0.1 ;\r\n" + 
			"			min_capacitance : 0.0004 ;\r\n" + 
			"\r\n" + 
			"			internal_power() {\r\n" + 
			"				related_pin : \"I\" ;\r\n" + 
			"\r\n" + 
			"				fall_power() {\r\n" + 
			"					index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"					index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"					values(\"5.461e-05, 5.682e-05, 5.592e-05, 6.007e-05, 5.962e-05, 5.967e-05, 5.971e-05\",\\\r\n" + 
			"					       \"-3.152e-05, -2.026e-05, 1.892e-06, 1.611e-05, 1.772e-05, 2.174e-05, 2.114e-05\",\\\r\n" + 
			"					       \"3.263e-05, 1.629e-05, 3.244e-05, 2.391e-05, 2.024e-05, 2.378e-05, 2.279e-05\",\\\r\n" + 
			"					       \"0.00031, 0.0002543, 0.0001724, 0.0001112, 7.198e-05, 5.895e-05, 4.92e-05\",\\\r\n" + 
			"					       \"0.0008151, 0.0007159, 0.0005395, 0.0003968, 0.0002889, 0.0002131, 0.000166\",\\\r\n" + 
			"					       \"0.001373, 0.001228, 0.0009525, 0.00072, 0.0005474, 0.0004049, 0.0003184\",\\\r\n" + 
			"					       \"0.002257, 0.002077, 0.001687, 0.001333, 0.001059, 0.0008471, 0.0006583\");\r\n" + 
			"				}\r\n" + 
			"\r\n" + 
			"				rise_power() {\r\n" + 
			"					index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"					index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"					values(\"0.0009257, 0.0009109, 0.0008489, 0.0004351, 0.00051, 0.0003671, -2.507e-04\",\\\r\n" + 
			"					       \"0.0009077, 0.0008728, 0.0008361, 0.0008033, 0.0004164, 6.903e-05, -1.41e-03\",\\\r\n" + 
			"					       \"0.001003, 0.001013, 0.0008694, 0.0004171, 0.0004976, 0.0001249, -2.14e-04\",\\\r\n" + 
			"					       \"0.001257, 0.001198, 0.001144, 0.0009086, 0.000141, -1.435e-04, -8.7e-04\",\\\r\n" + 
			"					       \"0.001711, 0.001619, 0.001431, 0.001277, 0.0009251, 0.0001137, -3.592e-04\",\\\r\n" + 
			"					       \"0.002373, 0.002252, 0.001956, 0.001754, 0.001411, 0.0008305, -2.063e-03\",\\\r\n" + 
			"					       \"0.003277, 0.003115, 0.002754, 0.00235, 0.002209, 0.001607, 0.0007789\");\r\n" + 
			"				}\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			timing() {\r\n" + 
			"				related_pin : \"I\" ;\r\n" + 
			"				timing_sense : negative_unate ;\r\n" + 
			"				timing_type : combinational ;\r\n" + 
			"\r\n" + 
			"				cell_fall() {\r\n" + 
			"					index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"					index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"					values(\"0.002903, 0.004739, 0.01105, 0.02309, 0.04186, 0.06803, 0.1027\",\\\r\n" + 
			"					       \"0.006017, 0.008135, 0.01447, 0.02643, 0.04482, 0.07123, 0.1056\",\\\r\n" + 
			"					       \"0.01096, 0.01553, 0.02566, 0.03851, 0.05695, 0.08308, 0.1174\",\\\r\n" + 
			"					       \"0.01518, 0.02239, 0.03854, 0.05888, 0.08103, 0.1067, 0.141\",\\\r\n" + 
			"					       \"0.01858, 0.02833, 0.05144, 0.07989, 0.1111, 0.1434, 0.1777\",\\\r\n" + 
			"					       \"0.02118, 0.03363, 0.06313, 0.1003, 0.1408, 0.1841, 0.228\",\\\r\n" + 
			"					       \"0.02274, 0.0376, 0.07244, 0.1194, 0.1704, 0.2235, 0.2798\"); \r\n" + 
			"				}\r\n" + 
			"\r\n" + 
			"				cell_rise() {\r\n" + 
			"					index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"					index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"					values(\"0.003231, 0.005288, 0.01241, 0.02596, 0.04703, 0.07708, 0.1153\",\\\r\n" + 
			"					       \"0.006405, 0.008628, 0.0158, 0.02938, 0.04989, 0.07939, 0.1182\",\\\r\n" + 
			"					       \"0.01213, 0.01667, 0.02756, 0.04149, 0.06244, 0.09183, 0.1309\",\\\r\n" + 
			"					       \"0.01781, 0.02524, 0.04252, 0.06368, 0.08655, 0.1138, 0.1523\",\\\r\n" + 
			"					       \"0.02355, 0.03346, 0.05731, 0.08696, 0.1196, 0.1534, 0.1916\",\\\r\n" + 
			"					       \"0.02928, 0.04178, 0.07172, 0.1106, 0.153, 0.198, 0.2442\",\\\r\n" + 
			"					       \"0.03504, 0.05018, 0.08675, 0.1336, 0.1867, 0.2427, 0.3009\"); \r\n" + 
			"				}\r\n" + 
			"\r\n" + 
			"				fall_transition() {\r\n" + 
			"					index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"					index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"					values(\"0.002854, 0.005345, 0.0141, 0.0308, 0.05676, 0.09379, 0.1416\",\\\r\n" + 
			"					       \"0.004057, 0.005863, 0.01403, 0.03081, 0.05712, 0.09363, 0.1416\",\\\r\n" + 
			"					       \"0.009504, 0.01264, 0.01944, 0.03223, 0.05707, 0.09339, 0.1413\",\\\r\n" + 
			"					       \"0.0163, 0.02118, 0.03236, 0.04599, 0.06472, 0.0954, 0.1411\",\\\r\n" + 
			"					       \"0.02449, 0.0315, 0.04682, 0.06565, 0.08602, 0.111, 0.1502\",\\\r\n" + 
			"					       \"0.03429, 0.04285, 0.06307, 0.08768, 0.1141, 0.1418, 0.1741\",\\\r\n" + 
			"					       \"0.04534, 0.05684, 0.08207, 0.1127, 0.1458, 0.18, 0.2162\"); \r\n" + 
			"				}\r\n" + 
			"\r\n" + 
			"				rise_transition() {\r\n" + 
			"					index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"					index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"					values(\"0.003297, 0.006196, 0.01639, 0.03593, 0.06614, 0.1101, 0.1642\",\\\r\n" + 
			"					       \"0.004448, 0.00669, 0.01643, 0.03587, 0.06661, 0.1095, 0.1654\",\\\r\n" + 
			"					       \"0.0102, 0.01361, 0.0212, 0.03721, 0.06624, 0.1091, 0.1651\",\\\r\n" + 
			"					       \"0.0171, 0.02235, 0.03439, 0.04898, 0.07198, 0.1104, 0.1644\",\\\r\n" + 
			"					       \"0.02544, 0.03286, 0.04932, 0.06967, 0.09223, 0.1231, 0.1706\",\\\r\n" + 
			"					       \"0.03558, 0.04505, 0.06665, 0.09292, 0.123, 0.1527, 0.1928\",\\\r\n" + 
			"					       \"0.04757, 0.05868, 0.08542, 0.1187, 0.1549, 0.1943, 0.2336\"); \r\n" + 
			"				}\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"		}\r\n" + 
			"	}";
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
	void parsyTest() throws InvalidFileFormatException {
		Cell celly = LibertyParser.parseCell(test, "library");
		assertTrue(true);
	}
	
	@Test
	void parseLibraryTest() throws InvalidFileFormatException {
		Library library = LibertyParser.parseLibrary(libraryExample.replaceAll("\\s+", ""));
		assertEquals("typical", library.getName());
		Cell cell1 = library.getCells().get(0);
		assertEquals("AND2_X1", cell1.getName());
		assertEquals(library, cell1.getParentLibrary());
	}
	
	@Test
	void parseCellTest() throws InvalidFileFormatException {
		Cell cell = LibertyParser.parseCell(cellExample.replaceAll("\\s+", ""), "library");
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
		OutputPin pin = (OutputPin) LibertyParser.parsePin(outputPinExample.replaceAll("\\s+", ""), relatedPins, "library/cell");
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
		InputPin pin = (InputPin) LibertyParser.parsePin(inputPinExample.replaceAll("\\s+", ""), new ArrayList<InputPin>(), "library/cell"); 
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
