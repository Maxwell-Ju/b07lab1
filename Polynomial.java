import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] coefficient;
    int[] exponent;

    public Polynomial() {
        // Check!
        coefficient = new double[1];
        coefficient[0] = 0;
        exponent = new int[1];
        exponent[0] = 0;
    }

    public Polynomial(double[] coefficient, int[] exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public Polynomial(File file) {
        try {
            Scanner input = new Scanner(file);
            String p = input.nextLine();
            input.close();

            String regex = "(?i)+";
            p.replaceAll(regex, " ");

            String regex2 = "(?i)-";
            p.replaceAll(regex2, " -");

            String[] arrOfSplit = p.split(" ");
            String[][] arrOfTerms = new String[arrOfSplit.length][2];
            for (int i = 0; i < arrOfSplit.length; i++) {
                String[] tmp = arrOfSplit[i].split("x");
                arrOfTerms[i] = tmp;
            }

            coefficient = new double[arrOfSplit.length];
            exponent = new int[arrOfSplit.length];
            for (int i = 0; i < arrOfSplit.length; i++) {
                if (arrOfTerms[i].length == 2) {
                    coefficient[i] = Double.parseDouble(arrOfTerms[i][0]);
                    exponent[i] = Integer.parseInt(arrOfTerms[i][1]);
                } else {
                    coefficient[i] = Double.parseDouble(arrOfTerms[i][0]);
                    exponent[i] = 0;
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public Polynomial add(Polynomial p) {
        double[] newCoefficient;
        int[] newExponent;

        // create a tmp array
        int tmpLength = p.coefficient.length + this.coefficient.length;
        newCoefficient = new double[tmpLength];
        newExponent = new int[tmpLength];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < this.exponent.length && j < p.exponent.length) {
            if (this.exponent[i] == p.exponent[j]) {
                newCoefficient[k] = this.coefficient[i] + p.coefficient[j];
                newExponent[k] = this.exponent[i];
                i++;
                j++;
            } else if (this.exponent[i] < p.exponent[j]) {
                newCoefficient[k] = this.coefficient[i];
                newExponent[k] = this.exponent[i];
                i++;
            } else {
                newCoefficient[k] = p.coefficient[j];
                newExponent[k] = p.exponent[j];
                j++;
            }
            k++;
        }

        while (i < this.exponent.length) {
            newCoefficient[k] = this.coefficient[i];
            newExponent[k] = this.exponent[i];
            i++;
            k++;
        }

        while (j < p.exponent.length) {
            newCoefficient[k] = p.coefficient[j];
            newExponent[k] = p.exponent[j];
            j++;
            k++;
        }

        int newLength = 0;
        k = 0;

        while (k < tmpLength) {
            if (newCoefficient[k] != 0) {
                newLength++;
            }
            k++;
        }

        double[] newCoefficient2 = new double[newLength];
        int[] newExponent2 = new int[newLength];

        k = 0;

        while (k < newLength) {
            newCoefficient2[k] = newCoefficient[k];
            newExponent2[k] = newExponent[k];
            k++;
        }

        Polynomial result = new Polynomial(newCoefficient2, newExponent2);
        return result;
    }

    public double evaluate(double x) {
        int i = 0;
        int length = coefficient.length;
        double sum = 0;

        while (i < length) {
            sum += coefficient[i] * Math.pow(x, exponent[i]);
            i++;
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial p) {
        double[] newCoefficient;
        int[] newExponent;

        // create a tmp array
        int tmpLength = p.coefficient.length * this.coefficient.length;
        newCoefficient = new double[tmpLength];
        newExponent = new int[tmpLength];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < p.coefficient.length) {
            while (j < this.coefficient.length) {
                newCoefficient[k] = p.coefficient[i] * this.coefficient[j];
                newExponent[k] = p.exponent[i] + this.exponent[j];
                k++;
                j++;
            }
            j = 0;
            i++;
        }

        i = 0;
        j = 0;
        k = 0;

        double[] newCoefficient2 = new double[tmpLength];
        int[] newExponent2 = new int[tmpLength];
        int count = 0;

        while (i < tmpLength) {
            int checker = 0;
            while (j < count) {
                if (newExponent[i] == newExponent2[j]) {
                    newCoefficient2[j] += newCoefficient[i];
                    checker = 1;
                    break;
                }
                j++;
            }
            if (checker == 0) {
                newExponent2[count] = newExponent[i];
                newCoefficient2[count] = newCoefficient[i];
                count++;
            }
            i++;
        }

        k = 0;
        int newLength = 0;

        while (k < count) {
            if (newCoefficient[k] != 0) {
                newLength++;
            }
            k++;
        }

        double[] newCoefficient3 = new double[newLength];
        int[] newExponent3 = new int[newLength];

        k = 0;

        while (k < count) {
            newCoefficient3[k] = newCoefficient2[k];
            newExponent3[k] = newExponent2[k];
            k++;
        }

        Polynomial result = new Polynomial(newCoefficient3, newExponent3);
        return result;
    }

    public void saveToFile(String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            int i = 0;
            while (i < this.coefficient.length) {
                myWriter.write(String.valueOf(this.coefficient[i]));
                if (this.exponent[i] != 0) {
                    myWriter.write("x");
                    myWriter.write(String.valueOf(this.exponent[i]));
                }
                i++;
                if (i < this.coefficient.length) {
                    myWriter.write("+");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}