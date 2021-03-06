package gelf.model.elements;
/**
 * Keeps common data of the libraries, cells, pins and provides abstraction.
 * @author Kerem Kara
 */
public abstract class Element {
	protected boolean filtered;
	protected boolean searched;
	protected String name;
	protected String elementContent;
	private String unsupportedData = "";
	
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

	
	public String getUnsupportedData() {
		return unsupportedData;
	}

	public void setUnsupportedData(String unsupportedData) {
		this.unsupportedData = unsupportedData;
	}
	
	public abstract String getInnerPath();

	@Override
	public abstract Element clone();
}
