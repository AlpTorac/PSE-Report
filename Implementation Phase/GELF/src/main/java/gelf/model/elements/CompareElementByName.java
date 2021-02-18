package gelf.model.elements;

import java.util.Comparator;

/**
 * Class that enables comparison of elements by name
 * @author Xhulio Pernoca
 */
public class CompareElementByName implements Comparator<Element> {
    
    /**
     * Compares the two elements so that they can be sorted alphabetically
     * @param firstElement the first element to be compared
     * @param secondElement the second element to be compared
     * @return a number that is negative is the first element has a name that is smaller
     * lexicographically, 0 if they are equal and positive if it is bigger
     */
    public int compare(Element firstElement, Element secondElement) {
        return firstElement.getName().compareTo(secondElement.getName());
    }
}
