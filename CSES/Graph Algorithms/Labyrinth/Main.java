import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int[][] labyrinth;

    public static void main(String[] args) throws IOException {

        InputStreamReader inputReader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(inputReader);

        String line = in.readLine();
        StringTokenizer tokens = new StringTokenizer(line);
        int n = Integer.parseInt(tokens.nextToken());
        int m = Integer.parseInt(tokens.nextToken());
        labyrinth = new int[n + 2][m + 2];

        int startR = 0, startC = 0;

        for (int r = 1; r <= n; r++) {
            line = in.readLine();
            for (int c = 1; c <= m; c++) {
                char cell = line.charAt(c - 1);
                if (cell == '.') {
                    labyrinth[r][c] = -1;
                } else if (cell == 'A') {
                    startR = r;
                    startC = c;

                } else if (cell == 'B') {
                    labyrinth[r][c] = -2;
                }
            }
        }

        List<Node> nodes = new LinkedList<>();
        int distance = 0;
        Node end = null;

        nodes.add(new Node(null, '\0', startR, startC));

        while (!nodes.isEmpty() && end == null) {
            List<Node> temp = new ArrayList<>();
            for (Node node : nodes) {

                int r = node.r;
                int c = node.c;

                if (labyrinth[r + 1][c] < 0) {
                    Node next = new Node( node, 'D', r + 1, c);
                    if(labyrinth[r + 1][c] == -2){
                        end = next;
                        break;
                    } else {
                        labyrinth[r + 1][c] = distance + 1;
                        temp.add(next);
                    }
                }
                if (labyrinth[r - 1][c] < 0) {
                    Node next = new Node(node, 'U', r - 1, c);
                    if(labyrinth[r - 1][c] == -2){
                        end = next;
                        break;
                    } else {
                        labyrinth[r - 1][c] = distance + 1;
                        temp.add(next);
                    }
                }
                if (labyrinth[r][c + 1] < 0) {
                    Node next = new Node(node, 'R', r, c + 1);
                    if(labyrinth[r][c + 1] == -2){
                        end = next;
                        break;
                    } else {
                        labyrinth[r][c + 1] = distance + 1;
                        temp.add(next);
                    }
                }
                if (labyrinth[r][c - 1] < 0) {
                    Node next = new Node(node, 'L', r, c - 1);
                    if(labyrinth[r][c - 1] == -2){
                        end = next;
                        break;
                    } else {
                        labyrinth[r][c - 1] = distance + 1;
                        temp.add(next);
                    }
                }
            }
            distance++;
            nodes = temp;
        }

        if(end != null){
            StringBuilder out = new StringBuilder();
            out.append("YES\n");
            out.append(distance);
            out.append("\n");
            char path[] = new char[distance];
            while(end.previous != null){
                path[distance - 1] = end.direction;
                distance--;
                end = end.previous;
            }
            out.append(path);
            System.out.print(out);
        } else {
            System.out.print("NO");
        }
    }
}


class Node {
    public int r;
    public int c;
    Node previous;
    char direction;
    public Node(Node previous, char direction, int r, int c){
        this.previous = previous;
        this.direction = direction;
        this.r = r;
        this.c = c;
    }
}
