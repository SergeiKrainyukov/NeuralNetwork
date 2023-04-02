import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Neuron {
    private final int[][] weights;
    private final int limit;
    private final int rows;
    private final int cols;

    public Neuron(int rows, int cols) {
        this.weights = new int[rows][cols];
        this.limit = 35;
        this.rows = rows;
        this.cols = cols;
    }

    public void learn(String[] dataset) throws IOException {
        for (String file : dataset) {
            int result = recognizeFromFile(file);
            if (file.contains("positive") && result != 1) {
                increaseWeights(readInputFromFile(file));
                continue;
            }
            if (file.contains("negative") && result == 1) {
                decreaseWeights(readInputFromFile(file));
            }
        }
    }

    private int recognizeFromFile(String filename) throws IOException {
        int[][] input = readInputFromFile(filename);
        double sum = 0.0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += input[i][j] * weights[i][j];
            }
        }
        int result = (sum >= limit) ? 1 : 0;
        System.out.println("File: " + filename + ", Result: " + result);
        return result;
    }

    private int[][] readInputFromFile(String filename) throws IOException {
        int[][] input = new int[rows][cols];
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null && i < rows) {
            for (int j = 0; j < cols; j++) {
                input[i][j] = line.charAt(j) == '1' ? 1 : 0;
            }
            i++;
        }
        reader.close();
        return input;
    }

    private void increaseWeights(int[][] data) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                weights[i][j] += data[i][j];
            }
        }
    }

    private void decreaseWeights(int[][] data) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                weights[i][j] -= data[i][j];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "/Users/sergeikrainyukov/Documents/development/test/NeuralNetwork/NeuralNetwork/src/";
        Neuron neuron = new Neuron(10, 10);
        String[] dataset = {
                path + "A_positive_1.txt",
                path + "A_positive_2.txt",
                path + "A_positive_3.txt",
                path + "A_positive_4.txt",
                path + "A_positive_5.txt",
                path + "A_negative_1.txt",
                path + "A_negative_2.txt",
                path + "A_negative_3.txt",
                path + "A_negative_4.txt",
                path + "A_negative_5.txt",
        };
        for (int i = 0; i < 5; i++) {
            neuron.learn(dataset);
        }
    }
}
