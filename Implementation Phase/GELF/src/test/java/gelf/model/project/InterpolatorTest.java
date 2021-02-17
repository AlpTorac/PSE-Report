package gelf.model.project;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InterpolatorTest {
	private static Interpolator interpolator;

	@BeforeAll
	static void buildInterpolator() {
		interpolator = new Interpolator();
	}

	@Test
	void interpolateTest1() {
		float[] index1 = new float[] {1f, 2.5f, 3.1f};
		float[] values = new float[] {0.32f, 0.45f, 0.52f};
		float[] newIndex = new float[] {1f, 2.5f, 2.2f};
		float[] newValues = interpolator.interpolate(index1, values, newIndex);
		assertArrayEquals(new float[] {0.32f, 0.45f, 0.4188f}, newValues, 0.001f);
	}
	
	@Test
	void interpolateTest2() {
		float[] index1 = new float[] {1f, 2.5f};
		float[] values = new float[] {0.32f, 0.45f};
		float[] newIndex = new float[] {1f, 2.2f};
		float[] newValues = interpolator.interpolate(index1, values, newIndex);
		assertArrayEquals(new float[] {0.32f, 0.424f}, newValues, 0.001f);
	}
	
	@Test
	void interpolateTest3() {
		float[] index1 = new float[] {1f};
		float[] values = new float[] {0.32f};
		float[] newIndex = new float[] {1f, 2.2f, 4.5f};
		float[] newValues = interpolator.interpolate(index1, values, newIndex);
		assertArrayEquals(new float[] {0.32f, 0.32f, 0.32f}, newValues);
	}
	
	@Test
	void interpolateTest4() {
		float[] index1 = new float[] {1f};
		float[] values = new float[] {0.32f};
		float[] newIndex = null;
		float[] newValues = interpolator.interpolate(index1, values, newIndex);
		Assertions.assertNull(newValues);
	}
	
	@Test
	void interpolateTest5() {
		float[] index1 = new float[] {1f};
		float[] values = new float[] {0.32f};
		float[] newIndex = new float[0];
		float[] newValues = interpolator.interpolate(index1, values, newIndex);
		Assertions.assertNull(newValues);
	}
	
	@Test
	void bicubicInterpolateTest() {
		float[] index1 = new float[] {1f, 2f};
		float[] index2 = new float[] {1f, 2f, 3f};
		float[][] values = new float[][] {
			{1, 2, 3}, // index1 = 1
			{1, 2, 3}, // index1 = 2
			};
		float[] newIndex1 = new float[] {5f, 6f};
		float[] newIndex2 = new float[] {6f, 7f};
		float[][] newValues = interpolator.bicubicInterpolate(index1, index2, values, newIndex1, newIndex2);
		float[][] expectedValues = new float[][] {
			{6, 7}, // index1 = 5
			{6, 7}, // index1 = 6
			};
		assertTrue(Arrays.deepEquals(expectedValues, newValues));
	}

}
