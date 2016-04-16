package org.t.stock.model.stock;

import java.math.BigDecimal;

/**
 *
 * @author TOM
 */
public class WalletStock extends StockAbstract {

    private static final long serialVersionUID = 6494057243507236700L;

    private final long id;
    private long amount;
    private BigDecimal value;

    public WalletStock(long id) {
        this.id = id;
    }

    public WalletStock(long id, long amount, BigDecimal value) {
        this.id = id;
        this.amount = amount;
        this.value = value;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

}
