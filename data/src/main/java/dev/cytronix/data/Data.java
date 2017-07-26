package dev.cytronix.data;

public class Data {

    private long time = System.currentTimeMillis();

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Data{" +
                "time=" + time +
                '}';
    }
}
