import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static StringBuilder res;
    static int n;

    public static void main(final String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(in.readLine());
        res = new StringBuilder(n * 8);
        long sum = (n * (n + 1)) / 2; //calc sum of range 1 ... n;

        if(sum % 2 == 0){
            res.append("YES\n");
            if(n % 2 == 0){
               solveForEvenInputNumber();
            }
            else {
                solveForOddInputNumber();
            }
        }
        else {
            res.append("NO");
        }

        System.out.print(res);
    }

    public static void solveForEvenInputNumber(){
       //first set
        res.append(n / 2);
        res.append("\n");
        for(int i = 1; i <= n; i+=2){
            if(i == ((n /2) + 1)){
                i++;
                }
            res.append(i);
            res.append(" ");
        }
        res.append("\n");

        //second set
        res.append(n / 2);
        res.append("\n");

        for(int i = 2; i <= n; i+= 2){
            if(i == ((n / 2) + 2)){
                i--;
            }
            res.append(i);
            res.append(" ");
        }
    }

    public static void solveForOddInputNumber(){

        final int middle = (n / 2) + 1;
        final int pivot = middle / 2;
        final int minPair = middle - 1;
        final int maxPair = middle + 1;
        final int nfirstSet = pivot % 2 == 0 ? middle : middle - 1;
        final int nSecondSet = pivot % 2 == 0 ? middle - 1 : middle;
        res.append(nfirstSet); // the middle number is equal to the number of elements in the larger set
        res.append("\n");

        //first set
        for(int i = 1; i <= n; i+= 2){
            if(pivot % 2 == 0) {
                if (i == pivot + 1) {
                    res.append(pivot);
                    res.append(" ");
                }
                if (i == middle + 1) {
                    res.append(middle);
                    res.append(" ");
                }

                if (i == minPair || i == maxPair) {
                    continue;
                }
            }
            else if(i == pivot){
                continue;
            }
            res.append(i);
            res.append(" ");
        }
        res.append("\n");

        //second set
        res.append(nSecondSet);
        res.append("\n");

        for(int i = 2; i <= n; i+= 2){
            if(pivot % 2 == 0) {
                if (i == minPair + 1) {
                    res.append(minPair);
                    res.append(" ");
                } else if (i == maxPair + 1) {
                    res.append(maxPair);
                    res.append(" ");
                }
                if (i == pivot || i == middle) {
                    continue;
                }
            }
            if(i == pivot + 1){
                res.append(pivot);
                res.append(" ");
            }
            res.append(i);
            res.append(" ");
        }
    }
}
