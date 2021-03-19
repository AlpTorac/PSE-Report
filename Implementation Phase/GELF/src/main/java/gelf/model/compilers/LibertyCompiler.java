package gelf.model.compilers;

import java.util.ArrayList;
import gelf.model.elements.*;
import gelf.model.elements.attributes.*;
import gelf.model.project.Model;

/**
 * Compiles the data objects to their Liberty Format equivalents so that they can be
 * saved or displayed in the text editor
 * @author Xhulio Pernoca
 */
public class LibertyCompiler {

    /**
     * Compiles the data object of a library into a string of Liberty File format
     * @param library the data object of the library
     * @return the String in Liberty File Format
     */
    public static String compile(Library library) {
        String output = "library(" + library.getName() + ") {\n"; //+ library.getFileData()[0];
        for (Cell cell : library.getCells()) {
            output += compile(cell);
        }
        output += "\n}";

        return output;
    }

    /**
     * Compiles the data object of a cell into a string in Liberty File format
     * @param cell the data object of the cell
     * @return the String in Liberty File Format
     */
    public static String compile(Cell cell) {
        String output = "\n\tcell(" + cell.getName() + ") {\n"; //+ cell.getCellData();
        output += "\t\tcell_leakage_power : " + Model.formatFloat(cell.getDefaultLeakage()) + ";\n";
        ArrayList<InputPin> inPins = cell.getInPins();
        String[] inPinNames = new String[inPins.size()];
        for (int i = 0; i < inPins.size(); i++) {
            inPinNames[i] = inPins.get(i).getName();
        }
        for (String leakageString: compileLeakage(cell.getLeakages())) {
            output += leakageString;
        }
        for (InputPin inPin : inPins) {
            output += compile(inPin);
        }
        for (OutputPin outPin : cell.getOutPins()) {
            output += compile(outPin);
        }
        output += "\n\t}\n";
        return output;
    }

    /**
     * Compiles the data object of an input pin into a string in Liberty File format
     * @param pin the data object of the input pin
     * @return the String in Liberty File Format
     */
    public static String compile(InputPin pin) {
        String index1String = "\t\t\t\t\tindex_1(" + compileArray(pin.getParent().getIndex1()) + ");\n";
        String output = "\n\t\tpin(" + pin.getName() + ") {\n"
        + "\t\t\tcapacitance : " + Model.formatFloat(pin.getCapacitance()) + " ;\n"
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

    /**
     * Compiles the data object of an output pin into a string in Liberty File format
     * @param pin the data object of the output pin
     * @return the String in Liberty File Format
     */
    public static String compile(OutputPin pin) {
        String index1String = "\t\t\t\t\tindex_1(" + compileArray(pin.getParent().getIndex1()) + ");\n";
        String index2String = "\t\t\t\t\tindex_2(" + compileArray(pin.getParent().getIndex2()) + ");\n";
        String output = "\n\t\tpin(" + pin.getName() + ") {\n"
        + "\t\t\tdirection : output ;\n"
        + "\t\t\tfunction : \"(" + pin.getOutputFunction() + ")\" ;\n"
        + "\t\t\tmax_capacitance : " + Model.formatFloat(pin.getMaxCapacitance()) + " ;\n"
        + "\t\t\tmin_capacitance : " + Model.formatFloat(pin.getMinCapacitance()) + " ;\n";
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

    /**
     * Compiles an array into liberty file format
     * @param array the array to be compiled
     * @return the compiled array String
     */
    private static String compileArray(float[] array) {
        String arrayString = "\"";
        for (float number : array) {
            arrayString += Model.formatFloat(number) + ", ";
        }
        arrayString = arrayString.substring(0,arrayString.length() - 2);
        arrayString += "\"";
        return arrayString;
    }

    /**
     * Compiles leakages
     * @param leakage the leakes to be compiled
     * @return the string array of compiled leakages
     */
    private static String[] compileLeakage(Leakage leakage) {
        leakage.getParentCell().setOutputFunctions();
        String[] leakageFunctions = leakage.getOutputFunctions();
        String[] output = new String[leakage.getValues().length];
        for (int i = 0; i < leakage.getValues().length; i++) {
            output[i] = "\t\tleakage_power() {\n"
            + "\t\t\twhen : \"" 
            + leakageFunctions[i]
            + "\" ;\n"
            + "\t\t\tvalue : \"" + Model.formatFloat(leakage.getValues()[i]) + "\" ;\n" 
            + "\t\t}\n\n";
        }
        return output;
    }
}
