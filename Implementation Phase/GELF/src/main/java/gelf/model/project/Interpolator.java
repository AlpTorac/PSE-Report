package gelf.model.project;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolator;
import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolatingFunction;

public class Interpolator {

    public Interpolator() {
    }

    public float[] interpolate(float[] indexes, float[] values, float[] newIndexes) {
    	if (indexes == null || values == null || newIndexes == null) {
    		return null;
    	}
    	if (indexes.length == 0 || values.length == 0 || newIndexes.length == 0) {
    		return null;
    	}
        float[] newValues = new float[newIndexes.length];
    	if (values.length == 1) {
        	float value = values[0];
        	for (int i = 0; i < newValues.length; i++) {
        		newValues[i] = value;
        	}
        	return newValues;
        }
        double[] doubleValues = convertFloatsToDoubles(values);
        double[] doubleIndexes = convertFloatsToDoubles(indexes);
        PolynomialFunctionLagrangeForm function;
    	NevilleInterpolator interpolator = new NevilleInterpolator();
    	function = interpolator.interpolate(doubleIndexes, doubleValues);
        for (int i = 0; i < newIndexes.length; i++) {
            Double newIndex = (double) newIndexes[i];
            newValues[i] = (float) function.value(newIndex);
        }
        return newValues;
    }

    public float[][] bicubicInterpolate(float[] indexes1, float[] indexes2,
                     float[][] values, float[] newIndexes1, float[] newIndexes2) {
        if (values == null || values[0] == null || indexes1 == null 
            || indexes2 == null || newIndexes1 == null || newIndexes2 == null){
                return null;
        }
        float[][] newValues = new float[newIndexes1.length][newIndexes2.length];
        double[][] newDoubleValues = new double[newIndexes1.length][newIndexes2.length];
        double[] doubleIndexes1 = convertFloatsToDoubles(indexes1);
        double[] doubleIndexes2 = convertFloatsToDoubles(indexes2);
        double[][] doubleValues = new double[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            doubleValues[i] = convertFloatsToDoubles(values[i]);
        }
        BicubicInterpolator interpolator = new BicubicInterpolator();
        BicubicInterpolatingFunction function = interpolator.interpolate(doubleIndexes1, doubleIndexes2, doubleValues);
        for (int i = 0; i < newIndexes1.length; i++) {
            Double newIndex1 = (double) newIndexes1[i];
            int length = newIndexes2.length;
            for (int j = 0; j < length; j++) {
                Double newIndex2 = (double) newIndexes2[j];
                newDoubleValues[i][j] = function.value(newIndex1, newIndex2);
            }
        }
        for (int i = 0; i < newDoubleValues.length; i++) {
            int length = newDoubleValues[i].length;
            for (int j = 0; j < length; j++) {
                newValues[i][j] = (float) newDoubleValues[i][j];
            }
        }
        return newValues;
    }

    /**
     * Should probably not belong here. Even if, should probably switch every float to double.
     * @param input
     * @return
     */
    private static double[] convertFloatsToDoubles(float[] floatArray) {
        if (floatArray == null) {
            return null;
        }
        double[] doubleArray = new double[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
        doubleArray[i] = floatArray[i];
        }
        return doubleArray;
    }
}
