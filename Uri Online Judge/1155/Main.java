import java.io.IOException;

public class Main {

    public static void main(final String[] args) throws IOException {

        double res = 0;
        for(int i = 1; i <= 100; i++){
            res+= 1.0 / i;
        }

        System.out.printf("%.2f\n", res);
    }
}

