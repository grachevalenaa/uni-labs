package lab2;

public class DataFactory {

    public static Hall getHall(Halls type, int rows, int columns, int price, MovieTheater movieTheater) {
        switch (type) {
            case STANDARD -> {
                return new StandardHall(rows, columns, price, movieTheater);
            }
            case COMFORT -> {
                return new ComfortHall(rows, columns, price, movieTheater);
            }
            case VIP -> {
                return new VIPHall(rows, columns, price, movieTheater);
            }
            case CHILDREN -> {
                return new ChildrenHall(rows, columns, price, movieTheater);
            }
        }
        return null;
    }

    public static Hall getHall(Halls type, int columns, int firstNumOfRows, int firstPrice, int secondNumOfRows, int secondPrice, MovieTheater movieTheater) {
        switch (type) {
            case STANDARD -> {
                return new StandardHall(columns, firstNumOfRows, firstPrice, secondNumOfRows, secondPrice, movieTheater);
            }
            case COMFORT -> {
                return new ComfortHall(columns, firstNumOfRows, firstPrice, secondNumOfRows, secondPrice, movieTheater);
            }
            case VIP -> {
                return new VIPHall(columns, firstNumOfRows, firstPrice, secondNumOfRows, secondPrice, movieTheater);
            }
            case CHILDREN -> {
                return new ChildrenHall(columns, firstNumOfRows, firstPrice, secondNumOfRows, secondPrice, movieTheater);
            }
        }
        return null;
    }

}
