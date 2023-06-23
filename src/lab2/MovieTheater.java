package lab2;


public class MovieTheater {

    /*
    * Уточнение:
    * в одном кинотеатре не может быть двух и более залов одинакового типа
    *
    * Требование про заранее известное количество залов и массив из залов не выполнено, так как есть база данных, устроенная по-другому
    * */

    private final int MAX_CAPACITY;

    private String name;

    private String address;

    public MovieTheater(String name, String address, int maxCapacity) {
        MAX_CAPACITY = maxCapacity;
        this.name = name;
        this.address = address;
    }


    public String getName() {
        return name;
    }
}
