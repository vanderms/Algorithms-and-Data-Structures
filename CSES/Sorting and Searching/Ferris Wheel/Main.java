import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(in.readLine());
        int nChildren = Integer.parseInt(line.nextToken());
        int maxWeight = Integer.parseInt(line.nextToken());
        List<Integer> children = new ArrayList<>(nChildren);

        line = new StringTokenizer(in.readLine());
        for(int i = 0; i < nChildren; i++){
            children.add(Integer.parseInt(line.nextToken()));
        }

        Collections.sort(children);
        int start = 0;
        int end = children.size() - 1;
        int gondolas = 1;
        int currentWeight = 0;
        int currentTotal = 0;

        while(start <= end) {
            if(currentTotal < 2){
                int heavier = children.get(end);
                int lighter = children.get(start);
                if (heavier + currentWeight <= maxWeight) {
                    currentWeight += heavier;
                    currentTotal++;
                    end--;
                } else if (lighter + currentWeight <= maxWeight) {
                    currentWeight += lighter;
                    currentTotal++;
                    start++;
                } else {
                    gondolas++;
                    currentWeight = 0;
                    currentTotal = 0;
                }
            }
            else{
                gondolas++;
                currentWeight = 0;
                currentTotal = 0;
            }

        }

        System.out.print(gondolas);
    }
}