import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Neuron {
    private double[][] weights;
    private double limit;
    private int rows;
    private int cols;

    public Neuron(int rows, int cols) {
        this.weights = new double[rows][cols];
        this.limit = 35;
        this.rows = rows;
        this.cols = cols;
    }

    public void recognizeFromFile(String filename) throws IOException {
        double[][] input = readInputFromFile(filename);
        double sum = 0.0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += input[i][j] * weights[i][j];
            }
        }
        int result = (sum >= limit) ? 1 : 0;
        System.out.println("File: " + filename + ", Result: " + result);
    }

    private double[][] readInputFromFile(String filename) throws IOException {
        double[][] input = new double[rows][cols];
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null && i < rows) {
            for (int j = 0; j < cols; j++) {
                input[i][j] = line.charAt(j) == '1' ? 1.0 : 0.0;
            }
            i++;
        }
        reader.close();
        return input;
    }
}
