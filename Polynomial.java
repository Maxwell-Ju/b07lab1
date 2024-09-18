public class Polynomial {
    double[] coefficient;

    public Polynomial() {
        coefficient = new double[1];
    }

    public Polynomial(double[] coefficient) {
        this.coefficient = coefficient;
    }

    public Polynomial add(Polynomial p) {
        int max = Math.max(this.coefficient.length, p.coefficient.length);
        double[] result = new double[max];

        for (int i = 0; i < this.coefficient.length; i++) {
            result[i] += this.coefficient[i];
        }

        for (int i = 0; i < p.coefficient.length; i++) {
            result[i] += p.coefficient[i];
        }

        Polynomial poly = new Polynomial(result);
        return poly;
    }

    public double evaluate(double x) {
        int i = 1; 
        int length = coefficient.length;
        double sum = coefficient[0];

        while (i < length){
            sum += coefficient[i] * Math.pow(x,i);
            i++;
        }

        return sum;
    }

    public boolean hasRoot(double x) {
        if (evaluate(x) == 0){
            return true;
        } 
        return false;
    }
}