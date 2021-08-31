import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Main {
    static BufferedReader in;
    static StringBuilder out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int n = 0;
        int city = 0;
        while((n = Integer.parseInt(in.readLine())) != 0){
            city++;
            TreeMap<Integer, Integer> houses = new TreeMap<>();
            double totalConsumption = 0.0;
            int totalPeople = 0;
            for(int i = 0; i < n; i++){
                StringTokenizer tokens = new StringTokenizer(in.readLine());
                int people = Integer.parseInt(tokens.nextToken());
                int consumption = Integer.parseInt(tokens.nextToken());
                int media = consumption / people;
                if(houses.containsKey(media)){
                    houses.put(media, houses.get(media) + people);
                }
                else {
                    houses.put(media, people);
                }
                totalConsumption+= consumption;
                totalPeople += people;
            }

            out.append("Cidade# ");
            out.append(city);
            out.append(":\n");

            for(Map.Entry<Integer, Integer> house : houses.entrySet()){
                out.append(house.getValue());
                out.append("-");
                out.append(house.getKey());
                out.append(" ");
            }
            out.deleteCharAt(out.length() - 1);
            out.append("\n");
            out.append("Consumo medio: ");

            int total = (int) ((totalConsumption / totalPeople) * 100);

            out.append(String.format("%.2f", total / 100.0));
            out.append(" m3.");
            out.append("\n\n");
        }

        out.deleteCharAt(out.length() - 1);
        System.out.print(out);
    }
}

