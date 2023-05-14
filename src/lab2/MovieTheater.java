package lab2;


public class MovieTheater {

    private final int MAX_CAPACITY;

    private String name;

    private String address;

    private String[] formats;

    public MovieTheater( int maxCapacity, String name, String address, String[] formats) {
        MAX_CAPACITY = maxCapacity;
        this.name = name;
        this.address = address;
        this.formats = formats;
    }


    public String getName() {
        return name;
    }
}
