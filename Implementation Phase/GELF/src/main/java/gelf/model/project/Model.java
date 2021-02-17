package gelf.model.project;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import gelf.model.commands.CommandHistory;

/**
 * Keeps track of the component classes the whole model as well as instantiating them
 * @author Xhulio Pernoca
 */
public class Model {
    private static Model instance = new Model();
    private Project currentProject;
    private CommandHistory currentCommandHistory;
    private static DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance(Locale.US);

    // The format for numbers bigger than e^(-4)
    private static DecimalFormat format = new DecimalFormat("#.#######", formatSymbols);
    // The scientific number format for numbers smaller than e^(-4)
    private static DecimalFormat scFormat = new DecimalFormat("0.###E00", formatSymbols);

    /**
     * Instantiates the model and it's component classes
     */
    private Model() {
        currentProject = new Project();
        currentCommandHistory = new CommandHistory();
    }
    
    /**
     * Formats a number to correspond to the Liberty file specified format
     * Not certain about the format of bigger numbers
     * @param number the number to be formatted
     * @return the formatted String of the number
     */
    public static String formatFloat(float number) {
    	if ((number < 0.0001 || number > 1000000) && number != 0) {
            return scFormat.format(number).toLowerCase();
    	} else {
    		return format.format(number);
    	}
    }

    /**
     * Formats a number while setting how many decimals have to be shown
     * @param number the number to be formatted
     * @param decimalSpots the number of decimals that are shown
     * @return the formatted String of the number
     */
    public static String formatIndex(float number, int decimalSpots) {
        String decimals = "";
        for (int i = 0; i < decimalSpots; i++) {
            decimals += "#";
        }
        DecimalFormat indexFormat = new DecimalFormat("#." + decimals, formatSymbols);
        if ((number < 0.0001 || number > 1000000) && number != 0) {
            return scFormat.format(number).toLowerCase();
    	} else {
    		return indexFormat.format(number);
    	}
    }

    /**
     * Gets the instance of the model
     * @return the instance of the model
     */
    public static Model getInstance() {
        return instance;
    }

    /**
     * Gets the current working project
     * @return the current working project
     */
    public Project getCurrentProject() {
        return currentProject;
    }

    /**
     * Gets the CommandHistory
     * @return the CommandHistory
     */
    public CommandHistory getCurrentCommandHistory() {
        return currentCommandHistory;
    }

    /**
     * Informs the project that changes have been made in one of the Settings, 
     * which haven't been added as of yet
     */
    public void inform() {
        currentProject.inform();
    }
}
