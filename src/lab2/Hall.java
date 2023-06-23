package lab2;


public abstract class Hall {

    @Override
    public abstract String toString();

    public abstract Halls getTypeOfHall();

    public abstract MovieTheater getMovieTheater();

    public abstract int getCOLUMNS();

    public abstract int getROWS();

    public abstract int[][] getHallMatrix();
}
