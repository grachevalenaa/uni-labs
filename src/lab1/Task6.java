package lab1;

public class Task6 {
    public static void main(String[] args) {
        MyIntArray numbers = new MyIntArray();
        numbers.inputFromConsole();
        System.out.print("Массив с консоли: ");
        numbers.print();
        numbers.putAllZeroesFirst();
        System.out.print("Измененный массив: ");
        numbers.print();
    }
}
