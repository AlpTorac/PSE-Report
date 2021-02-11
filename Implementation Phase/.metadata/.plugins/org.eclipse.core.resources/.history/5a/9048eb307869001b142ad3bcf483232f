package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListDataFormatter extends DiagramDataFormatter {

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends Collection<?>> ArrayList<?> format(T data) {
		@SuppressWarnings("unchecked")
		ArrayList<?> list = new ArrayList(data);
		return list;
	}
}
