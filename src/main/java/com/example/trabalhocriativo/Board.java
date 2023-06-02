package com.example.trabalhocriativo;

import java.util.Random;

public class Board {
    char[][] mat;
    int N;
    int SRN;
    int K;

    // Construtor
    Board(int N, int K) {
        this.N = N;
        this.K = K;

        // Compute square root of N
        SRN = (int) Math.sqrt(N);

        mat = new char[N][N];
    }

    public char[][] getMat() {
        return mat;
    }

    // Generador de matrix
    public char[][] fillValues() {
        fillDiagonal();
        fillRemaining(0, SRN);

        char[][] solution = copyMatrix(this.mat);

        removeKDigits();

        return solution;
    }

    // Encher a diagonal
    void fillDiagonal() {
        char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        for (int i = 0; i < N; i = i + SRN)
            fillBox(i, i, nums);
    }

    // Verificar se um número está na partição
    boolean unUsedInBox(int rowStart, int colStart, char num) {
        for (int i = 0; i < SRN; i++) {
            for (int j = 0; j < SRN; j++) {
                if (mat[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Encher a matrix.
    void fillBox(int row, int col, char[] nums) {
        int num;
        Random rand = new Random();

        for (int i = 0; i < SRN; i++) {
            for (int j = 0; j < SRN; j++) {
                do {
                    num = rand.nextInt(N);
                } while (!unUsedInBox(row, col, nums[num]));

                mat[row + i][col + j] = nums[num];
            }
        }
    }

    // Verificar se é possível inserir o número
    boolean checkIfSafe(int i, int j, char num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % SRN, j - j % SRN, num));
    }

    // Verificar se um número é usado numa linha
    boolean unUsedInRow(int i, char num) {
        for (int j = 0; j < N; j++) {
            if (mat[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // Verificar se um número é usado numa coluna
    boolean unUsedInCol(int j, char num) {
        for (int i = 0; i < N; i++) {
            if (mat[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // Encher o resto da matrix
    boolean fillRemaining(int i, int j) {
        if (j >= N && i < N - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= N && j >= N) {
            return true;
        }

        if (i < SRN) {
            if (j < SRN) {
                j = SRN;
            }
        } else if (i < N - SRN) {
            if (j == (i / SRN) * SRN) {
                j = j + SRN;
            }
        } else {
            if (j == N - SRN) {
                i = i + 1;
                j = 0;
                if (i >= N) {
                    return true;
                }
            }
        }

        Random rand = new Random();
        char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        for (int numIndex = 0; numIndex < N; numIndex++) {
            int randIndex = rand.nextInt(N - numIndex) + numIndex;
            char num = nums[randIndex];
            char temp = nums[numIndex];
            nums[numIndex] = num;
            nums[randIndex] = temp;

            if (checkIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                mat[i][j] = 0;
            }
        }
        return false;
    }

    // Remove K números
    public void removeKDigits() {
        int count = K;
        Random rand = new Random();

        while (count != 0) {
            int cellId = rand.nextInt(N * N);

            int i = cellId / N;
            int j = cellId % N;

            if (mat[i][j] != '-') {
                count--;
                mat[i][j] = '-';
            }
        }
    }

    private char[][] copyMatrix(char[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        char[][] copiedMatrix = new char[rows][columns];

        for (int i = 0; i < rows; i++)
            System.arraycopy(matrix[i], 0, copiedMatrix[i], 0, columns);

        return copiedMatrix;
    }

    public void printBoard() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");

        for (int i = 0; i < N; i++) {
            System.out.print("| ");
            for (int j = 0; j < N; j++)
                if ((j + SRN) % SRN != 0 || j == 0)
                    System.out.print(mat[i][j] + " ");
                else
                    System.out.print("| " + mat[i][j] + " ");

            System.out.println("|");

            if ((i + 1 + SRN) % SRN == 0)
                System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        }
    }

    public void displayMatrix(char[][] matrix) {
        int columns = matrix[0].length;
        int srn = (int) Math.sqrt(columns);
        int i = 0;

        System.out.println("+++++++++++++++++++++++++++++++++++++++++");

        for (char[] chars : matrix) {
            System.out.print("| ");

            for (int j = 0; j < columns; j++)
                if ((j + srn) % srn != 0 || j == 0)
                    System.out.print(chars[j] + " ");
                else
                    System.out.print("| " + chars[j] + " ");

            System.out.println("|");

            if ((i + 1 + srn) % srn == 0)
                System.out.println("+++++++++++++++++++++++++++++++++++++++++");

            i++;
        }
    }
}
