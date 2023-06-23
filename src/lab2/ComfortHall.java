package lab2;

public class ComfortHall extends Hall {
    private final int COLUMNS;

    private final int ROWS;

    private int[][] hallMatrix;

    private MovieTheater movieTheater;

    public ComfortHall(int rows, int columns, int price, MovieTheater movieTheater) {
        COLUMNS = columns;
        ROWS = rows;
        hallMatrix = new int[ROWS][COLUMNS];
        this.movieTheater = movieTheater;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                hallMatrix[i][j] = price;
            }
        }
    }

    public ComfortHall(int columns, int firstNumOfRows, int firstPrice, int secondNumOfRows, int secondPrice, MovieTheater movieTheater) {
        COLUMNS = columns;
        ROWS = firstNumOfRows + secondNumOfRows;
        hallMatrix = new int[ROWS][COLUMNS];
        this.movieTheater = movieTheater;
        for (int i = 0; i < firstNumOfRows; ++i) {
            for (int j = 0; j < columns; ++j) {
                hallMatrix[i][j] = firstPrice;
            }
        }
        for (int i = firstNumOfRows; i < ROWS; ++i) {
            for (int j = 0; j < columns; ++j) {
                hallMatrix[i][j] = secondPrice;
            }
        }
    }

    @Override
    public String toString() {
        for (int i = 1; i <= COLUMNS; ++i) {
            System.out.print("  " + i + "  ");
        }

        System.out.println();

        for (int i = 1; i <= ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                if (hallMatrix[i - 1][j] < 0) {
                    System.out.print("[   ]");
                    continue;
                }
                System.out.print("[" + hallMatrix[i - 1][j] + "]");
            }
            System.out.println(" " + i);
            System.out.println();
        }
        return null;
    }

    @Override
    public Halls getTypeOfHall() {
        return Halls.COMFORT;
    }
    @Override
    public int getCOLUMNS() {
        return COLUMNS;
    }

    @Override
    public int getROWS() {
        return ROWS;
    }

    @Override
    public int[][] getHallMatrix() {
        return hallMatrix;
    }

    @Override
    public MovieTheater getMovieTheater() {
        return movieTheater;
    }
}
