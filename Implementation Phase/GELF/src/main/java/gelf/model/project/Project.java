package gelf.model.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import gelf.model.elements.Cell;
import gelf.model.elements.CompareElementByName;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;

/**
 * Class that keeps track of the current project state.
 * 
 * @author Xhulio Pernoca
 */
public class Project {
    private ArrayList<Library> libraries;
    private HashSet<Element> copiedElements;
    private HashSet<Element> openedInTextElements;
    private ArrayList<Updatable> updatables;

    /**
     * Constructor that creates a new state of the project. With Settings and
     * Language implemented, it would construct the default Settings. Currently it
     * only instantiates it's attributes
     */
    public Project() {
        libraries = new ArrayList<Library>();
        copiedElements = new HashSet<Element>();
        openedInTextElements = new HashSet<Element>();
        updatables = new ArrayList<Updatable>();
    }

    /**
     * It informs the subscribed components that changes have been made in the
     * project so that the data would be re-fetched
     */
    public void inform() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    /**
     * It replaces an element with another so that after an object is edited, it can
     * be updated in the project No longer used, so commented out not to count in
     * lines out code
     * 
     * @param oldElement the element to be replaced
     * @param newElement the element it is replaced with
     */
    public static void replaceElementData(Element element, Element dataElement) {
        if (element instanceof Library) {
            Library lib = (Library) element;
            Library dataLib = (Library) dataElement;
            lib.replaceData(dataLib);
        } else if (element instanceof Cell) {
            Cell cell = (Cell) element;
            Cell dataCell = (Cell) dataElement;
            cell.replaceData(dataCell);
        } else if (element instanceof InputPin) {
            InputPin pin = (InputPin) element;
            InputPin dataPin = (InputPin) dataElement;
            pin.replaceData(dataPin);
        } else {
            OutputPin pin = (OutputPin) element;
            OutputPin dataPin = (OutputPin) dataElement;
            pin.replaceData(dataPin);
        }
    }

    /**
     * Unsubscribes a component that no longer needs to be informed to avoid memory
     * leakage. Otherwise the component would be kept alive and would in addition
     * eat more resources everytime inform() is called, further slowing down the
     * program
     * 
     * @param updatable the unsubscribed component
     */
    public void removeUpdatable(Updatable updatable) {
        updatables.remove(updatable);
    }

    /**
     * Subscribes a component to the updatables so that it's informed when changes
     * are made
     * 
     * @param updatable the subscribed component
     */
    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    /**
     * Gets the loaded libraries
     * 
     * @return the loaded libraries
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Library> getLibraries() {
        return (ArrayList<Library>) libraries.clone();
    }

    /**
     * Sets the loaded libraries
     * 
     * @param libraries the loaded libraries
     */
    public void setLibraries(ArrayList<Library> libraries) {
        Collections.sort(libraries, new CompareElementByName());
        this.libraries = libraries;
    }

    /**
     * Gets the copied elements
     * 
     * @return the copied elements
     */
    @SuppressWarnings("unchecked")
    public HashSet<Element> getCopiedElements() {
        return (HashSet<Element>) copiedElements.clone();
    }

    /**
     * Sets the copied elements
     * 
     * @param copiedElements copied elements
     */
    public void setCopiedElements(HashSet<Element> copiedElements) {
        this.copiedElements = copiedElements;
    }

    /**
     * Gets the elements opened in text editor
     * 
     * @return elements opened in text editor
     */
    @SuppressWarnings("unchecked")
    public HashSet<Element> getOpenedInTextElements() {
        return (HashSet<Element>) openedInTextElements.clone();
    }

    /**
     * Sets the elements opened in text editor
     * 
     * @param openedInTextElements elements opened in text editor
     */
    public void setOpenedInTextElements(HashSet<Element> openedInTextElements) {
        this.openedInTextElements = openedInTextElements;
    }
}
