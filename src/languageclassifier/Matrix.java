package languageclassifier;

import java.util.function.Function;

public class Matrix {
	
	private int rows;
	private int cols;
	private double[][] matrix;
	
	
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		matrix = new double[rows][cols];
	}
	
	public Matrix(double[][] array) {
		this(array.length, array[0].length);
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] = array[i][j];
			}
		}
	}
	
	public Matrix(double[] array) {
		this(array.length, 1);
		
		for(int i=0; i<rows; i++) {
			matrix[i][0] = array[i];
		}
	}
	
	
	// RANDOMIZING VALUES
	
	public void randomize() {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] = Math.random()*2.0 - 1.0;
			}
		}
	}
	
	public void randomize(double min, double max) {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] = min + (max-min)*Math.random();
			}
		}
	}
	
	
	// ADDING
	
	public void add(Matrix m) throws MatrixDimensionsMismatchException {
		
		if(m.getRows() != rows || m.getColumns() != cols) {
			throw new MatrixDimensionsMismatchException("Matrices have different dimensions.");
		}
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] += m.getValue(i, j);
			}
		}
	}
	
	public void add(double n) {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] += n;
			}
		}
	}
	
	public static Matrix add(Matrix m1, Matrix m2) throws MatrixDimensionsMismatchException {
		
		if(m1.getRows() != m2.getRows() || m1.getColumns() != m2.getColumns()) {
			throw new MatrixDimensionsMismatchException("Matrices have different dimensions.");
		}
		
		double[][] result = new double[m1.getRows()][m1.getColumns()];
		
		for(int i=0; i<m1.getRows(); i++) {
			for(int j=0; j<m1.getColumns(); j++) {
				result[i][j] = m1.getValue(i,  j) + m2.getValue(i, j);
			}
		}
		
		return new Matrix(result);
	}
	
	public static Matrix add(Matrix m, double n) {
		
		double[][] result = new double[m.getRows()][m.getColumns()];
		
		for(int i=0; i<m.getRows(); i++) {
			for(int j=0; j<m.getColumns(); j++) {
				result[i][j] = m.getValue(i,  j) + n;
			}
		}
		
		return new Matrix(result);
	}
	
	
	// SUBTRACTION
	
	public void subtract(Matrix m) throws MatrixDimensionsMismatchException {
		
		if(m.getRows() != rows || m.getColumns() != cols) {
			throw new MatrixDimensionsMismatchException("Matrices have different dimensions.");
		}
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] -= m.getValue(i, j);
			}
		}
	}
	
	public void subtract(double n) {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] -= n;
			}
		}
	}
	
	public static Matrix subtract(Matrix m1, Matrix m2) throws MatrixDimensionsMismatchException {
		
		if(m1.getRows() != m2.getRows() || m1.getColumns() != m2.getColumns()) {
			throw new MatrixDimensionsMismatchException("Matrices have different dimensions.");
		}
		
		double[][] result = new double[m1.getRows()][m1.getColumns()];
		
		for(int i=0; i<m1.getRows(); i++) {
			for(int j=0; j<m1.getColumns(); j++) {
				result[i][j] = m1.getValue(i,  j) - m2.getValue(i, j);
			}
		}
		
		return new Matrix(result);
	}
	
	public static Matrix subtract(Matrix m, double n) {
		
		double[][] result = new double[m.getRows()][m.getColumns()];
		
		for(int i=0; i<m.getRows(); i++) {
			for(int j=0; j<m.getColumns(); j++) {
				result[i][j] = m.getValue(i,  j) - n;
			}
		}
		
		return new Matrix(result);
	}
	
	
	// MULTIPLICATION (by number, or element-wise)
	
	public void multiply(Matrix m) throws MatrixDimensionsMismatchException {
		
		if(m.getRows() != rows || m.getColumns() != cols) {
			throw new MatrixDimensionsMismatchException("Matrices have different dimensions.");
		}
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] *= m.getValue(i, j);
			}
		}
	}
	
	public void multiply(double n) {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] *= n;
			}
		}
	}
	
	public static Matrix multiply(Matrix m1, Matrix m2) throws MatrixDimensionsMismatchException {
		
		if(m1.getRows() != m2.getRows() || m1.getColumns() != m2.getColumns()) {
			throw new MatrixDimensionsMismatchException("Matrices have different dimensions.");
		}
		
		double[][] result = new double[m1.getRows()][m1.getColumns()];
		
		for(int i=0; i<m1.getRows(); i++) {
			for(int j=0; j<m1.getColumns(); j++) {
				result[i][j] = m1.getValue(i,  j) * m2.getValue(i, j);
			}
		}
		
		return new Matrix(result);
	}
	
	public static Matrix multiply(Matrix m, double n) {
		
		double[][] result = new double[m.getRows()][m.getColumns()];
		
		for(int i=0; i<m.getRows(); i++) {
			for(int j=0; j<m.getColumns(); j++) {
				result[i][j] = m.getValue(i,  j) * n;
			}
		}
		
		return new Matrix(result);
	}
	
	
	// DOT PRODUCT (multiplication by other matrix)
	
	public void dot(Matrix m) throws MatrixDimensionsMismatchException {
		
		if(m.getRows() != cols) {
			throw new MatrixDimensionsMismatchException("Matrices have mismatched dimensions.");
		}
		
		double[][] result = new double[rows][m.getColumns()];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<m.getColumns(); j++) {
				double sum = 0;
				for(int k=0; k<cols; k++) {
					sum += matrix[i][k] * m.getValue(k, j);
				}
				result[i][j] = sum;
			}
		}
		
		cols = m.getColumns();
		matrix = result;
	}
	
	public static Matrix dot(Matrix m1, Matrix m2) throws MatrixDimensionsMismatchException {
		
		if(m2.getRows() != m1.getColumns()) {
			throw new MatrixDimensionsMismatchException("Matrices have mismatched dimensions.");
		}
		
		double[][] result = new double[m1.getRows()][m2.getColumns()];
		
		for(int i=0; i<m1.getRows(); i++) {
			for(int j=0; j<m2.getColumns(); j++) {
				double sum = 0;
				for(int k=0; k<m1.getColumns(); k++) {
					sum += m1.getValue(i, k) * m2.getValue(k, j);
				}
				result[i][j] = sum;
			}
		}
		
		return new Matrix(result);
	}
	
	
	// TRANSPOSING
	
	public void transpose() {
		
		double[][] result = new double[cols][rows];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		
		int tmp = rows;
		rows = cols;
		cols = tmp;
		
		matrix = result;
	}
	
	public static Matrix transpose(Matrix m) {
		
		double[][] result = new double[m.getColumns()][m.getRows()];
		
		for(int i=0; i<m.getColumns(); i++) {
			for(int j=0; j<m.getRows(); j++) {
				result[i][j] = m.getValue(j,  i);
			}
		}
		
		return new Matrix(result);
	}
	
	
	// MAPPING
	
	public void map(Function<Double, Double> function) {
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				matrix[i][j] = function.apply(matrix[i][j]);
			}
		}
	}
	
	public static Matrix map(Matrix m, Function<Double, Double> function) {
		
		double[][] result = new double[m.getRows()][m.getColumns()];
		
		for(int i=0; i<m.getRows(); i++) {
			for(int j=0; j<m.getColumns(); j++) {
				result[i][j] = function.apply(m.getValue(i,  j));
			}
		}
		
		return new Matrix(result);
	}
	
	
	// GETTERS
	
	public double getValue(int x, int y) {
		return matrix[x][y];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return cols;
	}
	
	public double[] asArray() {
		
		double[] result = new double[rows];
		
		for(int i=0; i<rows; i++) {
			result[i] = matrix[i][0];
		}
		
		return result;
	}
	
	public static double[] asArray(Matrix m) {
		
		double[] result = new double[m.getRows()];
		
		for(int i=0; i<result.length; i++) {
			result[i] = m.getValue(i, 0);
		}
		
		return result;
	}
	
	public static double[][] as2DArray(Matrix m) {
		double[][] result = new double[m.getRows()][m.getColumns()];
		
		for(int i=0; i<result.length; i++) {
			for(int j=0; j<result[i].length; j++) {
				result[i][j] = m.getValue(i, j);
			}
		}
		
		return result;
	}
	
	public static Matrix fromArray(double[][] array) {
		return new Matrix(array);
	}
	
	public static Matrix fromArray(double[] array) {
		return new Matrix(array);
	}
	
	public void printMatrix() {
		System.out.printf("Rows: %d\nColumns: %d\n", rows, cols);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
}
