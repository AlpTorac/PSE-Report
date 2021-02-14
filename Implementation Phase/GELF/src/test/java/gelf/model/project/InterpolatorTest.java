package gelf.model.project;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InterpolatorTest {

	@Test
	void interpolateTest1() {
		float[] index1 = new float[] {1f, 2.5f, 3.1f};
		float[] values = new float[] {0.32f, 0.45f, 0.52f};
		float[] newIndex = new float[] {1f, 2.5f, 2.2f};
		float[] newValues = Interpolator.interpolate(index1, values, newIndex);
		assertArrayEquals(new float[] {0.32f, 0.45f, 0.4188f}, newValues, 0.001f);
	}
	
	@Test
	void interpolateTest2() {
		float[] index1 = new float[] {1f, 2.5f};
		float[] values = new float[] {0.32f, 0.45f};
		float[] newIndex = new float[] {1f, 2.2f};
		float[] newValues = Interpolator.interpolate(index1, values, newIndex);
		assertArrayEquals(new float[] {0.32f, 0.424f}, newValues, 0.001f);
	}
	
	@Test
	void interpolateTest3() {
		float[] index1 = new float[] {1f};
		float[] values = new float[] {0.32f};
		float[] newIndex = new float[] {1f, 2.2f, 4.5f};
		float[] newValues = Interpolator.interpolate(index1, values, newIndex);
		assertArrayEquals(new float[] {0.32f, 0.32f, 0.32f}, newValues);
	}
	
	@Test
	void interpolateTest4() {
		float[] index1 = new float[] {1f};
		float[] values = new float[] {0.32f};
		float[] newIndex = null;
		float[] newValues = Interpolator.interpolate(index1, values, newIndex);
		Assertions.assertNull(newValues);
	}
	
	@Test
	void interpolateTest5() {
		float[] index1 = new float[] {1f};
		float[] values = new float[] {0.32f};
		float[] newIndex = new float[0];
		float[] newValues = Interpolator.interpolate(index1, values, newIndex);
		Assertions.assertNull(newValues);
	}
	
	@Test
	void bicubicInterpolateTest() {
		float[] index1 = new float[] {1f, 2.5f, 3.1f, 5.5f, 7.0f};
		float[] index2 = new float[] {0.43f, 0.51f, 0.52f, 0.64f, 76f};
		float[][] values = new float[][] {{0.32f, 0.45f, 0.52f, 2.5f, 1f}, 
										{0.1f, 0.4f, -0.3f, 23f, 0.5f},
										{0.7f, 0.43f, -1f, 5f, -4f},
										{0.7f, 0.43f, -1f, 5f, -4f},
										{0.7f, 0.43f, -1f, 5f, -4f}};
		float[] newIndex1 = new float[] {0.3f, 2.5f, 2.2f};
		float[] newIndex2 = new float[] {0.43f, 0.52f};
		float[][] newValues = Interpolator.bicubicInterpolate(index1, index2, values, newIndex1, newIndex2);
		float[][] expectedValues = new float[][] {{0.32f, 0.45f, 0.52f}, 
			{0.1f, 0.4f, -0.3f},
			{0.7f, 0.43f, -1f}};
		assertTrue(Arrays.deepEquals(expectedValues, newValues));
	}

}
