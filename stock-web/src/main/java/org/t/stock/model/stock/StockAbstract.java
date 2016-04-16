package org.t.stock.model.stock;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author TOM
 */
public abstract class StockAbstract implements Serializable {

    private String name;
    private String code;
    private int unit;
    private BigDecimal price;

    public StockAbstract() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Stock  [unit = " + unit + ", price = " + price + ", name = " + name + ", code = " + code + "]";
    }

}
