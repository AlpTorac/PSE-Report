package gelf.model.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import gelf.model.elements.*;
import gelf.model.elements.attributes.Attribute;
import gelf.model.elements.attributes.InAttribute;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutAttribute;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

/**
 * Parses the Liberty Files to their corresponding data objects
 */
public class LibertyParser {
    private static final String NAMEFORMAT = "([A-Za-z]|_|-|[0-9])*";
    private static final String FUNCTIONFORMAT = "";
    private static final String VALUEFORMAT = "\"([0-9]+";
    private static final String ARRAYFORMAT = "\"(" + VALUEFORMAT +",)*(\\)?" + VALUEFORMAT + "\"";
    private static final String DOUBLEARRAYFORMAT = "(" + ARRAYFORMAT + ")*,\\" + ARRAYFORMAT;
    private static final String INATTRIBUTESFORMAT = "";
    private static final String OUTATTRIBUTESFORMAT = "";
    private static String powerGroupSeparator = "";
    private static String timingGroupSeparator = "";
    private static final String LIBRARYFORMAT = "";
    private static final String CELLFORMAT = "";
    private static final String PINFORMAT = "";

    public static Library parseLibrary(String libraryString) throws InvalidFileFormatException {
        if (powerGroupSeparator.equals("")) {
            for (PowerGroup powGroup : PowerGroup.values()) {
                powerGroupSeparator += "|" + powGroup.name();
            }
            powerGroupSeparator.substring(1);
        }
        if (timingGroupSeparator.equals("")) {
            for (TimingGroup timGroup : TimingGroup.values()) {
                timingGroupSeparator += "|" + timGroup.name();
            }

        }
        libraryString.replaceAll("\\s+", "");
        String noCommentsString = "";
        String[] commentsSplit = libraryString.split("(/*)|(*/)");
        for (int i = 0; i < commentsSplit.length; i += 2) {
            noCommentsString += commentsSplit[i];
        }
        if (!noCommentsString.matches(LIBRARYFORMAT)) {
            throw InvalidFileFormatException;
        }
        String[] cellStrings = noCommentsString.split("(?=(cell\\())");
        ArrayList<Cell> childCells = new ArrayList<Cell>();
        for (int i = 1; i < cellStrings.length; i++) {
            childCells.add(parseCell(cellStrings[i]));
        }
        if (childCells.isEmpty()) {
            return InvalidFileFormatException;
        }
        float[] index1 = childCells.get(0).getIndex1();
        float[] index2 = childCells.get(0).getIndex2();
        for (int i = 1; i < childCells.size(); i++) {
            Cell currentCell = childCells.get(i); 
            if (!currentCell.getIndex1().equals(index1) || !currentCell.getIndex1().equals(index2)) {
                currentCell.interpolate(index1, index2);
            }
        }
        String[] libData = cellStrings[0].split("{");
        String name = libData[0].substring(7, libData[0].length() - 1);
        Library library = new Library(name, index1, index2, null, childCells);
    }

    public static Cell parseCell(String cellString) throws InvalidFileFormatException {
        String[] pinStrings = cellString.split("(?=(pin\\())");
        ArrayList<InputPin> childInPins = new ArrayList<InputPin>();
        ArrayList<OutputPin> childOutPins = new ArrayList<OutputPin>();
        for (int i = 1; i < pinStrings.length; i++) {
            Pin childPin = parsePin(pinStrings[i], childInPins);
            if (childPin instanceof InputPin) {
                childInPins.add((InputPin) childPin);
            } else {
                childOutPins.add((OutputPin) childPin);
            }
        }
        if (childInPins.isEmpty() && childOutPins.isEmpty()) {
            return InvalidFileFormatException;
        }
        String[] pinData = pinStrings[0].split("{");
        String name = pinData[0].substring(5, pinData[0].length() - 1);
        ArrayList<Pin> childPins = new ArrayList<Pin>();
        childPins.addAll(childInPins);
        childPins.addAll(childOutPins);
        for (Pin pin : childPins) {
            pin.setParent(parsedCell);
        }
    }

    public static Pin parsePin(String pinString,
                         ArrayList<InputPin> relatedPins) throws InvalidFileFormatException {
		String[] attributeStrings = pinString.split("(?=(timing\\()|internal_power\\()");
        String[] pinData = attributeStrings[0].split("\\{|\\}");
        String name = pinData[0].substring(pinData[0].indexOf("(") + 1, pinData[0].indexOf(")"));
        boolean isInput = false;
        boolean isOutput = false;
        String function = "";
        double capacitance;
        double maxCap;
        double minCap;
        ArrayList<String> leftOverData = new ArrayList<String>();
        String[] singleValueAttributes = pinData[1].split(";");
        for (String attributeData : singleValueAttributes) {
            String[] attributeParts = attributeData.split(":");
            String attrName = attributeParts[0];
            String attrValue = attributeParts[1];
            switch (attrName) {
                case "direction":
                    if (attrValue.equals("input") {
                        isInput = true;
                    } else if (attrValue.equals("output") {
                        isOutput = true;
                    }
                    break;
                case "function":
                    if (!attrValue.matches(FUNCTIONFORMAT)) {
                        throw InvalidFileFormatException;
                    }
                    function = attrValue.substring(attrValue.indexOf("\"") + 2, attrValue.indexOf("\"") - 1);
                    break;
                case "max_capacitance":
                    if (!attrValue.matches(VALUEFORMAT)) {
                        throw InvalidFileFormatException;
                    }
                    maxCap =  Float.parseFloat(attrValue);
                    break;
                case "min_capacitance":
                    if (!attrValue.matches(VALUEFORMAT)) {
                        throw InvalidFileFormatException;
                    }
                    minCap =  Float.parseFloat(attrValue);
                    break;
                case "capacitance":
                    if (!attrValue.matches(VALUEFORMAT)) {
                        throw InvalidFileFormatException;
                    }
                    capacitance =  Float.parseFloat(attrValue);
                    break;
                default:
                    leftOverData.add(attribute);
            }
        }
        if (!(isInput ^ isOutput)) {
            throw InvalidFileFormatException;
        }
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        for (int i = 1; i < attributeStrings.length; i++) {
            String attributeData = attributeStrings[i];
            InputPin relatedPin;
            if (attributeData.startsWith("internal_power")) {
                String[] powerAttributes =  attributeData.split("(?=(" + powerGroupSeparator + "))");
                if (isOutput) {
                    String[] powerAttributeData = powerAttributes[0].split("(\\{)")
                    String[] powerAttributeParameters = powerAttributeData[1].split(";");
                    for (String attributeParam : powerAttributeParameters) {
                        String[] powerAttributeParamParts = attributeParam.split(":");
                        switch (powerAttributeParamParts[0]) {
                            case "related_pin":
                                if (!powerAttributeParamParts[1].matches("\"" + NAMEFORMAT + "\"")) {
                                    throw InvalidFileFormatException;
                                }
                                for (InputPin pin: relatedPins) {
                                    if (pin.getName() == powerAttributeParamParts[1]){
                                        relatedPin = pin;
                                    }
                                }
                                break;
                            default:
                                /** No other parameters supported */
                        }
                    }
                    for (int j = 1; j < powerAttributes.length; j++) {
                        attributes.add(parseOutPower(powerAttributes[j]));
                        OutputPower power = parseOutTiming(powerAttributes[j], indexes);
                        if (relatedPin == null) {
                            throw InvalidFileFormatException("Nonexistant related Pin")
                        }
                        power.setRelatedPin(relatedPin);
                        attributes.add(power);
                    }
                } else {
                    for (int j = 1; j < powerAttributes.length; j++) {
                        attributes.add(parseInPower(powerAttributes[j]));
                    }
                }

            } else if (attributeData.startsWith("timing")) {
                TimingSense timSense;
                TimingType timType;
                String[] timingAttributes =  attributeData.split("(?=(" + timingGroupSeparator + "))");
                /** Parses timing for the output pin. Therefore ensures to fetch the relatedPin first */
                if (isOutput) {
                    String[] timingAttributeData = timingAttributes[0].split("(\\{)")
                    String[] timingAttributeParameters = timingAttributeData[1].split(";");
                    for (String attributeParam : timingAttributeParameters) {
                        String[] timingAttributeParamParts = attributeParam.split(":");
                        String value = timingAttributeParamParts[1];
                        switch (timingAttributeParamParts[0]) {
                            case "related_pin":
                                if (!value.matches("\"" + NAMEFORMAT + "\"")) {
                                    throw InvalidFileFormatException;
                                }
                                relatedPin = timingAttributeParamParts[1];
                                break;
                            case "timing_sense":
                                for (TimingSense timSenseEnum : TimingSense.values()) {
                                    if (value.matches(timSenseEnum.name())) {
                                        timSense = timSenseEnum;
                                    }
                                }
                                break;
                            case "timing_type":
                                for (TimingType timTypeEnum : TimingType.values()) {
                                    if (value.matches(timTypeEnum.name())) {
                                        timType = timTypeEnum;
                                    }   
                                }   
                                break;
                            default:
                                /** No other parameters supported */
                        }
                    }
                    for (int j = 1; j < timingAttributes.length; j++) {
                        Timing timing = parseOutTiming(timingAttributes[j], timType, timSense);
                        attributes.add(timing);
                    }
                } else {
                    /* Timing for input pins not supported and therefore ignored, although it can 
                    be found in some Liberty files */
                }
            } else {
                /* no other known multiple value attributes. Can be implemented later */
            }
        }
        if (attributes.size() != 0) {
            if (isInput) {
                float[] uniformIndex1;
                for (Attribute attribute : attributes) {
                    if (attribute instanceof InAttribute) {
                        InAttribute inAttribute = (InAttribute) attribute
                        float[] index1 = inAttribute.getIndex1();
                        if (uniformIndex1 == null) {
                            uniformIndex1 = index1;
                        }
                        if (!uniformIndex1.equals(index1)) {
                            float[] newValues = interpolate(index1, inAttribute.getValues(), uniformIndex1,);
                            inAttribute.setIndex1(uniformIndex1);
                            inAttribute.setValues(newValues);
                        }
                    } else {
                        throw InvalidFileFormatException;
                    }
                }
            } else {
                float[] uniformIndex1;
                float[] uniformIndex2;
                for (Attribute attribute : attributes) {
                    if (attribute instanceof OutAttribute) {
                        OutAttribute outAttribute = (OutAttribute) attribute
                        float[] index1 = outAttribute.getIndex1();
                        float[] index2 = outAttribute.getIndex2();
                        if (uniformIndex1 == null | uniformIndex2 == null) {
                            uniformIndex1 = index1;
                            uniformIndex2 = index2;
                        }
                        if (!uniformIndex1.equals(index1) || uniformIndex2.equals(index2)) {
                            float[][] newValues = interpolate(index1, index2, outAttribute.getValues(), uniformIndex1, uniformIndex2);
                            outAttribute.setIndex1(uniformIndex1);
                            outAttribute.setIndex2(uniformIndex2);
                            outAttribute.setValues(newValues);
                        }
                    } else {
                        throw InvalidFileFormatException;
                    }
                }
            }
        }
        if (isInput) {
            ArrayList<InputPower> powers = new ArrayList<InputPower>();
            for (Attribute attribute : attributes) {
                if (attribute instanceof InputPower) {
                    powers.add((InputPower) attribute);
                }
            return new InputPin(name, null, powers);
        }
    }

    private static OutputPower parseOutPower(String content) {
        PowerGroup powGroup;
        for (PowerGroup powGroupEnum : PowerGroup.values()) {
            if (content.startsWith(powGroupEnum.name())) {
                powGroup = powGroupEnum;
            }   
        } 
        if (powGroup == null) {
            throw InvalidFileFormatException;
        }
        float[][] values = parseDoubleArray(content, "values");
        float[] index1 = parseArray(content, "index_1");
        float[] index2 = parseArray(content, "index_2");
        OutputPower attribute = new OutputPower(powGroup, values);
        attribute.setIndex1(index1);
        attribute.setIndex2(index2);
        return attribute;
    }

    private static InputPower parseInPower(String content) {
        PowerGroup powGroup;
        for (PowerGroup powGroupEnum : PowerGroup.values()) {
            if (content.startsWith(powGroupEnum.name())) {
                powGroup = powGroupEnum;
            }   
        }
        if (powGroup == null) {
            throw InvalidFileFormatException;
        }
        float[] values = parseArray(content, "values");
        float[] index1 = parseArray(content, "index_1");
        InputPower attribute = new InputPower(powGroup, values);
        attribute.setIndex1(index1);
        return attribute;
    }

    private static Timing parseOutTiming(String content, TimingSense timSense, TimingType timType) {
        TimingGroup timGroup;
        for (TimingGroup timingEnum : TimingGroup.values()) {
            if (content.startsWith(timingEnum.name())) {
                timGroup = timingEnum;
            }
        }
        if (timGroup == null) {
            throw InvalidFileFormatException;
        }
        float[][] values = parseDoubleArray(content, "values");
        float[] index1 = parseArray(content, "index_1");
        float[] index2 = parseArray(content, "index_2");
        Timing attribute = new Timing(timSense, timType, timGroup, values);
        attribute.setIndex1(index1);
        attribute.setIndex2(index2);
        return attribute;
    }

    private static float[] parseArray(String content, String arrayName){
        int firstIndex = content.lastIndexOf(arrayName);
        int lastIndex = content.indexOf(";", firstIndex);
        String arrayString = content.substring(firstIndex, lastIndex - 1);
        return stringToArray(arrayString);
    }

    private static float[][] parseDoubleArray(String content, String arrayName){
        int firstIndex = content.lastIndexOf(arrayName);
        int lastIndex = content.indexOf(";", firstIndex);
        String arrayStrings = content.substring(firstIndex, lastIndex - 1);
        String[] separatedArrayStrings = arrayStrings.split("\\");
        float[][] result = new float[separatedArrayStrings.length][]; 
        for (int i = 0; i < separatedArrayStrings.length; i++) {
            result[i] = stringToArray(separatedArrayStrings[i]);
        }
        return result;
    }

    private static float[] stringToArray(String content) {
        content.replaceAll("(\"|\\)", "");
        String[] stringArray = content.split(",");
        float[] floatArray = new float[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            floatArray[i] = Float.parseFloat(stringArray[i]);
        }
        return floatArray;
    }
}