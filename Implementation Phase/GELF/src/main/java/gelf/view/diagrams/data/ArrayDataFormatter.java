package gelf.view.diagrams.data;

import java.util.Collection;

public class ArrayDataFormatter extends DiagramDataFormatter {

	@Override
	public <T extends Collection<?>> Object[] format(T data) {
		return data.toArray();
	}

}
