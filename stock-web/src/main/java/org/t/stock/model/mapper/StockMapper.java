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
        Stock stock = new Stock.StockBuilder()
                .setId(rs.getLong("ID_STOCK"))
                .setName(rs.getString("NAME"))
                .setCode(rs.getString("CODE"))
                .setUnit(rs.getInt("UNIT"))
                .setPrice(rs.getBigDecimal("PRICE"))
                .setAvailable(rs.getLong("AVAILABLE"))
                .setPublicationId(rs.getLong("ID_PUBLICATION"))
                .createStock();

        return stock;
    }

}
