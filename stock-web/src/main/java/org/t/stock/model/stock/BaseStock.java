package org.t.stock.model.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author TOM
 */
public abstract class BaseStock implements Serializable {

    private static final long serialVersionUID = -7259769737633298402L;

    private String name;
    private String code;
    private int unit;
    
    @NumberFormat(pattern = "0.00", style = NumberFormat.Style.CURRENCY)
    private BigDecimal price;

    public BaseStock() {
    }

    public BaseStock(String name, String code, int unit, BigDecimal price) {
        this.name = name;
        this.code = code;
        this.unit = unit;
        this.price = price;
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
