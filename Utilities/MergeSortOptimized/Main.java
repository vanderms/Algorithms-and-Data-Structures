import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main{

    public static void main(String[] args) {

        boolean isMergeFast = false;
        final int start = 1;
        final int total = 10_000;

        System.out.println("Other: " + 0);

        for(int i = start; i < total; i++){
           if(teste(i) == 1){
               if(!isMergeFast) {
                  // System.out.println("Merge: " + i);
                   isMergeFast = true;
               }
           }
           else if(isMergeFast){
               //System.out.println("Other: " + i);
               isMergeFast = false;
           }
        }
    }

    public static int teste(int total){
        Integer[] arr = new Integer[total];
        Random rand = new Random();

        for(int i = 0; i < total; i++){
            arr[i] = rand.nextInt();
        }

        Integer[] clone = Arrays.copyOf(arr, arr.length);

        long t0 = System.nanoTime();
        MergeSort.sort(arr, Comparator.naturalOrder());
        double mergeTime = (System.nanoTime() - t0) / 1_000_000_000.0;

        t0 = System.nanoTime();
        sort(clone, Comparator.naturalOrder());
        double shellTime = (System.nanoTime() - t0) / 1_000_000_000.0;

        for(int i = 0; i < total; i++){
            if(!arr[i].equals(clone[i])){
                System.out.println("Failed");
            }
        }

        if(mergeTime < shellTime){
            return 1;
        } else {
            return 0;
        }
    }

    public static <T> void sort(T[] a, Comparator<T> comp){
        int gap = 0;
        while(gap < a.length) gap = (gap * 3) + 1;
        gap/= 3;

        while (gap > 0) {
            for (int i = gap; i < a.length; i += 1) {

                T temp = a[i];
                int j = i;

                while(j >= gap && comp.compare(a[j - gap],  temp) > 0){
                    a[j] = a[j - gap];
                    j -= gap;
                }
                a[j] = temp;
            }
            gap/= 3;
        }
    }

}

class MergeSort{

    public static <T>void sort(T[] self, Comparator<T> comp) {

        firstPass(self, comp);
        int maxStep = 1;
        while(maxStep < self.length){
            maxStep <<= 1;
        }
        maxStep >>= 1;
        int bufferSize = Math.max(self.length - maxStep, maxStep / 2);

        T[] buffer = Arrays.copyOf (self, bufferSize);

        for (int step = 2; step < self.length; step *= 2) {
            for (int second = self.length - step; second > 0; second -= (2 * step)) {
                merge(self, buffer, comp, second, step);
            }
        }
    }


    static <T>void merge(T[] self, T[] buffer, Comparator<T> comp, int second, int step) {

        if (comp.compare(self[second], self[second - 1]) >= 0) {
            return;
        }

        int f = Math.max(second - step, 0);
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
        final int end = second + step;
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