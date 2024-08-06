import java.util.*;
public class InvertedNumPyramid {
    public static void main(String[] args) {
        int n=4;
        for (int j = 1; j <= n; j++) {
            for (int i = n - j + 1; i >= 1; i--) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
}
