package utils;

import java.util.Objects;

public class Key implements Comparable<Key> {

    private final String x;

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    private final String y;

    public Key(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x.equals(key.x) && y.equals(key.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Key{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Key o) {
        return this.getX().compareTo(o.getX());
    }
}