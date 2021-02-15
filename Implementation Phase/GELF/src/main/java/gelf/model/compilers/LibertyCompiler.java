package gelf.model.compilers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import gelf.model.elements.*;
import gelf.model.elements.attributes.*;
/**
 * Compiles the data objects to their Liberty Format equivalents
 */
public class LibertyCompiler {
    private static DecimalFormat format = new DecimalFormat("##.#######");

    public static String compile(Library library) {
        String output = "library(" + library.getName() + ") {\n" + library.getLibraryContent();
        for (Cell cell : library.getCells()) {
            output += compile(cell);
        }
        output += "\n}";
        return output;
    }

    /**
     * 2^input_pins will cause problem if left unattended
     * @param cell
     * @return
     */
    public static String compile(Cell cell) {
        String output = "\tcell(" + cell.getName() + ") {\n"; //+ cell.getCellData();
        output += "\t\tcell_leakage_power : " + cell.getDefaultLeakage() + ";";
        ArrayList<InputPin> inPins = cell.getInPins();
        String[] inPinNames = new String[inPins.size()];
        for (int i = 0; i < inPins.size(); i++) {
            inPinNames[i] = inPins.get(i).getName();
        }
        for (int i = 0; i < java.lang.Math.pow(2 , inPinNames.length); i++) {
            String leakageFunction = "";
            for (int j = 0; j < inPinNames.length; j++) {
                if ((i & j) == 0) {
                    leakageFunction += "!";
                }
                leakageFunction += inPinNames[j] + "&";
            }
            leakageFunction.substring(0, leakageFunction.length() - 1);
            output += "\t\tleakage_power() {\n"
            + "\t\t\twhen : \"" 
            + leakageFunction
            + "\" ;\n"
            + "\t\t\tvalue : \"" + cell.getLeakages().getValues()[i] + "\" ;\n" 
            + "\t\t}\n\n";
        }
        for (InputPin inPin : inPins) {
            output += compile(inPin);
        }
        for (OutputPin outPin : cell.getOutPins()) {
            output += compile(outPin);
        }
        return output;
    }

    public static String compile(InputPin pin) {
        String index1String = "\t\t\t\t\tindex_1(" + compileArray( pin.getParent().getIndex1()) + ");\n";
        String output = "\t\tpin(" + pin.getName() + ") {\n"
        + "\t\t\tcapacitance : " + pin.getCapacitance() + " ;\n"
        + "\t\t\tdirection : input ;\n";
        if (!pin.getInputPowers().isEmpty()){
            output += "\t\t\tinternal_power() {\n";
            for (InputPower inputPower : pin.getInputPowers()) {
                String valuesString = "\t\t\t\t\tvalues(" + compileArray(inputPower.getValues()) + ");\n";
                output += "\t\t\t\t" + inputPower.getPowGroup().name() + "() {\n"
                + index1String + valuesString + "\t\t\t\t}\n";
            }
            output += "\t\t\t}\n";
        }
        return output;
    }

    public static String compile(OutputPin pin) {
        String index1String = "\t\t\t\t\tindex_1(" + compileArray(pin.getParent().getIndex1()) + ");\n";
        String index2String = "\t\t\t\t\tindex_2(" + compileArray(pin.getParent().getIndex2()) + ");\n";
        String output = "\t\tpin(" + pin.getName() + ") {\n"
        + "\t\t\tdirection : output ;\n"
        + "\t\t\tfunction : \"(" + pin.getOutputFunction() + ")\" ;\n"
        + "\t\t\tmax_capacitance : " + pin.getMaxCapacitance() + " ;\n"
        + "\t\t\tmin_capacitance : " + pin.getMinCapacitance() + " ;\n";
        for (InputPin relatedPin : pin.getParent().getInPins()) {
            boolean usedPin = false;
            for (OutputPower outputPower : pin.getOutputPowers()) {
                if (outputPower.getRelatedPin().getName().equals(relatedPin.getName())) {
                    if (!usedPin){
                        output += "\t\t\tinternal_power() {\n" 
                        + "\t\t\t\trelated_pin : \"" + relatedPin.getName() + "\" ;\n";
                        usedPin = true;
                    }
                    String valuesString = "\t\t\t\t\tvalues(";
                    for (float[] valuesArray : outputPower.getValues()) {
                        valuesString += compileArray(valuesArray) + ",\\\n\t\t\t\t\t       ";
                        valuesString.substring(0, valuesString.length() - 15);
                        valuesString += "); \n";
                    }
                    output += "\t\t\t\t" + outputPower.getPowGroup().name() + "() {\n"
                    + index1String +index2String + valuesString + "\t\t\t\t}\n";
                }
                if (usedPin) {
                    output += "\t\t\t}\n";
                }
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
                                output += "\t\t\ttiming() {\n" 
                                + "\t\t\t\trelated_pin : \"" + relatedPin.getName() + "\" ;\n"
                                + "\t\t\t\ttiming_sense : \"" + timingType.name() + "\" ;\n"
                                + "\t\t\t\ttiming_type : \"" + timingType.name() + "\" ;\n";
                                usedCombination = true;
                            }
                            String valuesString = "\t\t\t\t\tvalues(";
                            for (float[] valuesArray : timing.getValues()) {
                                valuesString += compileArray(valuesArray) + ",\\\n\t\t\t\t\t       ";
                                valuesString.substring(0, valuesString.length() - 15);
                                valuesString += "); \n";
                            }
                            output += "\t\t\t\t" + timing.getTimGroup().name() + "() {\n"
                            + index1String +index2String + valuesString + "\t\t\t\t}\n";
                        }
                        if (usedCombination) {
                            output += "\t\t\t}\n";
                        }
                    }
                }
            }
        }
        return output;
    }

    private static String compileArray(float[] array) {
        String arrayString = "\"";
        for (float number : array) {
            arrayString += format.format(number) + ", ";
        }
        arrayString.substring(0,arrayString.length()-2);
        arrayString += "\"";
        return arrayString;
    }
    
}