package dev.cytronix.data;

public class Data {

    private long time = System.currentTimeMillis();

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        String time = String.valueOf(this.time);
        return time.substring(10, time.length());
    }
}
