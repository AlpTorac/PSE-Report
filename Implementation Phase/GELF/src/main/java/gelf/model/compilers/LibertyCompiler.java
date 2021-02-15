package gelf.model.compilers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import gelf.model.elements.*;
import gelf.model.elements.attributes.*;
import gelf.model.exceptions.InvalidFileFormatException;
/**
 * Compiles the data objects to their Liberty Format equivalents
 */
public class LibertyCompiler {
    private static DecimalFormat format = new DecimalFormat("#.#######");
    private static DecimalFormat scFormat = new DecimalFormat("0.###E00");

    public static String compile(Library library) {
        String output = "library(" + library.getName() + ") {\n"; //+ library.getFileData()[0];
        for (Cell cell : library.getCells()) {
            output += compile(cell);
        }
        output += "\n}";

        return output;
    }

    /**
     * 
     * @param cell
     * @return
     * @throws InvalidFileFormatException 
     */
    public static String compile(Cell cell) {
        String output = "\tcell(" + cell.getName() + ") {\n"; //+ cell.getCellData();
        output += "\t\tcell_leakage_power : " + formatFloat(cell.getDefaultLeakage()) + ";";
        ArrayList<InputPin> inPins = cell.getInPins();
        String[] inPinNames = new String[inPins.size()];
        for (int i = 0; i < inPins.size(); i++) {
            inPinNames[i] = inPins.get(i).getName();
        }
        output += "\n";
        for (int i = 0; i < cell.getLeakages().getValues().length; i++) {
            String leakageFunction = "";
            for (int j = 0; j < inPinNames.length; j++) {
            	int reversedIndex = inPinNames.length - 1 - j;
                if ((i & (reversedIndex + 1)) == 0) {
                    leakageFunction += "!";
                }
                leakageFunction += inPinNames[j] + "&";
            }
            leakageFunction = leakageFunction.substring(0, leakageFunction.length() - 1);
            output += "\t\tleakage_power() {\n"
            + "\t\t\twhen : \"" 
            + leakageFunction
            + "\" ;\n"
            + "\t\t\tvalue : \"" + formatFloat(cell.getLeakages().getValues()[i]) + "\" ;\n" 
            + "\t\t}\n\n";
        }
        for (InputPin inPin : inPins) {
            output += compile(inPin);
        }
        for (OutputPin outPin : cell.getOutPins()) {
            output += compile(outPin);
        }
        output += "\n\t}";
        return output;
    }

    public static String compile(InputPin pin) {
        String index1String = "\t\t\t\t\tindex_1(" + compileArray(pin.getParent().getIndex1()) + ");\n";
        String output = "\n\t\tpin(" + pin.getName() + ") {\n"
        + "\t\t\tcapacitance : " + formatFloat(pin.getCapacitance()) + " ;\n"
        + "\t\t\tdirection : input ;\n";
        if (!pin.getInputPowers().isEmpty()){
            output += "\n\t\t\tinternal_power() {\n";
            for (InputPower inputPower : pin.getInputPowers()) {
                String valuesString = "\t\t\t\t\tvalues(" + compileArray(inputPower.getValues()) + ");\n";
                output += "\n\t\t\t\t" + inputPower.getPowGroup().name().toLowerCase() + "() {\n"
                + index1String + valuesString + "\t\t\t\t}\n";
            }
            output += "\t\t\t}\n";
        }
        output += "\n\t\t}";
        return output;
    }

    public static String compile(OutputPin pin) {
        String index1String = "\t\t\t\t\tindex_1(" + compileArray(pin.getParent().getIndex1()) + ");\n";
        String index2String = "\t\t\t\t\tindex_2(" + compileArray(pin.getParent().getIndex2()) + ");\n";
        String output = "\n\t\tpin(" + pin.getName() + ") {\n"
        + "\t\t\tdirection : output ;\n"
        + "\t\t\tfunction : \"(" + pin.getOutputFunction() + ")\" ;\n"
        + "\t\t\tmax_capacitance : " + formatFloat(pin.getMaxCapacitance()) + " ;\n"
        + "\t\t\tmin_capacitance : " + formatFloat(pin.getMinCapacitance()) + " ;\n";
        for (InputPin relatedPin : pin.getParent().getInPins()) {
            boolean usedPin = false;
            for (OutputPower outputPower : pin.getOutputPowers()) {
                if (outputPower.getRelatedPin().getName().equals(relatedPin.getName())) {
                    if (!usedPin){
                        output += "\n\t\t\tinternal_power() {\n" 
                        + "\t\t\t\trelated_pin : \"" + relatedPin.getName() + "\" ;\n";
                        usedPin = true;
                    }
                    String valuesString = "\t\t\t\t\tvalues(";
                    for (float[] valuesArray : outputPower.getValues()) {
                        valuesString += compileArray(valuesArray) + ",\\\n\t\t\t\t\t       ";
                    }
                    valuesString = valuesString.substring(0, valuesString.length() - 15);
                    output += "\n\t\t\t\t" + outputPower.getPowGroup().name().toLowerCase() + "() {\n"
                    + index1String +index2String + valuesString + ");\n\t\t\t\t}\n";
                }
            }
            if (usedPin) {
                output += "\t\t\t}\n";
            }
        }
        for (InputPin relatedPin : pin.getParent().getInPins()) {
            for (TimingSense timingSense : pin.getAvailableTimSen()) {
                for (TimingType timingType : pin.getAvailableTimType()) {
                    boolean usedCombination = false;
                    for (Timing timing : pin.getTimings()) {
                        if (timing.getRelatedPin().getName().equals(relatedPin.getName())
                            && timing.getTimSense().equals(timingSense) 
                            && timing.getTimType().equals(timingType)) {
                            if (!usedCombination){
                                output += "\n\t\t\ttiming() {\n" 
                                + "\t\t\t\trelated_pin : \"" + relatedPin.getName() + "\" ;\n"
                                + "\t\t\t\ttiming_sense : " + timingSense.name().toLowerCase() + " ;\n"
                                + "\t\t\t\ttiming_type : " + timingType.name().toLowerCase() + " ;\n";
                                usedCombination = true;
                            }
                            String valuesString = "\t\t\t\t\tvalues(";
                            for (float[] valuesArray : timing.getValues()) {
                                valuesString += compileArray(valuesArray) + ",\\\n\t\t\t\t\t       ";
                            }
                            valuesString = valuesString.substring(0, valuesString.length() - 15);
                            valuesString += "); \n";
                            output += "\n\t\t\t\t" + timing.getTimGroup().name().toLowerCase() + "() {\n"
                            + index1String +index2String + valuesString + "\t\t\t\t}\n";
                        }
                    }
                    if (usedCombination) {
                        output += "\t\t\t}\n";
                    }
                }
            }
        }
        output += "\n\t\t}";
        return output;
    }

    private static String compileArray(float[] array) {
        String arrayString = "\"";
        for (float number : array) {
            arrayString += formatFloat(number) + ", ";
        }
        arrayString = arrayString.substring(0,arrayString.length() - 2);
        arrayString += "\"";
        return arrayString;
    }
    
    private static String formatFloat(float number) {
    	if (number < 0.0001) {
            return scFormat.format(number).toLowerCase();
    	} else {
    		return format.format(number);
    	}
    }
    
}