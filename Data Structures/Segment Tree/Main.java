import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

    }
}

interface SegmentTreeCriteria<T> {
    T apply(T lhs, T rhs);
}


class SegmentTree<T> {
    private final SegmentTreeCriteria<T> criteria;
    private final List<T> arr;
    private final int size;
    private final int height;

    public SegmentTree(int size, SegmentTreeCriteria<T> criteria){
        this.criteria = criteria;
        int log = 1;
        while(1 << log < size){ log++; }
        this.height = log + 1;
        this.size = 1 << log;
        this.arr = new ArrayList<>(2 * this.size);
        for(int i = 0; i < 2 * this.size; i++){
            this.arr.add(null);
        }
    }

    public T get(int index){
        return arr.get(this.size + index);
    }

    public void update(int index, final T value){

        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException();
        }

        index += this.size;
        arr.set(index, value);

        for(int i = 1; i < this.height; i++){
            index /= 2;
            int nodeA = index << 1;
            int nodeB = nodeA + 1;
            arr.set(index, criteria.apply(arr.get(nodeA), arr.get(nodeB)));
        }
    }

    public T query(int start, int end){
        if(start < 0 || end >= this.size || start > end){
            throw new IndexOutOfBoundsException();
        }
        if(start == end){
            return get(start);
        }
        else return query(start, end, 1, 1);
    }

    public T query(int start, int end, int node, int depth){
        int range = 1 << (this.height - depth);
        int left = (node - (1 << (depth - 1))) * range;
        int right = left + (range - 1);
        int middle = left + (range/2);

        if(start <= left && end >= right){
            return this.arr.get(node);
        }
        else if(end < middle){
            return query(start, end, node * 2, depth + 1);
        }
        else if(start >= middle){
            return query(start, end, node * 2 + 1, depth + 1);
        }
        else{
            return criteria.apply(
                    query(start, end, node * 2, depth + 1),
                    query(start, end, node * 2 + 1, depth + 1)
            );
        }
    }
}








