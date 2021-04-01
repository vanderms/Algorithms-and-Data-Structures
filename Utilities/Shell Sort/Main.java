import java.io.*;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

       Integer[] arr = {3, 1, 7, -12, 4 };
       sort(arr, (x, y) -> x - y);
       for(int i : arr) System.out.print(i + " ");
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