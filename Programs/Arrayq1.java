import java.util.*;
public class Arrayq1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        String names[] = new String[size];
        // input
        for (int i = 0; i < size; i++) {
            names[i] = sc.next();
        }
        // output
        for (int i = 0; i < size; i++) {
            System.out.println(names[i]);
        }
    }
}
