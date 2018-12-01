package dev.cytronix.data.cryptowat.model;

public enum DataProvider {

    BITSTAMP("Bitstamp"),
    KRAKEN("Kraken"),
    COINBASE("Coinbase");

    private String name;

    DataProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DataProvider fromName(String name) {
        for(DataProvider dataProvider : DataProvider.values()) {
            if(dataProvider.getName().equals(name)) {
                return dataProvider;
            }
        }
        return COINBASE;
    }
}
