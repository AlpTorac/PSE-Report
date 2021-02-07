package gelf.view.diagrams.data;

import java.util.Collection;

public abstract class DiagramDataFormatter {
	public abstract <T extends Collection<?>> Object format(T data);
}
