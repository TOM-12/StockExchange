package org.t.stock.model;

import java.io.Serializable;
import org.t.stock.model.stock.WalletStock;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author TOM
 */
public class Wallet implements Serializable {

    private static final long serialVersionUID = -148107894098960331L;

    private final long id;
    private List<WalletStock> stocks;
    private BigDecimal money;

    public Wallet(long id, List<WalletStock> stocks, BigDecimal money) {
        this.id = id;
        this.stocks = stocks;
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public List<WalletStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<WalletStock> stocks) {
        this.stocks = stocks;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}
