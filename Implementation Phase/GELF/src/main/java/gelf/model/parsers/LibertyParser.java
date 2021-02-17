package gelf.model.parsers;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.model.elements.attributes.Attribute;
import gelf.model.elements.attributes.InAttribute;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.Leakage;
import gelf.model.elements.attributes.OutAttribute;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.project.Interpolator;

/**
 * Parses the Liberty Files to their corresponding data objects so that
 * they can be easily manipulated and displayed in graphs
 * @author Xhulio Pernoca
 */
public class LibertyParser {
    // The format of an Elements name
    private static final String NAMEFORMAT = "([a-zA-Z]|_|-|[0-9])*";

    // The format of an attribute's or parameter's name, not implemented because of runtime
    /* private static final String ATTRNAMEFORMAT = "([a-zA-Z]*[_]?[a-zA-Z]*)+";*/
    
    // The format of a function 
    private static final String FUNCTIONFORMAT = "([a-zA-Z]|_|-|\\^|\\||\\(|\\)|&|\\!|[0-9])+";
    
    // The format of a float value
    private static final String FLOATFORMAT = "[-+]?[0-9]*(\\.)?[0-9]+([eE][-+]?[0-9]+)?";

    // The format of a parameter, not implemented because of runtime
    /* private static final String PARAMFORMAT = ATTRNAMEFORMAT + "\\:(\"\\(" + FUNCTIONFORMAT 
    		+ "\\)\"|\"" + NAMEFORMAT + "\"|" + FLOATFORMAT + "|" + ATTRNAMEFORMAT + "|\"" 
			+ ATTRNAMEFORMAT + "\");"; */

    // The format of an array, not implemented because of runtime
    /* private static final String ARRAYFORMAT = "\"((" + FLOATFORMAT +",)(\\\\)?)*" + FLOATFORMAT + "\"";*/

    // The format of an array with a depth of 2, not implemented because of runtime
    /* private static final String DOUBLEARRAYFORMAT = "(" + ARRAYFORMAT + ",(\\\\)?)*" + ARRAYFORMAT;*/

    // The format of an input Pin Attribute, not implemented because of runtime
    /* private static final String INATTRIBUTESFORMAT = ATTRNAMEFORMAT + "\\(" + NAMEFORMAT + "\\)\\{"
    		+ "index_1\\(" + ARRAYFORMAT + "\\);values\\(" + ARRAYFORMAT + "\\);\\}"; */

    // The format of an output Pin Attribute, not implemented because of runtime
    /* private static final String OUTATTRIBUTESFORMAT = ATTRNAMEFORMAT + "\\(" + NAMEFORMAT + "\\)\\{"
    		+ "index_1\\(" + ARRAYFORMAT + "\\);index_2\\(" + ARRAYFORMAT + "\\);values\\("
    				+ DOUBLEARRAYFORMAT + "\\);\\}"; */

    // The format of a Leakage Attribute, not implemented because of runtime
    /* private static final String LEAKAGEFORMAT = "leakage_power\\(\\)\\{when:\"" + FUNCTIONFORMAT
			+ "\";value:\"" + FLOATFORMAT + "\";\\}"; */

    // The format of a regex that encompasses all Power groups
    private static String powerGroupSeparator = "";

    // The format of a regex that encompasses all Timing groups
    private static String timingGroupSeparator = "";

    // The format of a pin, not implemented because of runtime
    /* private static final String PINFORMAT = "pin\\(" + NAMEFORMAT + "\\)\\{(" + PARAMFORMAT
    		+ ")+((internal_power|timing)\\(" + NAMEFORMAT + "\\)\\{(" + PARAMFORMAT
    		+ ")*((" + INATTRIBUTESFORMAT + ")+|(" + OUTATTRIBUTESFORMAT + ")+)\\})+\\}"; */

    // The format of a cell, not implemented because of runtime
    /* private static final String CELLFORMAT = "cell\\(" + NAMEFORMAT + "\\)\\{(" + PARAMFORMAT
    		+ ")+(" + LEAKAGEFORMAT + ")+(" + PINFORMAT + ")+\\}"; */

    // The format of a Library, not implemented because of runtime
    /* private static final String LIBRARYFORMAT = "library\\(" + NAMEFORMAT + "\\)\\{"
    		+ ".*(" + CELLFORMAT + ")+\\}"; */

    /**
     * Parses a String data assuming it is a Library
     * @param libraryString the String data of a Liberty file
     * @return the parsed Library object
     * @throws InvalidFileFormatException if the format of the File is not recognised
     */
    public static Library parseLibrary(String libraryString) throws InvalidFileFormatException {
        // checks if File data is empty
    	if (libraryString == null || libraryString.length() == 0) {
    		throw new InvalidFileFormatException("File is empty or file content"
    				+ " can't be read");
    	}
        // checks brackets
    	checkBrackets(libraryString);
        // removes all whitespaces
        libraryString = libraryString.replaceAll("\\s+", "");
        String noCommentsString = "";
        // removes comments
        String[] commentsSplit = libraryString.split("(\\/\\*)|(\\*\\/)");
        for (int i = 0; i < commentsSplit.length; i += 2) {
            noCommentsString += commentsSplit[i];
        }
        // checks format validity, removed because of current exceeding runtime 
        /*if (!noCommentsString.matches(LIBRARYFORMAT)) {
            throw new InvalidFileFormatException("Library doesn't fit format");
        }*/
        // Splits text by cell
        String[] cellStrings = noCommentsString.split("(?=(cell\\())");
        // Finds library name
        String[] libData = cellStrings[0].split("\\{");
        String name = libData[0].substring(libData[0].indexOf("(") + 1, libData[0].indexOf(")"));
        // parses Cells
        ArrayList<Cell> childCells = new ArrayList<Cell>();
        for (int i = 1; i < cellStrings.length; i++) {
            childCells.add(parseCell(cellStrings[i], name));
        }
        if (childCells.isEmpty()) {
            throw new InvalidFileFormatException("Library doesn't have any cells in it");
        }
        // unifies child Cells indexes
        float[] index1 = childCells.get(0).getIndex1();
        float[] index2 = childCells.get(0).getIndex2();
        for (int i = 1; i < childCells.size(); i++) {
            Cell currentCell = childCells.get(i);
            if (!Arrays.equals(currentCell.getIndex1(),index1) 
            		|| !Arrays.equals(currentCell.getIndex2(), index2)) {
                currentCell.interpolate(index1, index2);
            }
        }
        Library productLibrary = new Library(name, index1, index2, null, childCells);
        for (Cell cell : childCells) {
        	cell.setParentLibrary(productLibrary);
        }
        return productLibrary;
    }

    /**
     * Parses a String data assuming it is a Cell
     * @param cellString the String data of a Cell file
     * @param path the path to the Cell
     * @return the parsed Cell object
     * @throws InvalidFileFormatException if the format of the Cell is not recognised
     */
    public static Cell parseCell(String cellString, String path) throws InvalidFileFormatException {
    	cellString = cellString.replaceAll("\\s+", "");
        // splits by pins
        String[] pinStrings = cellString.split("(?=(pin\\())");
        // finds leakage attributes
        String[] cellLeakages = pinStrings[0].split("(?=(leakage_power\\())");
        float[] leakagesValues = new float[cellLeakages.length - 1];
        // parses leakage values
        for (int i = 1; i < cellLeakages.length; i++) {
        	String leakageInfo = cellLeakages[i];
        	int valueIndex = leakageInfo.indexOf("value");
        	int firstQuotation = leakageInfo.indexOf("\"", valueIndex);
        	int lastQuotation = leakageInfo.indexOf("\"", firstQuotation + 1);
        	String leakageString = leakageInfo.substring(firstQuotation + 1, lastQuotation);
        	leakagesValues[i - 1] = Float.parseFloat(leakageString);
        }
        String dataAfterLeakageInfo = cellLeakages[cellLeakages.length - 1].split("\\{", 2)[1];
        cellLeakages[0] += dataAfterLeakageInfo;
        // fetches cell name
        String[] cellData = cellLeakages[0].split("\\{");
        String name = cellData[0].substring(cellData[0].indexOf("(") + 1, cellData[0].indexOf(")"));
        path += "/" + name;
        //splits the cell parameters
        String[] cellParameters = cellData[1].split(";");
        float defaultLeakage = 0f;
        boolean hasDefaultLeakage = false;
        ArrayList<String> unsupportedData = new ArrayList<String>();
        // parses all known cell parameters
        for (int i = 0; i < cellParameters.length; i++) {
        	String[] paramParts = cellParameters[i].split(":");
        	switch (paramParts[0]) {
        	case "cell_leakage_power":
        		hasDefaultLeakage = true;
        		defaultLeakage = Float.parseFloat(paramParts[1]);
        		break;
        	default:
        		unsupportedData.add(cellParameters[i]);
        	}
        }
        if (!hasDefaultLeakage) {
        	throw new InvalidFileFormatException("No default leakages specified in cell "
        			+ "\"" + path + "\"");
        }
        Leakage leakages = new Leakage(leakagesValues);
        ArrayList<InputPin> childInPins = new ArrayList<InputPin>();
        ArrayList<OutputPin> childOutPins = new ArrayList<OutputPin>();
        for (int i = 1; i < pinStrings.length; i++) {
            Pin childPin = parsePin(pinStrings[i], childInPins, path);
            if (childPin instanceof InputPin) {
                childInPins.add((InputPin) childPin);
            } else {
                childOutPins.add((OutputPin) childPin);
            }
        }
        //checks leakages attribute validity
        if (java.lang.Math.pow(2 , childInPins.size()) != leakages.getValues().length) {
        	throw new InvalidFileFormatException("Invalid number of leakage entries "
        			+ "in cell \"" + path + "\"");
        }
        ArrayList<Pin> childPins = new ArrayList<Pin>();
        childPins.addAll(childInPins);
        childPins.addAll(childOutPins);
        if (childPins.isEmpty()) {
            throw new InvalidFileFormatException("Cell \"" + path + "\" has no child pins");
        }
        float[][] indexes = findCellIndex(childOutPins);
        if (indexes == null) {
        	throw new InvalidFileFormatException("Cell \"" + path + "\" has no valid indexes");
        }
        float[] index1 = indexes[0];
        float[] index2 = indexes[1];
        Cell productCell = new Cell(name, index1, index2, null, childInPins, 
        		childOutPins, leakages, defaultLeakage);
        for (Pin pin : childPins) {
            pin.setParent(productCell);
        }
        return productCell;
    }

    /**
     * Parses a String data assuming it is a Pin. It checks midway if it is an input Pin or
     * an output Pin so that it can decide which format to expect. It is done this way since when
     * parsing new Files, we don't initially know which pin to expect
     * @param pinString the String data of a Pin
     * @param relatedPins the pins related to it. Neccessary if it is an output Pin
     * @param path the path to the Pin
     * @return the parsed Pin. Eithe InputPin or OutputPin
     * @throws InvalidFileFormatException if the format of the Cell is not recognised
     */
    public static Pin parsePin(String pinString,
                         ArrayList<InputPin> relatedPins, String path) throws InvalidFileFormatException {
        // might move to constructor
        // recalculates regex to separate power groups
        if (powerGroupSeparator.equals("")) {
            for (PowerGroup powGroup : PowerGroup.values()) {
                powerGroupSeparator += "|" + powGroup.name().toLowerCase() + "\\(";
            }
            powerGroupSeparator = powerGroupSeparator.substring(1);
        }
        // recalculates regex to separate timing groups
        if (timingGroupSeparator.equals("")) {
            for (TimingGroup timGroup : TimingGroup.values()) {
                timingGroupSeparator += "|" + timGroup.name().toLowerCase() + "\\(";
            }
            timingGroupSeparator = timingGroupSeparator.substring(1);
        }
        pinString.replaceAll("\\s+", "");
        //checks pin format, removed because of exceeding runtime
        /*if (!pinString.matches(PINFORMAT)) {
        	throw new InvalidFileFormatException("Pin doesn't match format");
        }*/
        // searches for attribute groups
		String[] attributeStrings = pinString.split("(?=internal_power\\(|timing\\()");
        // parses pin name
        String[] pinData = attributeStrings[0].split("\\{|\\}");
        String name = pinData[0].substring(pinData[0].indexOf("(") + 1, pinData[0].indexOf(")"));
        path += "/" + name;
        boolean isInput = false;
        boolean isOutput = false;
        String function = "";
        boolean hasCapacitance = false;
        boolean hasMinCapacitance = false;
        float capacitance = 0;
        float maxCap = 0;
        float minCap = 0;
        ArrayList<String> leftOverData = new ArrayList<String>();
        // searches attribute group parameters
        String[] singleValueAttributes = pinData[1].split(";");
        // parses all known pin parameters
        for (int i = 0; i < singleValueAttributes.length; i++) {
            String attributeData = singleValueAttributes[i]; 
            String[] attributeParts = attributeData.split(":");
            String attrName = attributeParts[0];
            String attrValue = attributeParts[1];
            switch (attrName) {
                case "direction":
                    if (attrValue.equals("input")) {
                        isInput = true;
                    } else if (attrValue.equals("output")) {
                        isOutput = true;
                    } else {
                        throw new InvalidFileFormatException("Direction \"" + attrValue + "\" in pin"
                        + " \"" + path + "not supported");
                    }
                    break;
                case "function":
                    if (!attrValue.matches("\"\\(" + FUNCTIONFORMAT + "\\)\"")) {
                        throw new InvalidFileFormatException("Function format: \"" + attrValue + 
                        		"\" for Pin \"" + path + " \" is invalid");
                    }
                    int firstIndex = attrValue.indexOf("\"");
                    int secondIndex = attrValue.indexOf("\"", firstIndex + 1);
                    function = attrValue.substring(firstIndex + 2, secondIndex - 1);
                    break;
                case "max_capacitance":
                    if (!attrValue.matches(FLOATFORMAT)) {
                        throw new InvalidFileFormatException("Value format for " + attrValue 
                                + " in Pin \"" + path + "\" is invalid");
                    }
                    if (hasCapacitance) {
                        throw new InvalidFileFormatException(attrName 
                                + " in Pin \"" + path + "\" is defined twice");
                    }
                    hasCapacitance = true;
                    maxCap =  Float.parseFloat(attrValue);
                    break;
                case "min_capacitance":
                    if (!attrValue.matches(FLOATFORMAT)) {
                        throw new InvalidFileFormatException("Value format for" + attrName 
                                + "in Pin \"" + path + "\" is invalid");
                    }
                    if (hasMinCapacitance) {
                        throw new InvalidFileFormatException(attrName 
                                + "in Pin \"" + path + "\" is defined twice");
                    }
                    hasMinCapacitance = true;
                    minCap =  Float.parseFloat(attrValue);
                    break;
                case "capacitance":
                    if (!attrValue.matches(FLOATFORMAT)) {
                        throw new InvalidFileFormatException("Value format for" + attrName 
                                + "in Pin \"" + path + "\" is invalid");
                    }
                    if (hasCapacitance) {
                        throw new InvalidFileFormatException(attrName 
                                + "in Pin \"" + path + "\" is defined twice");
                    }
                    hasCapacitance = true;
                    capacitance =  Float.parseFloat(attrValue);
                    break;
                default:
                    leftOverData.add(attributeData);
            }
        }
        // throws exceptions for invalid pin direction
        if (isInput & isOutput) {
            throw new InvalidFileFormatException("Pin \"" + path + "\" is defined as both input and output");
        }
        if (!isInput & !isOutput) {
            throw new InvalidFileFormatException("Pin \"" + path + "\" is defined as neither input nor output");
        }
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        // parses all known attribute
        for (int i = 1; i < attributeStrings.length; i++) {
            String attributeData = attributeStrings[i];
            InputPin relatedPin = null;
            // parses internal power
            if (attributeData.startsWith("internal_power")) {
                String[] powerAttributes =  attributeData.split("(?=" + powerGroupSeparator + ")");
                // parses internal power for an output pin
                if (isOutput) {
                	String relatedPinString = null;
                    String[] powerAttributeData = powerAttributes[0].split("(\\{)");
                    String[] powerAttributeParameters = powerAttributeData[1].split(";");
                    for (String attributeParam : powerAttributeParameters) {
                        String[] powerAttributeParamParts = attributeParam.split(":");
                        switch (powerAttributeParamParts[0]) {
                            case "related_pin":
                            	relatedPinString = powerAttributeParamParts[1].substring(1, powerAttributeParamParts[1].length() - 1);
                                if (!powerAttributeParamParts[1].matches("\"" + NAMEFORMAT + "\"")) {
                                    throw new InvalidFileFormatException("Name format of related_pin \"" 
                                    + powerAttributeParamParts[1] + "\" of Pin \"" + path + " is invalid");
                                }
                                for (InputPin inpin: relatedPins) {
                                    if (inpin.getName().equals(relatedPinString)){
                                        relatedPin = inpin;
                                    }
                                }
                                break;
                            default:
                                /** No other parameters supported */
                        }
                    }
                    // gets related Pins
                    for (int j = 1; j < powerAttributes.length; j++) {
                        OutputPower power = parseOutPower(powerAttributes[j], path);
                        if (relatedPin == null && relatedPinString != null) {
                        	throw new InvalidFileFormatException("Related Pin \"" + relatedPinString + "\""
                        			+ " on Pin \"" + path + "\" was not found before it");
                        } else if (relatedPin == null) {
                            throw new InvalidFileFormatException("Nonexistant related Pin on Pin \""
                                + path + "\"");
                        }
                        power.setRelatedPin(relatedPin);
                        attributes.add(power);
                    }
                }
                // parses internal power for input pins
                else {
                    for (int j = 1; j < powerAttributes.length; j++) {
                        attributes.add(parseInPower(powerAttributes[j], path));
                    }
                }
            // parses timing
            } else if (attributeData.startsWith("timing")) {
                TimingSense timSense = null;
                TimingType timType = null;
                String[] timingAttributes =  attributeData.split("(?=" + timingGroupSeparator + ")");
                /** Parses timing for the output pin. Therefore ensures to fetch the relatedPin first */
                if (isOutput) {
                	String relatedPinString = null;
                    String[] timingAttributeData = timingAttributes[0].split("(\\{)");
                    String[] timingAttributeParameters = timingAttributeData[1].split(";");
                    // parses all known timing parameters
                    for (String attributeParam : timingAttributeParameters) {
                        String[] timingAttributeParamParts = attributeParam.split(":");
                        String value = timingAttributeParamParts[1];
                        switch (timingAttributeParamParts[0]) {
                            case "related_pin":
                            	relatedPinString = value.substring(1, 
                            			value.length() - 1);
                                if (!value.matches("\"" + NAMEFORMAT + "\"")) {
                                    throw new InvalidFileFormatException("Name format of related_pin \"" 
                                    + value + "\" of Pin \"" + path + " is invalid");
                                }
                                for (InputPin pin: relatedPins) {
                                    if (pin.getName().equals(relatedPinString)){
                                        relatedPin = pin;
                                    }
                                }
                                break;
                            case "timing_sense":
                                for (TimingSense timSenseEnum : TimingSense.values()) {
                                    if (value.matches(timSenseEnum.name().toLowerCase())) {
                                        timSense = timSenseEnum;
                                    }
                                }
                                break;
                            case "timing_type":
                                for (TimingType timTypeEnum : TimingType.values()) {
                                    if (value.matches(timTypeEnum.name().toLowerCase())) {
                                        timType = timTypeEnum;
                                    }   
                                } 
                                break;
                            default:
                                /** No other parameters supported */
                        }
                    }
                    // filters only timings of known timing groups, senses and types
                    if (timSense != null && timType != null) {
                        for (int j = 1; j < timingAttributes.length; j++) {
                        	if (relatedPin == null && relatedPinString != null) {
                            	throw new InvalidFileFormatException("Related Pin \"" + relatedPinString + "\""
                            			+ " on Pin \"" + path + "\" was not found before it");
                            } else if (relatedPin == null) {
                                throw new InvalidFileFormatException("Nonexistant related Pin on Pin \""
                                    + path + "\"");
                            }
                            Timing timing = parseOutTiming(timingAttributes[j], timSense, timType, path);
                            timing.setRelatedPin(relatedPin);
                            attributes.add(timing);
                        }
                    }
                } else {
                    /* Timing for input pins not supported and therefore ignored, although it can 
                    be found in some Liberty files */
                }
            } else {
                /* no other known multiple value attributes. Can be implemented later */
            }
        }
        // unifies attribute indexes
        if (attributes.size() != 0) {
            if (isInput) {
                float[] uniformIndex1 = null;
                for (Attribute attribute : attributes) {
                    if (attribute instanceof InAttribute) {
                        InAttribute inAttribute = (InAttribute) attribute;
                        float[] index1 = inAttribute.getIndex1();
                        if (uniformIndex1 == null) {
                            uniformIndex1 = index1;
                        }
                        if (!Arrays.equals(index1, uniformIndex1)) {
                            Interpolator interpolator = new Interpolator();
                            float[] newValues = interpolator.interpolate(index1, inAttribute.getValues(), uniformIndex1);
                            inAttribute.setIndex1(uniformIndex1);
                            inAttribute.setValues(newValues);
                        }
                    }
                }
            } else {
                float[] uniformIndex1 = null;
                float[] uniformIndex2 = null;
                for (Attribute attribute : attributes) {
                    if (attribute instanceof OutAttribute) {
                        OutAttribute outAttribute = (OutAttribute) attribute;
                        float[] index1 = outAttribute.getIndex1();
                        float[] index2 = outAttribute.getIndex2();
                        if (uniformIndex1 == null | uniformIndex2 == null) {
                            uniformIndex1 = index1;
                            uniformIndex2 = index2;
                        }
                        if (!Arrays.equals(uniformIndex1, index1) || !Arrays.equals(uniformIndex2, index2)) {
                            Interpolator interpolator = new Interpolator();
                            float[][] newValues = interpolator.bicubicInterpolate(index1, index2, outAttribute.getValues(), uniformIndex1, uniformIndex2);
                            outAttribute.setIndex1(uniformIndex1);
                            outAttribute.setIndex2(uniformIndex2);
                            outAttribute.setValues(newValues);
                        }
                    }
                }
            }
        }
        // instantiates the input pin if it is input
        if (isInput) {
            ArrayList<InputPower> powers = new ArrayList<InputPower>();
            for (Attribute attribute : attributes) {
                if (attribute instanceof InputPower) {
                    powers.add((InputPower) attribute);
                }
            }
            InputPin parsedPin = new InputPin(name, null, powers);
            if (hasCapacitance) {
                parsedPin.setCapacitance(capacitance);
            }
            for (Attribute attribute : attributes) {
                if (attribute instanceof InAttribute) {
                    ((InAttribute) attribute).setParentInPin(parsedPin);
                }
            }
            return parsedPin;
        } 
        // instantiates the output pin if it is output
        else {
            ArrayList<OutputPower> powers = new ArrayList<OutputPower>();
            ArrayList<Timing> timings = new ArrayList<Timing>();
            for (Attribute attribute : attributes) {
                if (attribute instanceof OutputPower) {
                    powers.add((OutputPower) attribute);
                } else if (attribute instanceof Timing) {
                    timings.add((Timing) attribute);
                }
            }
            OutputPin parsedPin = new OutputPin(name, null, powers, timings);
            for (Attribute attribute : attributes) {
                if (attribute instanceof OutAttribute) {
                    ((OutAttribute) attribute).setParentOutPin(parsedPin);
                }
            }
            if (hasMinCapacitance && hasCapacitance) {
                parsedPin.setMaxCapacitance(maxCap);
                parsedPin.setMinCapacitance(minCap);
            }
            if (!function.equals("")) {
                parsedPin.setOutputFunction(function);
            }
            return parsedPin;
        }
    }


    /**
     * Parses an array that contains Internal Power information on an output Pin to give the
     * OutputPower object
     * @param content the string of the Internal Power attribute
     * @param path the path to the Internal Power
     * @return the parsed Internal Power Attribute
     * @throws InvalidFileFormatException if the Format of the Internal Power attribute doesn't fit
     * expected format
     */
    public static OutputPower parseOutPower(String content, String path) throws InvalidFileFormatException {
        PowerGroup powGroup = null;
        for (PowerGroup powGroupEnum : PowerGroup.values()) {
            if (content.startsWith(powGroupEnum.name().toLowerCase())) {
                powGroup = powGroupEnum;
            }   
        }
        float[][] values = parseDoubleArray(content, "values", path);
        float[] index1 = parseArray(content, "index_1");
        float[] index2 = parseArray(content, "index_2");
        OutputPower attribute = new OutputPower(powGroup, values);
        attribute.setIndex1(index1);
        attribute.setIndex2(index2);
        if (values.length == 0) {
            throw new InvalidFileFormatException("Values are empty in internal_power of Pin \"" + path + "\"");
        }
        if (values.length != index1.length || values[0].length != index2.length) {
            throw new InvalidFileFormatException("Values length mismatch in internal_power of Pin \"" + path + "\"");
        }
        return attribute;
    }

    /**
     * Parses an array that contains Internal Power information on an input Pin to give the
     * InputPower object
     * @param content the string of the Internal Power attribute
     * @param path the path to the Internal Power
     * @return the parsed Internal Power Attribute
     * @throws InvalidFileFormatException if the Format of the Internal Power attribute doesn't fit
     * expected format
     */
    public static InputPower parseInPower(String content, String path) throws InvalidFileFormatException {
        PowerGroup powGroup = null;
        for (PowerGroup powGroupEnum : PowerGroup.values()) {
            if (content.startsWith(powGroupEnum.name().toLowerCase())) {
                powGroup = powGroupEnum;
            }   
        }
        float[] values = parseArray(content, "values");
        float[] index1 = parseArray(content, "index_1");
        InputPower attribute = new InputPower(powGroup, values);
        attribute.setIndex1(index1);
        if (values.length == 0) {
            throw new InvalidFileFormatException("Values are empty in internal_power of Pin \"" + path + "\"");
        }
        if (values.length != index1.length) {
            throw new InvalidFileFormatException("Values mismatch in internal_power of Pin \"" + path + "\"");
        }
        return attribute;
    }

    /**
     * Parses an array that contains Timing information on an output Pin to give the
     * Timing object
     * @param content the string of the Timing attribute
     * @param timSense the timing Sense
     * @param timType the timing Type
     * @param path the path to this timing
     * @return the parsed Timing Attribute
     * @throws InvalidFileFormatException if the Format of the Timing attribute doesn't fit
     * expected format
     */
    public static Timing parseOutTiming(String content, TimingSense timSense, TimingType timType, String path)
            throws InvalidFileFormatException {
        TimingGroup timGroup = null;
        for (TimingGroup timingEnum : TimingGroup.values()) {
            if (content.startsWith(timingEnum.name().toLowerCase())) {
                timGroup = timingEnum;
            }
        }
        float[][] values = parseDoubleArray(content, "values", path);
        float[] index1 = parseArray(content, "index_1");
        float[] index2 = parseArray(content, "index_2");
        Timing attribute = new Timing(timSense, timType, timGroup, values);
        attribute.setIndex1(index1);
        attribute.setIndex2(index2);
        if (values.length == 0) {
            throw new InvalidFileFormatException("Values are empty in timing of Pin \"" + path + "\"");
        }
        if (values.length != index1.length || values[0].length != index2.length) {
            throw new InvalidFileFormatException("Values mismatch in timing of Pin \"" + path + "\"");
        }
        return attribute;
    }

    /**
     * Parses the string of an attribute, but looking for an array of the
     * parameter arrayName, so that indexes can be parsed for attributes
     * @param content the String of an attribute that contains the array
     * @param arrayName the parameter name
     * @return the array object of the parameter name
     */
    public static float[] parseArray(String content, String arrayName){
        int firstIndex = content.indexOf(arrayName) + arrayName.length();
        int lastIndex = content.indexOf(";", firstIndex);
        String arrayString = content.substring(firstIndex + 1, lastIndex - 1);
        return stringToArray(arrayString);
    }

    /**
     * Parses the string of an attribute but looking for a double array of the
     * parameter arrayName, so that values can be parsed for attributes
     * 
     * @param content   the String of an attribute that contains the double array
     * @param arrayName the parameter name
     * @param path the path to the double array
     * @return the array object of the parameter name
     * @throws InvalidFileFormatException
     */
    public static float[][] parseDoubleArray(String content, String arrayName, String path)
            throws InvalidFileFormatException {
        int firstIndex = content.indexOf(arrayName) + arrayName.length();
        int lastIndex = content.indexOf(";", firstIndex);
        String arrayStrings = content.substring(firstIndex + 1, lastIndex - 1);
        String[] separatedArrayStrings = arrayStrings.split("(\",\\\\|\",)");
        float[][] result = new float[separatedArrayStrings.length][]; 
        for (int i = 0; i < separatedArrayStrings.length; i++) {
            result[i] = stringToArray(separatedArrayStrings[i] + "\"");
        }
        if (result.length == 0) {
            throw new InvalidFileFormatException("Values are empty in timing of Pin \"" + path + "\"");
        }
        int uniformLength = result[0].length;
        for (float[] array : result) {
            if (array.length != uniformLength) {
                throw new InvalidFileFormatException("Values have differen array lengths " 
                    + "in timing of Pin \"" + path + "\"");
            }
        }
        return result;
    }

    /**
     * Parses a String of an array to an float[] array object so that the attributes
     * can read the values
     * @param content the string containing the array
     * @return the float[] array object
     */
    public static float[] stringToArray(String content) {
        content = content.replaceAll("(\"|\\\\)", "");
        String[] stringArray = content.split(",");
        float[] floatArray = new float[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            floatArray[i] = Float.parseFloat(stringArray[i]);
        }
        return floatArray;
    }
    
    /**
     * Checks the indexes on an output Pin of a Cell so that the Cell (and also its parent Library) 
     * can get the indexes
     * @param outPins the output Pins of the cell
     * @return An array containing index1 array in it's first index and index2 in it's second
     */
    private static float[][] findCellIndex(ArrayList<OutputPin> outPins) {
    	float[][] indexes = null;
        for (OutputPin outPin : outPins) {
        	ArrayList<OutAttribute> attributes = new ArrayList<OutAttribute>();
        	attributes.addAll(outPin.getOutputPowers());
        	attributes.addAll(outPin.getTimings());
        	for (OutAttribute attribute : attributes) {
        		float[] index1 = attribute.getIndex1();
        		float[] index2 = attribute.getIndex2();
        		indexes = new float[2][];
        		indexes[0] = index1;
        		indexes[1] = index2;
        	}
        }
        return indexes;
    }
    
    /**
     * Checks whether the brackets are respected in the file, so that it can reject
     * files with corrupted format
     * @param content The String to be checked
     * @throws InvalidFileFormatException if the String doesn't respect brackets
     */
    private static void checkBrackets(String content) throws InvalidFileFormatException {
    	Stack<Integer> roundStack = new Stack<Integer>();
    	Stack<Integer> curlyStack = new Stack<Integer>();
    	
    	CharacterIterator it = new StringCharacterIterator(content);
    	 
        while (it.current() != CharacterIterator.DONE) {
            switch (it.current()) {
            	case '(':
                	roundStack.add(it.getIndex());
                	break;
            	case ')':
            		if (roundStack.isEmpty()) {
            			throw new InvalidFileFormatException("Closed bracket in index "
            					+ it.getIndex() + " although no bracket was opened");
            		}
            		if (curlyStack.isEmpty()) {
            			roundStack.pop();
            		} else if (curlyStack.lastElement() > roundStack.pop()) {
            			throw new InvalidFileFormatException("Closed bracket in index "
            					+ it.getIndex() + " although was supposed to close curly "
            							+ "brackets first");
            		}
            		break;
            	case '{':
            		curlyStack.add(it.getIndex());
            		break;
            	case '}':
            		if (curlyStack.isEmpty()) {
            			throw new InvalidFileFormatException("Closed bracket in index "
            					+ it.getIndex() + " although no bracket was opened");
            		}
            		if (roundStack.isEmpty()) {
            			curlyStack.pop();
            		} else if (curlyStack.pop() < roundStack.lastElement()) {
            			throw new InvalidFileFormatException("Closed bracket in index "
            					+ it.getIndex() + " although was supposed to close round "
            							+ "brackets first");
            		}
            		break;
            	}
            	it.next();
        	}
        if (!curlyStack.isEmpty()) {
        	throw new InvalidFileFormatException("Unclosed curly bracket from index "
        			+ it.getIndex());
        } else if (!roundStack.isEmpty()) {
        	throw new InvalidFileFormatException("Unclosed round bracket from index "
        			+ it.getIndex());
        }
    }
}