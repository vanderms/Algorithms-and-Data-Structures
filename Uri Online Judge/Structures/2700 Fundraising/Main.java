import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Candidate> allCandidates = new ArrayList<>();
        List<Integer> allIndexes = new ArrayList<>();
        List<Candidate> candidates = new ArrayList<>();
        Map<Integer, Integer> indexes = new HashMap<>();
        int numberOfCases = Integer.parseInt(in.readLine());

        //read cases
        for(int i = 0; i < numberOfCases; i++){
            StringTokenizer st = new StringTokenizer(in.readLine());
            int beauty = Integer.parseInt(st.nextToken());
            int wealth = Integer.parseInt(st.nextToken());
            int donation = Integer.parseInt(st.nextToken());
            allCandidates.add(new Candidate(beauty, wealth, donation));
            allIndexes.add(wealth);
        }

        //set up indexes hashmap
        Collections.sort(allIndexes);
        Integer previous = null;

        for(int i = 0; i < allIndexes.size(); i++){
            Integer current = allIndexes.get(i);
            if(!current.equals(previous)){
                indexes.put(current, indexes.size());
                previous = current;
            }
        }

        SegmentTree tree = new SegmentTree(indexes.size(), Math::max);

        Collections.sort(allCandidates);
        candidates.add(allCandidates.get(0));
        for(int i = 1; i < allCandidates.size(); i++){
            Candidate last = candidates.get(candidates.size() - 1);
            Candidate current = allCandidates.get(i);
            if(last.beauty == current.beauty && last.wealth == current.wealth){
                last.sumDonation(current);
            }
            else{
                candidates.add(current);
            }
        }

        long maxDonation = 0;
        List<Candidate> buffer = new ArrayList<>();

        for(int i = 0; i < candidates.size(); i++){
            Candidate current = candidates.get(i);

            while(!buffer.isEmpty() && buffer.get(buffer.size() - 1).beauty < current.beauty){

                int index = indexes.get(buffer.get(buffer.size() - 1).wealth);
                long currentIndexDonation = tree.get(index);
                long bufferDonation = buffer.get(buffer.size() - 1).getDonation();
                if(currentIndexDonation < bufferDonation){
                    tree.update(index, bufferDonation);
                }
                buffer.remove(buffer.size() - 1);
            }

            int currentIndex = indexes.get(current.wealth);
            long query = currentIndex > 0 ? tree.query(0, currentIndex - 1) : 0;
            long currentDonation = query + current.getDonation();

            buffer.add(new Candidate(current.beauty,current.wealth, currentDonation));

            maxDonation = Math.max(maxDonation, currentDonation);
        }

        System.out.println(maxDonation);
    }
}


class Candidate implements Comparable<Candidate>{
    final public int beauty;
    final public int wealth;
    private long donation;

    public Candidate(int beauty, int wealth, long donation){
        this.beauty = beauty;
        this.wealth = wealth;
        this.donation = donation;
    }

    public void sumDonation(Candidate other) {
        this.donation += other.donation;
    }

    public long getDonation() {
        return donation;
    }

    @Override
    public int compareTo(Candidate other){
        if(this.beauty > other.beauty){
            return 1;
        }
        else if(this.beauty < other.beauty){
            return -1;
        }
        else return this.wealth - other.wealth;
    }
}

interface SegmentTreeCriteria {
    long apply(long lhs, long rhs);
}

class SegmentTree {
    private final SegmentTreeCriteria criteria;
    private final long[] arr;
    private final int size;
    private final int height;

    public SegmentTree(int size, SegmentTreeCriteria criteria){
        this.criteria = criteria;
        int log = 1;
        while(1 << log < size){ log++; }
        this.height = log + 1;
        this.size = 1 << log;
        this.arr = new long[2 * this.size];
    }

    public long get(int index){
        return arr[this.size + index];
    }

    public void update(int index, final long value){

        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException();
        }

        index += this.size;
        arr[index] = value;

        for(int i = 1; i < this.height; i++){
            index /= 2;
            int nodeA = index << 1;
            int nodeB = nodeA + 1;
            arr[index] = criteria.apply(arr[nodeA], arr[nodeB]);
        }
    }

    public long query(int start, int end){
        if(start < 0 || end >= this.size || start > end){
            throw new IndexOutOfBoundsException();
        }
        if(start == end){
            return get(start);
        }
        else return query(start, end, 1, 1);
    }

    public long query(int start, int end, int node, int depth){
        int range = 1 << (this.height - depth);
        int left = (node - (1 << (depth - 1))) * range;
        int right = left + (range - 1);
        int middle = left + (range/2);

        if(start <= left && end >= right){
            return this.arr[node];
        }
        else if(end < middle){
            return query(start, end, node * 2, depth + 1);
        }
        else if(start >= middle){
            return query(start, end, node * 2 + 1, depth + 1);
        }
        else return criteria.apply(
            query(start, end, node * 2, depth + 1),
            query(start, end, node * 2 + 1, depth + 1)
        );
    }
}

