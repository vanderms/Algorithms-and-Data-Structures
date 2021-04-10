import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokens = new StringTokenizer(in.readLine());
        int nPupils = Integer.parseInt(tokens.nextToken());
        int nFriendships = Integer.parseInt(tokens.nextToken());

        Vertex[] pupils = new Vertex[nPupils + 1];
        for(int i = 1; i <= nPupils; i++){
            pupils[i] = new Vertex(i);
        }

        for(int i = 0; i < nFriendships; i++){
            tokens = new StringTokenizer(in.readLine());
            int pupilA = Integer.parseInt(tokens.nextToken());
            int pupilB = Integer.parseInt(tokens.nextToken());
            pupils[pupilA].connections.add(pupils[pupilB]);
            pupils[pupilB].connections.add(pupils[pupilA]);
        }

        for(int i = 1; i<= nPupils; i++){
            if(pupils[i].team == '\0'){
                if(!dfs(pupils, i)){
                    System.out.print("IMPOSSIBLE");
                    return;
                };
            }
        }

        StringBuilder answer = new StringBuilder(nPupils * 8);

        for(int i = 1; i <= nPupils; i++){
            answer.append(pupils[i].team);
            answer.append(' ');
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }


    static boolean dfs(Vertex pupils[], int idx){

        Stack<Vertex> vertexes = new Stack<>();
        vertexes.add(pupils[idx]);
        pupils[idx].team = '1';

        while(!vertexes.empty()){
            Vertex current = vertexes.pop();

            for(var adjacent : current.connections) {
                if(adjacent.team == '\0'){
                    adjacent.team = current.team == '1' ? '2' : '1';
                    vertexes.add(adjacent);
                } else if(current.team == adjacent.team){
                    return false;
                }
            }
        }
        return true;
    }
}


class Vertex {
    protected int pupil;
    protected char team;
    protected List<Vertex> connections;

    public Vertex(int pupil){
        this.pupil = pupil;
        this.team = '\0';
        this.connections = new LinkedList<>();
    }
}


