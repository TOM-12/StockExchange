package org.t.stock.dao.stockexchange;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.t.stock.model.stock.WalletStock;

/**
 *
 * @author TOM
 */
@Repository
public class StockExchangeDAOImpl implements StockExchangeDAO {

    private static final Logger LOGGER = LogManager.getLogger(StockExchangeDAOImpl.class.getName());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StockExchangeDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createWalletStocks(final long walletId, final ArrayList<WalletStock> walletStocks) {
        if (!walletStocks.isEmpty()) {
            boolean flag = false;
            for (WalletStock walletStock : walletStocks) {
                flag = walletStock.getWalletAmount() != 0;
                if (flag) {
                    break;
                }
            }
            if (flag) {
                StringBuilder sql = new StringBuilder()
                        .append("INSERT INTO wallet_stocks\n"
                                + "(\n"
                                + "CODE,\n"
                                + "AMOUNT,\n"
                                + "WALLET_ID\n"
                                + ")\n"
                                + "VALUES\n"
                                + "(\n"
                                + "?,\n"
                                + "?,\n"
                                + "?\n"
                                + ")");

                jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        WalletStock walletStock = walletStocks.get(i);
                        if (walletStock.getWalletAmount() != 0) {
                            ps.setString(1, walletStock.getCode());
                            ps.setLong(2, walletStock.getWalletAmount());
                            ps.setLong(3, walletId);
                        }
                    }

                    @Override
                    public int getBatchSize() {
                        return walletStocks.size();
                    }
                });
            }

        }
    }
}
