package languageclassifier;

import java.util.function.Function;

public class PerceptronsLayer {
	
	private static final Function<Double, Double> sigmoid = x -> 1.0 / (1.0 + Math.exp(-x));
	private static final Function<Double, Double> dsigmoid = y -> y * (1.0 - y);
	
	private int numberOfInputs;
	private int numberOfPerceptrons;
	
	private Matrix weights;
	private Matrix bias;
	
	private double learningRate;
	
	public PerceptronsLayer(int numberOfInputs, int numberOfPerceptrons, double learningRate) {
		
		this.numberOfInputs = numberOfInputs;
		this.numberOfPerceptrons = numberOfPerceptrons;
		
		weights = new Matrix(this.numberOfPerceptrons, this.numberOfInputs);
		bias = new Matrix(this.numberOfPerceptrons, 1);
		
		weights.randomize();
		bias.randomize();
		
		this.learningRate = learningRate;
	}
	
	public double[] feedForward(double[] inputArray) {
		
		try {
			
			Matrix input = new Matrix(inputArray);
			
			Matrix output = Matrix.dot(weights, input);
			output.subtract(bias);
			output.map(sigmoid);
			
			return output.asArray();
			
		} catch(MatrixDimensionsMismatchException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public void train(double inputArray[], double[] targetArray) {
		
		try {
			
			// CONVERT ARRAYS TO MATRIX OBJECTS
			Matrix input = new Matrix(inputArray);
			Matrix target = new Matrix(targetArray);
			
			// CALCULATE ERROR
			Matrix output = new Matrix(feedForward(inputArray));
			Matrix errors = Matrix.subtract(target, output);
			
			// CALULATE GRADIENTS
			Matrix gradients = Matrix.map(output, dsigmoid);
			gradients.multiply(errors);
			
			// CALCULATE DELTAS
			Matrix deltas = Matrix.dot(gradients, Matrix.transpose(input));
			deltas.multiply(learningRate);
			
			// CALCULATE BIAS DELTAS
			Matrix biasDeltas = Matrix.multiply(Matrix.multiply(gradients, -1), learningRate);
			
			// ADJUST WEIGHTS AND BIAS
			weights.add(deltas);
			bias.add(biasDeltas);
			
		} catch(MatrixDimensionsMismatchException ex) {
			ex.printStackTrace();
		}
	}
	
	public double getError(double inputArray[], double[] targetArray) {
		
		try {
			
			Matrix input = new Matrix(inputArray);
			
			Matrix output = Matrix.dot(weights, input);
			output.subtract(bias);
			output.map(sigmoid);
			
			double[] outputArray = output.asArray();
			
			double error = 0;
			for(int i=0; i<outputArray.length; i++) {
				error += Math.pow((targetArray[i] - outputArray[i]), 2);
			}
			
			error *= 0.5;
			
			return error;
			
		} catch(MatrixDimensionsMismatchException ex) {
			ex.printStackTrace();
		}
		
		return 1;
		
	}
	
	public void normalize() {
		double[] sums = new double[numberOfPerceptrons];
		double[][] weightsArray = Matrix.as2DArray(weights);
		double[] biasArray = Matrix.asArray(bias);
		
		for(int i=0; i<numberOfPerceptrons; i++) {
			for(int j=0; j<numberOfInputs; j++) {
				sums[i] += Math.pow(weightsArray[i][j], 2);
			}
			sums[i] += Math.pow(biasArray[i], 2);
		}
		
		for(int i=0; i<numberOfPerceptrons; i++) {
			sums[i] = Math.sqrt(sums[i]);
		}
		
		for(int i=0; i<numberOfPerceptrons; i++) {
			for(int j=0; j<numberOfInputs; j++) {
				weightsArray[i][j] /= sums[i];
			}
			biasArray[i] /= sums[i];
		}
		
		weights = Matrix.fromArray(weightsArray);
		bias = Matrix.fromArray(biasArray);
	}
}
