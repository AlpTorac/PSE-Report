package gelf.view.diagrams.components;

import java.awt.Container;

public interface HasAttachablePart {
	public void attachToContainer(Container container);
	
	public void removeFromContainer();
}
