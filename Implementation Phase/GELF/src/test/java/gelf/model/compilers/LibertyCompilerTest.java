package gelf.model.compilers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.Timing;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;

class LibertyCompilerTest {
	private static String exampleString = "library(typical) { \r\n" + 
			"  delay_model : table_lookup ; \r\n" + 
			"  library_features(report_delay_calculation, report_power_calculation);\r\n" + 
			"  time_unit : 1ns ; \r\n" + 
			"  voltage_unit : 1V ; \r\n" + 
			"  current_unit : 1mA ; \r\n" + 
			"  capacitive_load_unit(1, pf);\r\n" + 
			"  pulling_resistance_unit : 1ohm ; \r\n" + 
			"  leakage_power_unit : 1uW ; \r\n" + 
			"  input_threshold_pct_fall : 50 ; \r\n" + 
			"  input_threshold_pct_rise : 50 ; \r\n" + 
			"  output_threshold_pct_fall : 50 ; \r\n" + 
			"  output_threshold_pct_rise : 50 ; \r\n" + 
			"  slew_derate_from_library : 1 ; \r\n" + 
			"  slew_lower_threshold_pct_fall : 20 ; \r\n" + 
			"  slew_lower_threshold_pct_rise : 20 ; \r\n" + 
			"  slew_upper_threshold_pct_fall : 80 ; \r\n" + 
			"  slew_upper_threshold_pct_rise : 80 ; \r\n" + 
			"  nom_process : 1 ; \r\n" + 
			"  nom_temperature : 27 ; \r\n" + 
			"  nom_voltage : 0.7 ; \r\n" + 
			"  default_cell_leakage_power : 0 ; \r\n" + 
			"  default_fanout_load : 0 ; \r\n" + 
			"  default_inout_pin_cap : 0 ; \r\n" + 
			"  default_input_pin_cap : 0 ; \r\n" + 
			"  default_leakage_power_density : 0 ; \r\n" + 
			"  default_output_pin_cap : 0 ; \r\n" + 
			"\r\n" + 
			"  operating_conditions(typical) { \r\n" + 
			"    process : 1 ; \r\n" + 
			"    temperature : 27 ; \r\n" + 
			"    voltage : 0.7 ; \r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  input_voltage(default) { \r\n" + 
			"    vil : 0 ; \r\n" + 
			"    vih : 0.7 ; \r\n" + 
			"    vimin : 0 ; \r\n" + 
			"    vimax : 0.7 ; \r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  output_voltage(default) { \r\n" + 
			"    vol : 0 ; \r\n" + 
			"    voh : 0.7 ; \r\n" + 
			"    vomin : 0 ; \r\n" + 
			"    vomax : 0.7 ; \r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  lu_table_template(ndw_ntin_nvolt_7x2) { \r\n" + 
			"    variable_1 : input_net_transition ; \r\n" + 
			"    variable_2 : normalized_voltage ; \r\n" + 
			"    index_1(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"    index_2(\"1, 2\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  lu_table_template(tmg_ntin_oload_7x7) { \r\n" + 
			"    variable_1 : input_net_transition ; \r\n" + 
			"    variable_2 : total_output_net_capacitance ; \r\n" + 
			"    index_1(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"    index_2(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  power_lut_template(pwr_tin_7) { \r\n" + 
			"    variable_1 : input_transition_time ; \r\n" + 
			"    index_1(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  power_lut_template(pwr_tin_oload_7x7) { \r\n" + 
			"    variable_1 : input_transition_time ; \r\n" + 
			"    variable_2 : total_output_net_capacitance ; \r\n" + 
			"    index_1(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"    index_2(\"1, 2, 3, 4, 5, 6, 7\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  normalized_driver_waveform(ndw_ntin_nvolt_7x2) { \r\n" + 
			"    driver_waveform_name : \"driver_waveform_default_fall\" ; \r\n" + 
			"    index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"    index_2(\"0, 1\");\r\n" + 
			"    values(\"0, 0.0006667\",\\\r\n" + 
			"           \"0, 0.01505\",\\\r\n" + 
			"           \"0, 0.06552\",\\\r\n" + 
			"           \"0, 0.1619\",\\\r\n" + 
			"           \"0, 0.3121\",\\\r\n" + 
			"           \"0, 0.5228\",\\\r\n" + 
			"           \"0, 0.8\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  normalized_driver_waveform(ndw_ntin_nvolt_7x2) { \r\n" + 
			"    driver_waveform_name : \"driver_waveform_default_rise\" ; \r\n" + 
			"    index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"    index_2(\"0, 1\");\r\n" + 
			"    values(\"0, 0.0006667\",\\\r\n" + 
			"           \"0, 0.01505\",\\\r\n" + 
			"           \"0, 0.06552\",\\\r\n" + 
			"           \"0, 0.1619\",\\\r\n" + 
			"           \"0, 0.3121\",\\\r\n" + 
			"           \"0, 0.5228\",\\\r\n" + 
			"           \"0, 0.8\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  normalized_driver_waveform(ndw_ntin_nvolt_7x2) { \r\n" + 
			"    index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"    index_2(\"0, 1\");\r\n" + 
			"    values(\"0, 0.0006667\",\\\r\n" + 
			"           \"0, 0.01505\",\\\r\n" + 
			"           \"0, 0.06552\",\\\r\n" + 
			"           \"0, 0.1619\",\\\r\n" + 
			"           \"0, 0.3121\",\\\r\n" + 
			"           \"0, 0.5228\",\\\r\n" + 
			"           \"0, 0.8\");\r\n" + 
			"  }\r\n" + 
			"\r\n" + 
			"  cell(AND2_X1) { \r\n" + 
			"    cell_leakage_power : 0.008039 ; \r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&!A2\" ; \r\n" + 
			"      value : \"0.003117\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"!A1&A2\" ; \r\n" + 
			"      value : \"0.005393\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"A1&!A2\" ; \r\n" + 
			"      value : \"0.005081\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    leakage_power() { \r\n" + 
			"      when : \"A1&A2\" ; \r\n" + 
			"      value : \"0.008039\" ; \r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    pin(A1) { \r\n" + 
			"      capacitance : 0.001486 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          values(\"0.0002627, 0.0003495, 0.000353, 0.0003513, 0.000352, 0.0003524, \\\r\n" + 
			"                  0.0003527\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          values(\"-4.39e-05, -8.686e-05, -9.103e-05, -8.533e-05, -8.26e-05, -8.096e-05, \\\r\n" + 
			"                  -7.986e-05\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    pin(A2) { \r\n" + 
			"      capacitance : 0.001442 ; \r\n" + 
			"      direction : input ; \r\n" + 
			"      driver_waveform_rise : \"driver_waveform_default_rise\" ; \r\n" + 
			"      driver_waveform_fall : \"driver_waveform_default_fall\" ; \r\n" + 
			"      input_voltage : default ; \r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          values(\"0.0002967, 0.0003143, 0.0003147, 0.0003107, 0.0003065, 0.0003055, \\\r\n" + 
			"                  0.0003048\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          values(\"-0.000201, -0.0002455, -0.0002576, -0.0002582, -0.0002586, -0.0002589, \\\r\n" + 
			"                  -0.0002599\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
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
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.001411, 0.00144, 0.001459, 0.001467, 0.001459, 0.001457, 0.001444\",\\\r\n" + 
			"                 \"0.00131, 0.001331, 0.001353, 0.001344, 0.001355, 0.001355, 0.001353\",\\\r\n" + 
			"                 \"0.001362, 0.001369, 0.00138, 0.001396, 0.001409, 0.001415, 0.001413\",\\\r\n" + 
			"                 \"0.001581, 0.001583, 0.001605, 0.001613, 0.001621, 0.001622, 0.001627\",\\\r\n" + 
			"                 \"0.002, 0.001988, 0.002008, 0.00201, 0.002021, 0.002015, 0.002018\",\\\r\n" + 
			"                 \"0.002659, 0.002623, 0.00261, 0.002612, 0.002612, 0.00262, 0.002616\",\\\r\n" + 
			"                 \"0.003528, 0.003478, 0.003449, 0.00343, 0.003427, 0.003431, 0.003435\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.0009879, 0.000949, 0.0008789, 0.0008505, 0.0007788, 0.0006526, -0.0007449\",\\\r\n" + 
			"                 \"0.0009454, 0.0009323, 0.0009083, 0.0007541, 0.0005675, 0.0003502, -0.0002095\",\\\r\n" + 
			"                 \"0.001, 0.000973, 0.0008875, 0.0008295, 0.0006605, 0.0004556, 8.968e-05\",\\\r\n" + 
			"                 \"0.001234, 0.001212, 0.001194, 0.0009462, 0.0006816, 0.0003945, 0.0001279\",\\\r\n" + 
			"                 \"0.001695, 0.001661, 0.001546, 0.001579, 0.001296, 0.000727, -6.667e-05\",\\\r\n" + 
			"                 \"0.002224, 0.002185, 0.001924, 0.001906, 0.001996, 0.001547, -0.0008939\",\\\r\n" + 
			"                 \"0.003063, 0.003019, 0.002892, 0.002432, 0.002765, 0.002684, 0.002607\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"\r\n" + 
			"      internal_power() { \r\n" + 
			"        related_pin : \"A2\" ; \r\n" + 
			"\r\n" + 
			"        fall_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.001769, 0.00182, 0.00184, 0.001824, 0.001847, 0.00185, 0.001841\",\\\r\n" + 
			"                 \"0.001733, 0.00177, 0.001804, 0.001808, 0.001816, 0.001817, 0.00181\",\\\r\n" + 
			"                 \"0.001823, 0.00182, 0.001835, 0.001862, 0.001872, 0.001878, 0.001878\",\\\r\n" + 
			"                 \"0.002045, 0.00205, 0.002059, 0.002043, 0.002067, 0.00208, 0.002084\",\\\r\n" + 
			"                 \"0.002473, 0.00247, 0.00247, 0.002473, 0.002482, 0.002468, 0.002476\",\\\r\n" + 
			"                 \"0.003116, 0.003097, 0.003083, 0.003068, 0.003079, 0.003083, 0.003076\",\\\r\n" + 
			"                 \"0.003984, 0.003975, 0.003926, 0.003888, 0.003877, 0.003892, 0.003893\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_power(pwr_tin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.001134, 0.001119, 0.0011, 0.0008065, 0.0007269, 0.0001202, 0.0001418\",\\\r\n" + 
			"                 \"0.001101, 0.00109, 0.001033, 0.0009533, 0.0008231, 0.0006011, 0.0001378\",\\\r\n" + 
			"                 \"0.001128, 0.001091, 0.0009206, 0.0007394, 0.0009374, -2.656e-06, 0.0006266\",\\\r\n" + 
			"                 \"0.001241, 0.001196, 0.001225, 0.001057, 0.0006034, 0.000586, -0.0001532\",\\\r\n" + 
			"                 \"0.001584, 0.001544, 0.001456, 0.001492, 0.001317, 0.0007041, 0.0003177\",\\\r\n" + 
			"                 \"0.002175, 0.002136, 0.001993, 0.001875, 0.00202, 0.001875, 0.0008455\",\\\r\n" + 
			"                 \"0.002989, 0.002948, 0.00282, 0.002631, 0.002518, 0.002666, 0.002519\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"\r\n" + 
			"      timing() { \r\n" + 
			"        related_pin : \"A1\" ; \r\n" + 
			"        timing_sense : positive_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.009227, 0.01143, 0.01803, 0.03016, 0.04885, 0.07524, 0.1099\",\\\r\n" + 
			"                 \"0.01257, 0.01477, 0.02138, 0.03353, 0.0523, 0.07879, 0.1132\",\\\r\n" + 
			"                 \"0.02092, 0.02334, 0.03013, 0.04225, 0.06109, 0.08736, 0.1223\",\\\r\n" + 
			"                 \"0.02937, 0.03227, 0.0394, 0.05158, 0.07044, 0.0965, 0.1311\",\\\r\n" + 
			"                 \"0.03723, 0.04082, 0.04887, 0.06124, 0.07984, 0.1063, 0.1406\",\\\r\n" + 
			"                 \"0.04449, 0.0487, 0.05825, 0.07101, 0.08963, 0.1162, 0.1511\",\\\r\n" + 
			"                 \"0.05132, 0.05608, 0.067, 0.08088, 0.09967, 0.1265, 0.1614\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        cell_rise(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.01289, 0.01563, 0.02339, 0.03704, 0.05819, 0.08782, 0.1265\",\\\r\n" + 
			"                 \"0.01562, 0.01833, 0.02609, 0.03977, 0.06081, 0.09052, 0.1291\",\\\r\n" + 
			"                 \"0.02425, 0.02705, 0.03502, 0.04877, 0.06987, 0.09943, 0.1384\",\\\r\n" + 
			"                 \"0.03324, 0.03645, 0.04457, 0.05842, 0.07964, 0.1092, 0.1479\",\\\r\n" + 
			"                 \"0.04193, 0.04567, 0.05452, 0.06849, 0.08973, 0.1197, 0.1581\",\\\r\n" + 
			"                 \"0.05045, 0.05474, 0.06488, 0.07911, 0.1004, 0.1305, 0.1697\",\\\r\n" + 
			"                 \"0.05848, 0.06343, 0.07494, 0.09012, 0.1118, 0.1411, 0.1802\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        fall_transition(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.003708, 0.006041, 0.01446, 0.03116, 0.05712, 0.09352, 0.1427\",\\\r\n" + 
			"                 \"0.00375, 0.006053, 0.01451, 0.03105, 0.05699, 0.09435, 0.1424\",\\\r\n" + 
			"                 \"0.004758, 0.00683, 0.01481, 0.0311, 0.05723, 0.09398, 0.1423\",\\\r\n" + 
			"                 \"0.006495, 0.008466, 0.01579, 0.03154, 0.05688, 0.09334, 0.1416\",\\\r\n" + 
			"                 \"0.008422, 0.01068, 0.01742, 0.03237, 0.05739, 0.0936, 0.1407\",\\\r\n" + 
			"                 \"0.01062, 0.01324, 0.01976, 0.03375, 0.05853, 0.09452, 0.142\",\\\r\n" + 
			"                 \"0.01277, 0.0159, 0.02289, 0.03627, 0.0599, 0.09554, 0.1428\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_transition(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.004931, 0.007664, 0.01726, 0.03641, 0.06686, 0.1088, 0.1657\",\\\r\n" + 
			"                 \"0.004899, 0.007618, 0.01716, 0.03635, 0.06678, 0.1097, 0.1666\",\\\r\n" + 
			"                 \"0.005685, 0.008306, 0.01766, 0.0364, 0.06689, 0.1098, 0.1661\",\\\r\n" + 
			"                 \"0.007295, 0.009679, 0.01835, 0.03683, 0.06662, 0.1096, 0.1648\",\\\r\n" + 
			"                 \"0.0092, 0.01179, 0.01985, 0.03751, 0.06736, 0.1091, 0.1645\",\\\r\n" + 
			"                 \"0.01115, 0.01427, 0.02191, 0.03881, 0.06787, 0.1104, 0.1655\",\\\r\n" + 
			"                 \"0.01341, 0.01667, 0.02517, 0.04081, 0.06897, 0.111, 0.1665\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"\r\n" + 
			"      timing() { \r\n" + 
			"        related_pin : \"A2\" ; \r\n" + 
			"        timing_sense : positive_unate ; \r\n" + 
			"        timing_type : combinational ; \r\n" + 
			"\r\n" + 
			"        cell_fall(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.01033, 0.0126, 0.01929, 0.03152, 0.05034, 0.07665, 0.1119\",\\\r\n" + 
			"                 \"0.01367, 0.01593, 0.02264, 0.03487, 0.05377, 0.07992, 0.1146\",\\\r\n" + 
			"                 \"0.02313, 0.02547, 0.03234, 0.04452, 0.06346, 0.09002, 0.1243\",\\\r\n" + 
			"                 \"0.03322, 0.03607, 0.04324, 0.05548, 0.07436, 0.1006, 0.1351\",\\\r\n" + 
			"                 \"0.04308, 0.04651, 0.05443, 0.06675, 0.08556, 0.1121, 0.1466\",\\\r\n" + 
			"                 \"0.05279, 0.05678, 0.06583, 0.07841, 0.09741, 0.1239, 0.1586\",\\\r\n" + 
			"                 \"0.06235, 0.06688, 0.07723, 0.09048, 0.1097, 0.1364, 0.171\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        cell_rise(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.01394, 0.01667, 0.02443, 0.03808, 0.05918, 0.08923, 0.1275\",\\\r\n" + 
			"                 \"0.01694, 0.01966, 0.02743, 0.04112, 0.06232, 0.0918, 0.1312\",\\\r\n" + 
			"                 \"0.0243, 0.02715, 0.03509, 0.04879, 0.06999, 0.09979, 0.139\",\\\r\n" + 
			"                 \"0.03201, 0.03518, 0.04358, 0.05744, 0.07863, 0.108, 0.1468\",\\\r\n" + 
			"                 \"0.03924, 0.04311, 0.05232, 0.06649, 0.08775, 0.1176, 0.156\",\\\r\n" + 
			"                 \"0.04625, 0.05071, 0.06114, 0.07598, 0.09749, 0.1272, 0.1665\",\\\r\n" + 
			"                 \"0.05264, 0.05773, 0.06954, 0.08533, 0.1073, 0.1374, 0.1769\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        fall_transition(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.003868, 0.006203, 0.01461, 0.03117, 0.05735, 0.09414, 0.1427\",\\\r\n" + 
			"                 \"0.003888, 0.006221, 0.01464, 0.03116, 0.0575, 0.09408, 0.142\",\\\r\n" + 
			"                 \"0.004697, 0.006879, 0.01483, 0.03134, 0.05753, 0.09472, 0.1425\",\\\r\n" + 
			"                 \"0.006397, 0.008377, 0.0157, 0.03162, 0.05681, 0.09369, 0.1419\",\\\r\n" + 
			"                 \"0.00821, 0.01031, 0.01702, 0.03202, 0.05775, 0.09357, 0.1408\",\\\r\n" + 
			"                 \"0.01008, 0.01251, 0.01879, 0.03318, 0.05799, 0.09415, 0.1428\",\\\r\n" + 
			"                 \"0.01232, 0.0151, 0.02145, 0.035, 0.05912, 0.09491, 0.1436\");\r\n" + 
			"        }\r\n" + 
			"\r\n" + 
			"        rise_transition(tmg_ntin_oload_7x7) { \r\n" + 
			"          index_1(\"0.0004, 0.009027, 0.03931, 0.09714, 0.1872, 0.3137, 0.48\");\r\n" + 
			"          index_2(\"0.0004, 0.002192, 0.008481, 0.02049, 0.0392, 0.06545, 0.1\");\r\n" + 
			"          values(\"0.004908, 0.007648, 0.01725, 0.03647, 0.06665, 0.1101, 0.1654\",\\\r\n" + 
			"                 \"0.004893, 0.007612, 0.01721, 0.03649, 0.06689, 0.1092, 0.1658\",\\\r\n" + 
			"                 \"0.005471, 0.008216, 0.01762, 0.03654, 0.0663, 0.1101, 0.1655\",\\\r\n" + 
			"                 \"0.006965, 0.009626, 0.01842, 0.03668, 0.06622, 0.1092, 0.1652\",\\\r\n" + 
			"                 \"0.00888, 0.01166, 0.01991, 0.03738, 0.0672, 0.1088, 0.1644\",\\\r\n" + 
			"                 \"0.01082, 0.01402, 0.02213, 0.03898, 0.06805, 0.11, 0.1652\",\\\r\n" + 
			"                 \"0.01291, 0.01641, 0.02493, 0.04109, 0.06944, 0.111, 0.167\");\r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"  }\r\n" +
			"}";
	public static Library exampleLibrary;
	
	@BeforeAll
	static void createLib() throws IOException, InvalidFileFormatException {
		/*String content;
		content = new String(Files.readAllBytes(Paths.get("resources.baseline.txt")));*/
		LibertyParser.setUp();
		exampleLibrary = LibertyParser.parseLibrary(exampleString);
	}
	
	@Test
	void compileLibraryTest() throws InvalidFileFormatException {
		String compiledString = LibertyCompiler.compile(exampleLibrary);
		Library resLibrary = LibertyParser.parseLibrary(compiledString);
		assertEquals(exampleLibrary.getName(), resLibrary.getName());
		for (int i = 0; i < exampleLibrary.getCells().size(); i++) {
			assertEquals(exampleLibrary.getCells().get(i).getName(), resLibrary.getCells().get(i).getName());
		}
		assertArrayEquals(exampleLibrary.getUnits(), resLibrary.getUnits());
	}
	
	@Test
	void compileCellTest() throws InvalidFileFormatException {
		Cell focusedCell = exampleLibrary.getCells().get(0);
		String compiledString = LibertyCompiler.compile(focusedCell);
		Cell resCell = LibertyParser.parseCell(compiledString.replaceAll("\\s+", ""), "library");
		assertEquals(focusedCell.getName(), resCell.getName());
		for (int i = 0; i < focusedCell.getLeakages().getValues().length; i++) {
			assertEquals(focusedCell.getLeakages().getValues()[i], resCell.getLeakages().getValues()[i]);
		}
		assertEquals(focusedCell.getDefaultLeakage(), resCell.getDefaultLeakage());
		for (int i = 0; i < focusedCell.getInPins().size(); i++) {
			assertEquals(focusedCell.getInPins().get(i).getName(), resCell.getInPins().get(i).getName());
		}
		for (int i = 0; i < focusedCell.getOutPins().size(); i++) {
			assertEquals(focusedCell.getOutPins().get(i).getName(), resCell.getOutPins().get(i).getName());
		}
	}

	@Test
	void compileInPinTest() throws InvalidFileFormatException {
		ArrayList<InputPin> relatedPins = exampleLibrary.getCells().get(0).getInPins();
		InputPin focusedPin = relatedPins.get(0);
		String compiledString = LibertyCompiler.compile(focusedPin);
		Pin resultingPin = LibertyParser.parsePin(compiledString.replaceAll("\\s+", ""), relatedPins, "library/AND2_X1");
		assertTrue(resultingPin instanceof InputPin);
		InputPin resInPin = (InputPin) resultingPin;
		assertEquals(focusedPin.getName(), resInPin.getName());
		assertEquals(focusedPin.getCapacitance(), resInPin.getCapacitance());
		InputPower focusedInPower = focusedPin.getInputPowers().get(1);
		InputPower resInPower = resInPin.getInputPowers().get(1);
		assertEquals(focusedInPower.getPowGroup(), resInPower.getPowGroup());
		assertArrayEquals(focusedInPower.getIndex1(), resInPower.getIndex1());
		assertArrayEquals(focusedInPower.getValues(), resInPower.getValues());
	}
	
	@Test
	void compileOutPinTest() throws InvalidFileFormatException {
		ArrayList<InputPin> relatedPins = exampleLibrary.getCells().get(0).getInPins();
		OutputPin focusedPin = exampleLibrary.getCells().get(0).getOutPins().get(0);
		String compiledString = LibertyCompiler.compile(focusedPin);
		Pin resultingPin = LibertyParser.parsePin(compiledString.replaceAll("\\s+", ""), relatedPins, "library/AND2_X1");
		assertTrue(resultingPin instanceof OutputPin);
		OutputPin resOutPin = (OutputPin) resultingPin;
		assertEquals(focusedPin.getName(), resOutPin.getName());
		assertEquals(focusedPin.getOutputFunction(), resOutPin.getOutputFunction());
		assertEquals(focusedPin.getMaxCapacitance(), resOutPin.getMaxCapacitance());
		assertEquals(focusedPin.getMinCapacitance(), resOutPin.getMinCapacitance());
		for (int i = 0; i < focusedPin.getOutputPowers().size(); i++) {
			OutputPower focusedOutPower = focusedPin.getOutputPowers().get(i);
			OutputPower resOutPower = resOutPin.getOutputPowers().get(i);
			assertEquals(focusedOutPower.getPowGroup(), resOutPower.getPowGroup());
			assertArrayEquals(focusedOutPower.getIndex1(), resOutPower.getIndex1());
			assertArrayEquals(focusedOutPower.getIndex2(), resOutPower.getIndex2());
			assertArrayEquals(focusedOutPower.getValues(), resOutPower.getValues());
		}
		for (int i = 0; i < focusedPin.getTimings().size(); i++) {
			Timing focusedTiming = focusedPin.getTimings().get(i);
			Timing resTiming = resOutPin.getTimings().get(i);
			assertEquals(focusedTiming.getTimGroup(), resTiming.getTimGroup());
			assertEquals(focusedTiming.getTimType(), resTiming.getTimType());
			assertEquals(focusedTiming.getTimSense(), resTiming.getTimSense());
			assertArrayEquals(focusedTiming.getIndex1(), resTiming.getIndex1());
			assertArrayEquals(focusedTiming.getIndex2(), resTiming.getIndex2());
			assertArrayEquals(focusedTiming.getValues(), resTiming.getValues());
		}
	}
}
