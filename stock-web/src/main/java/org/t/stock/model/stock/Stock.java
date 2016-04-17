package org.t.stock.model.stock;

import java.math.BigDecimal;

/**
 *
 * @author TOM
 */
public class Stock extends BaseStock {

    private static final long serialVersionUID = 7974031914562858221L;

    private final long id;
    private final long publicationId;
    private long available;

    public Stock(long id, long publicationId, long available, String name, String code, int unit, BigDecimal price) {
        super(name, code, unit, price);
        this.id = id;
        this.publicationId = publicationId;
        this.available = available;
    }

    public Stock(long id, long publicationId) {
        this.id = id;
        this.publicationId = publicationId;
    }

    public Stock(long id, long publicationId, long amount) {
        this.id = id;
        this.publicationId = publicationId;
        this.available = amount;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {

        this.available = available;
    }

    public long getId() {
        return id;
    }

    public long getPublicationId() {
        return publicationId;
    }

    public static class StockBuilder {

        private long id;
        private long publicationId;
        private long available;
        private String name;
        private String code;
        private int unit;
        private BigDecimal price;

        public StockBuilder() {
        }

        public StockBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public StockBuilder setPublicationId(long publicationId) {
            this.publicationId = publicationId;
            return this;
        }

        public StockBuilder setAvailable(long available) {
            this.available = available;
            return this;
        }

        public StockBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StockBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public StockBuilder setUnit(int unit) {
            this.unit = unit;
            return this;
        }

        public StockBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Stock createStock() {
            return new Stock(id, publicationId, available, name, code, unit, price);
        }

    }

}
