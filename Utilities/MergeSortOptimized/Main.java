import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main{

    public static void main(String[] args) {

      teste(1_000_000);
    }

    public static void teste(int total){
        Integer[] arr = new Integer[total];
        Integer[] arr2 = {4, 7, 0 , 1, 1};
        Random rand = new Random();
        for(int i = 0; i < total; i++){
            arr[i] = rand.nextInt(total * 2);
        }
        Integer[] clone = Arrays.copyOf(arr, arr.length);
        MergeSort.sort(arr, Comparator.naturalOrder());
        MergeSort.sort(arr2, Comparator.naturalOrder());

        for(int i = 1; i < total; i++){
            if(arr[i] < arr[i - 1]){
                int debug = 0;
            }
        }
    }
}

class MergeSort{

    public static <T>void sort(T[] self, Comparator<T> comp) {

        firstPass(self, comp);
        T[] buffer = Arrays.copyOf (self, (self.length / 2) + 1);

        for (int step = 2; step < self.length; step *= 2) {
            for (int second = self.length - step; second > 0; second -= (2 * step)) {
                int first = second - step;
                if (first < 0) {
                    first = 0;
                }
                merge(self, buffer, comp, first, second, second + step);
            }
        }
    }


    static <T>void merge(T[] self, T[] buffer, Comparator<T> comp, int first, int second, int end) {

        if (comp.compare(self[second], self[second - 1]) >= 0) {
            return;
        }

        int f = first;
        int s = second;
        int last = 0;

        while (s == second) {
            if (comp.compare(self[f], self[s]) > 0) {
                last = second - f;
                for (int i = 0; i < last; i++) {
                    buffer[i] = self[f + i];
                }
                self[f] = self[s];
                s++;
            } else {
                f++;
            }
        }

        int idx = f + 1;
        f = 0;

        while (f < last) {
            if (s >= end || comp.compare(buffer[f], self[s]) <= 0) {
                self[idx] = buffer[f];
                f++;
            } else {
                self[idx] = self[s];
                s++;
            }
            idx++;
        }
    }

    static <T>void firstPass(T[] self, Comparator<T> comp) {

        boolean reverse = false;
        int start = 0;

        for (int i = 1; i < self.length; i++) {
            if (reverse){
                if (comp.compare(self[i], self[i - 1]) > 0) {
                    reverse(self, start, i - 1);
                    reverse = false;
                    i--;
                }
            }
            else { // if not reverse
                if ((i & 1) != (self.length & 1)){
                    if(comp.compare(self[i], self[i - 1]) < 0) {
                        reverse = true;
                        start = i - 1;
                    } else {
                        i++;
                    }
                }
            }
        }

        if (reverse) {
            reverse(self, start, self.length - 1);
        }
    }

    static <T>void reverse(T[] self, int start, int end) {
        while (start < end) {
            T temp = self[start];
            self[start] = self[end];
            self[end] = temp;
            start++;
            end--;
        }
    }
}