package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DiagramDataNoIndexTest {
	
	private static final float[] arr3 = new float[] {100, 101, 102, 103};
	private static final float[] arr4 = new float[] {110, 111, 112, 113};
	private static final float[] arr5 = new float[] {120, 121, 122, 123};
	private static final float[] arr6 = new float[] {130, 131, 132, 133};
	
	private static final String[] dArr3 = new String[] {"1,1","1,2","1,3","1,4"};
	private static final String[] dArr4 = new String[] {"2,1","2,2","2,3","2,4"};
	private static final String[] dArr5 = new String[] {"3,1","3,2","3,3","3,4"};
	private static final String[] dArr6 = new String[] {"4,1","4,2","4,3","4,4"};
	
	private static ArrayList<float[]> dataArrays = new ArrayList<float[]>();
	private static ArrayList<String[]> descArrays = new ArrayList<String[]>();
	
	private static int indexCount;
	
	private static DiagramData data;
	
	@BeforeAll
	static void initialize() {
		dataArrays.clear();
		descArrays.clear();
		
		dataArrays.add(arr3);
		dataArrays.add(arr4);
		dataArrays.add(arr5);
		dataArrays.add(arr6);
		
		descArrays.add(dArr3);
		descArrays.add(dArr4);
		descArrays.add(dArr5);
		descArrays.add(dArr6);
		
		indexCount = 0;
		data = new DiagramData(dataArrays, descArrays, indexCount);
	}
	
	@Test
	void indexTest() {
		ArrayList<float[]> indices = data.extractIndices();
		
		for (int i = 0; i < indexCount; i++) {
			float[] currentArray = indices.get(i);
			Assertions.assertArrayEquals(dataArrays.get(i), currentArray);
		}
	}
	
	@Test
	void indexDescTest() {
		ArrayList<String[]> indexDescs = data.extractIndexDescriptions();
		
		for (int i = 0; i < indexCount; i++) {
			String[] currentArray = indexDescs.get(i);
			Assertions.assertArrayEquals(descArrays.get(i), currentArray);
		}
	}
	
	@Test
	void valueTest() {
		ArrayList<float[]> values = data.extractValues();
		List<float[]> originalValues = dataArrays.subList(indexCount, dataArrays.size());
		
		for (int i = 0; i < originalValues.size(); i++) {
			for (int j = 0; j < originalValues.get(0).length; j++) {
				Assertions.assertEquals(originalValues.get(i)[j], values.get(i)[j]);
			}
		}
	}
	
	@Test
	void valueDescTest() {
		ArrayList<String[]> valueDesc = data.extractValueDescriptions();
		List<String[]> originalDescs = descArrays.subList(indexCount, dataArrays.size());
		
		for (int i = 0; i < originalDescs.size(); i++) {
			for (int j = 0; j < originalDescs.get(0).length; j++) {
				Assertions.assertEquals(originalDescs.get(i)[j], valueDesc.get(i)[j]);
			}
		}
	}
	
	@Test
	void minimumValueTest() {
		Assertions.assertEquals(100, data.getMinimumValue());
	}
	@Test
	void maximumValueTest() {
		Assertions.assertEquals(133, data.getMaximumValue());
	}
	@Test
	void minimumIndexTest() {
		Assertions.assertThrows(NullPointerException.class, () -> data.getMinimumIndex());
	}
	@Test
	void maximumIndexTest() {
		Assertions.assertThrows(NullPointerException.class, () -> data.getMaximumIndex());
	}
	@Test
	void minimumValueAtTest() {
		Assertions.assertEquals(100, data.getMinimumValueAt(0));
		Assertions.assertEquals(110, data.getMinimumValueAt(1));
		Assertions.assertEquals(120, data.getMinimumValueAt(2));
		Assertions.assertEquals(130, data.getMinimumValueAt(3));
	}
	@Test
	void maximumValueAtTest() {
		Assertions.assertEquals(103, data.getMaximumValueAt(0));
		Assertions.assertEquals(113, data.getMaximumValueAt(1));
		Assertions.assertEquals(123, data.getMaximumValueAt(2));
		Assertions.assertEquals(133, data.getMaximumValueAt(3));
	}
	@Test
	void minimumIndexAtTest() {
		Assertions.assertThrows(NullPointerException.class, () -> data.getMinimumIndexAt(0));
		Assertions.assertThrows(NullPointerException.class, () -> data.getMinimumIndexAt(1));
	}
	@Test
	void maximumIndexAtTest() {
		Assertions.assertThrows(NullPointerException.class, () -> data.getMaximumIndexAt(0));
		Assertions.assertThrows(NullPointerException.class, () -> data.getMaximumIndexAt(1));
	}
	@Test
	void averageValueTest() {
		Assertions.assertEquals(116.5, data.getAverageValue());
	}
	@Test
	void medianValueTest() {
		Assertions.assertEquals(116.5, data.getValueMedian());
	}
	@Test
	void valueDescriptionsTest() {
		ArrayList<String[]> valueDescs = data.extractValueDescriptions();
		Assertions.assertArrayEquals(dArr3, valueDescs.get(0));
		Assertions.assertArrayEquals(dArr4, valueDescs.get(1));
		Assertions.assertArrayEquals(dArr5, valueDescs.get(2));
		Assertions.assertArrayEquals(dArr6, valueDescs.get(3));
	}
	@Test
	void indexDescriptionsTest() {
		ArrayList<String[]> indexDescs = data.extractIndexDescriptions();
		Assertions.assertNull(indexDescs);
	}
	@Test
	void cloneTest() {
		DiagramData clone = data.clone();
		Assertions.assertTrue(data.equals(clone));
	}
	@Test
	void indexCountTest() {
		Assertions.assertEquals(indexCount, data.getNumberOfIndices());
	}
}
