package gelf.model.project;

import java.util.ArrayList;
import java.util.HashSet;

import gelf.model.elements.Element;
import gelf.model.elements.Library;

public class Project {
    private ArrayList<Library> libraries;
    private HashSet<Element> copiedElements;
    private HashSet<Element> openedInTextElements;
    private ArrayList<Updatable> updatables;
    
    public Project() {
        libraries = new ArrayList<Library>();
        copiedElements = new HashSet<Element>();
        openedInTextElements = new HashSet<Element>();
        updatables = new ArrayList<Updatable>();
    }

    public void inform() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    public void removeUpdatable(Updatable updatable) {
        updatables.remove(updatable);
    }

    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Library> getLibraries() {
        return (ArrayList<Library>) libraries.clone();
    }

    public void setLibraries(ArrayList<Library> libraries) {
        this.libraries = libraries;
    }

    @SuppressWarnings("unchecked")
    public HashSet<Element> getCopiedElements() {
        return (HashSet<Element>) copiedElements.clone();
    }

    public void setCopiedElements(HashSet<Element> copiedElements) {
        this.copiedElements = copiedElements;
    }

    @SuppressWarnings("unchecked")
    public HashSet<Element> getOpenedInTextElements() {
        return (HashSet<Element>) openedInTextElements.clone();
    }

    public void setOpenedInTextElements(HashSet<Element> openedInTextElements) {
        this.openedInTextElements = openedInTextElements;
    }
}
