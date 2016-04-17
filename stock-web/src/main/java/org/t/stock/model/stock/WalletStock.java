package org.t.stock.model.stock;

import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author TOM
 */
public class WalletStock extends BaseStock {

    private static final long serialVersionUID = 6494057243507236700L;

    private long id;
    private long walletAmount;
    private long available;
    @NumberFormat(pattern = "0.00", style = NumberFormat.Style.CURRENCY)
    private BigDecimal value;

    public WalletStock() {
    }

    public WalletStock(long id) {
        this.id = id;
    }

    public WalletStock(long id, long walletAmount, long available, BigDecimal value, String name, String code, int unit, BigDecimal price) {
        super(name, code, unit, price);
        this.id = id;
        this.walletAmount = walletAmount;
        this.available = available;
        this.value = value;
    }

    public WalletStock(long id, long amount, BigDecimal value) {
        this.id = id;
        this.walletAmount = amount;
        this.value = value;
    }

    public long getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(long walletAmount) {
        this.walletAmount = walletAmount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public static class WalletStockBuilder {

        private long id;
        private long amount;
        private long available;
        private BigDecimal value;
        private String name;
        private String code;
        private int unit;
        private BigDecimal price;

        public WalletStockBuilder() {
        }

        public WalletStockBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public WalletStockBuilder setAmount(long amount) {
            this.amount = amount;
            return this;
        }

        public WalletStockBuilder setValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public WalletStockBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public WalletStockBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public WalletStockBuilder setUnit(int unit) {
            this.unit = unit;
            return this;
        }

        public WalletStockBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public WalletStockBuilder setAvailable(long available) {
            this.available = available;
            return this;
        }

        public WalletStock createWalletStock() {
            return new WalletStock(id, amount, available, value, name, code, unit, price);
        }
    }

}
