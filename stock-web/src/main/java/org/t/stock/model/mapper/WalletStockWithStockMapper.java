package org.t.stock.model.mapper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.t.stock.model.stock.WalletStock;

/**
 *
 * @author TOM
 */
public class WalletStockWithStockMapper implements RowMapper<WalletStock> {

    @Override
    public WalletStock mapRow(ResultSet rs, int rowNum) throws SQLException {
        WalletStock walletStock = new WalletStock.WalletStockBuilder()
                .setId(rs.getLong("ID_WALLET_STOCKS"))
                .setCode(rs.getString("CODE"))
                .setName(rs.getString("NAME"))
                .setUnit(rs.getInt("UNIT"))
                .setPrice(rs.getBigDecimal("PRICE"))
                .setAvailable(rs.getLong("AVAILABLE"))
                .setAmount(rs.getLong("AMOUNT"))
                .createWalletStock();

        walletStock.setValue(
                new BigDecimal(walletStock.getWalletAmount())
                .divide(new BigDecimal(walletStock.getUnit()), 4, RoundingMode.HALF_DOWN)
                .multiply(walletStock.getPrice(), new MathContext(4, RoundingMode.HALF_DOWN))
        );

        return walletStock;
    }

}
