package gelf.model.elements;

public abstract class Element {
	protected boolean filtered;
	protected boolean searched;
	protected String name;
	protected String elementContent;
	
	public Element() {}

	public boolean getFiltered() {
		return filtered;
	}

	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}

	public boolean getSearched() {
		return searched;
	}

	public void setSearched(boolean searched) {
		this.searched = searched;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.getName();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void calculate();
	
	public int compareTo(Element element) {
		return this.name.compareTo(element.name);
	}

	public String getElementContent() {
		return elementContent;
	}

	public void setElementContent(String elementContent) {
		this.elementContent = elementContent;
	}
	
	public abstract String getInnerPath();

	@Override
	public abstract Element clone();
}
