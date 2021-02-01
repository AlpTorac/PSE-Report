package gelf.model.parsers;

import java.util.ArrayList;
import gelf.model.elements.*;

/**
 * Parses the Liberty Files to their corresponding data objects
 */
public class LibertyParser {
    private static final String NAMEFORMAT = "([A-Za-z]|_|-|[0-9])*";
    private static final String VALUEFORMAT = "\"([0-9]+";
    private static final String ARRAYFORMAT = "\"(" + VALUEFORMAT +",)*(\\)?" + VALUEFORMAT + "\"";
    private static final String DOUBLEARRAYFORMAT = "(" + ARRAYFORMAT + ")*,\\" + ARRAYFORMAT;
    private static final String INATTRIBUTESFORMAT = "";
    private static final String OUTATTRIBUTESFORMAT = "";

    private static final String LIBRARYFORMAT = "";
    private static final String CELLFORMAT = "";
    private static final String PINFORMAT = "";


    public static Library parseLibrary(String libraryString) throws InvalidFileFormatException {
        libraryString.replaceAll("\\s+", "");
        String noCommentsString = "";
        String[] commentsSplit = libraryString.split("(/*)|(*/)");
        for (int i = 0; i < commentsSplit.length; i += 2) {
            noCommentsString += commentsSplit[i];
        }
        if (!noCommentsString.matches(LIBRARYFORMAT)) {
            throw InvalidFileFormatException;
        }
        String[] cellStrings = noCommentsString.split("cell(");
        ArrayList<Cell> childCells = new ArrayList<Cell>();
        for (int i = 1; i < cellStrings.length; i++) {
            childCells.add(parseCell("cell(" + cellStrings[i]));
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

    public static Cell parseCell(String cellString) {
        String[] pinStrings = cellString.split("pin(");
        ArrayList<Pin> childPins = new ArrayList<Pin>();
        for (int i = 1; i < pinStrings.length; i++) {
            childPins.add(parsePin("pin(" + pinStrings[i]));
        }
        if (childPins.isEmpty()) {
            return InvalidFileFormatException;
        }
        String[] pinData = pinStrings[0].split("{");
        String name = pinData[0].substring(5, pinData[0].length() - 1);
    }

    public static Pin parsePin(String pinString) {
        String[] pinData = pinString.split("{|}");
        String name = pinData[0].substring(4, pinData[0].length() - 1);
        ArrayList<String> unsupportedData = new ArrayList<String>();
        boolean isInput;
        double capacitance;
        double maxCap;
        double minCap;
        for (String data : pinData) {
            if (data != null) {
                
            }
        }
        
    }
}