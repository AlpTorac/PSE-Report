package gelf.view.diagrams;

public interface IHighlightableElementContainer {
	public void receiveHoveredElementInfo(int[] indexPositions);
	public void stopHighlighting();
}
