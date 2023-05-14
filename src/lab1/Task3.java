package lab1;

import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        int powValue = scanner.nextInt();
        System.out.println(pow(value, powValue));
    }

    public static double pow(int value, int powValue) {
        // если основание равно 0
        if (value == 0) {
            return 0;
        }

        // если основание равно 1 или показатель степени равен 0
        if (value == 1 || powValue == 0) {
            return 1;
        }

        // все остальные случаи
        if (powValue < 0) {
            return 1 / recursionPow(value, -powValue);
        } else {
            return recursionPow(value, powValue);
        }
    }

    public static double recursionPow(int value, int powValue) {
        if (powValue == 1) {
            return value;
        } else {
            return value * recursionPow(value, powValue - 1);
        }
    }
}
