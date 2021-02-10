package gelf.model.elements;

import java.util.Comparator;

public class CompareElementByName implements Comparator<Element> {
    
    public int compare(Element firstElement, Element secondElement) {
        return firstElement.getName().compareTo(secondElement.getName());
    }
}
