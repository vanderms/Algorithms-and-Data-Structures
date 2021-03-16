import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] line = in.readLine().split(" ");
        int nApplicants = Integer.parseInt(line[0]);
        int nAppartments = Integer.parseInt(line[1]);
        int maxDifference = Integer.parseInt(line[2]);
        Queue<Integer> applicants = new PriorityQueue<>(nApplicants);
        Queue<Integer> appartments = new PriorityQueue<>(nAppartments);

        line = in.readLine().split(" ");
        for(int i = 0; i < nApplicants; i++){
            applicants.add(Integer.parseInt(line[i]));
        }

        line = in.readLine().split(" ");
        for(int i = 0; i < nAppartments; i++){
            appartments.add(Integer.parseInt(line[i]));
        }

        int counter = 0;

        while(applicants.size() > 0 && appartments.size() > 0){
            if(appartments.peek() < applicants.peek() - maxDifference){
                appartments.poll();
            }
            else if(appartments.peek() > applicants.peek() + maxDifference){
                applicants.poll();
            }
            else{
                counter++;
                appartments.poll();
                applicants.poll();
            }
        }
        System.out.print(counter);
    }
}