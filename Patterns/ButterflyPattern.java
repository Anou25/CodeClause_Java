import java.util.*;
public class ButterflyPattern {
    public static void main(String[] args) {
        int n=4;
        //upper part
        for(int i=1;i<=n;i++){
            //first half
            for(int j=1;j<=i;j++){
               System.out.print("*");
            }
            int spaces=2*(n-i);
            for(int j=1;j<=spaces;j++){
                System.out.print(" ");
            }
            for(int j=1;j<=i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
        //lower part
        for (int i = n; i >= 1; i--) {
            // first half
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            int spaces = 2 * (n - i);
            for (int j = 1; j <= spaces; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
