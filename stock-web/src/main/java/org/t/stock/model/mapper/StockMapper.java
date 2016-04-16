package org.t.stock.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
public class StockMapper implements RowMapper<Stock> {

    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
        Stock stock = new Stock(
                rs.getLong("ID_STOCK"),
                rs.getLong("ID_PUBLICATION")
        );
        stock.setCode(rs.getString("CODE"));
        stock.setName(rs.getString("NAME"));
        stock.setUnit(rs.getInt("UNIT"));
        stock.setPrice(rs.getBigDecimal("PRICE"));
        stock.setAmount(rs.getLong("AMOUNT"));

        return stock;
    }

}
