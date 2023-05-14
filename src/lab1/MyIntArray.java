package lab1;

import java.util.Arrays;
import java.util.Scanner;

public class MyIntArray {
    private final int INIT_SIZE = 0;
    private int[] array;

    MyIntArray() {
        array = new int[INIT_SIZE];
    }
    public void print() {
        System.out.println(Arrays.toString(array));
    }

    public void putAllZeroesFirst() {
        int[] temp = array;
        array = new int[0];

        // копируем нули
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == 0) {
                add(0);
            }
        }

        // добавляем новые элементы
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == 0) {
                continue;
            }
            add(temp[i]);
        }
    }

    public void inputFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.trim().equals("")) {
            this.add(Integer.parseInt(input));
            input = scanner.nextLine();
        }
    }

    public void add(int item) {
        int[] temp = array;
        array = new int[temp.length + 1];
        System.arraycopy(temp, 0, array, 0, temp.length);
        array[temp.length] = item;
    }
}
