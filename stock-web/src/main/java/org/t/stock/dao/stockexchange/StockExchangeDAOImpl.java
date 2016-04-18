package org.t.stock.dao.stockexchange;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.t.stock.model.Wallet;
import org.t.stock.model.mapper.WalletStockWithStockMapper;
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

    @Override
    public Wallet getUserWalletBasicInfo(final String username) {
        StringBuilder getWalletBasicSql = new StringBuilder()
                .append(" SELECT \n"
                        + "ID_WALLET,\n"
                        + "MONEY\n "
                        + "FROM \n"
                        + "wallets\n"
                        + "WHERE 1=1\n"
                        + "AND USER_ID = (SELECT ID_USER FROM users WHERE LOGIN=?)\n");

        Wallet wallet = jdbcTemplate.queryForObject(getWalletBasicSql.toString(), new Object[]{username}, new RowMapper<Wallet>() {
            @Override
            public Wallet mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Wallet(rs.getLong("ID_WALLET"), null, rs.getBigDecimal("MONEY"));
            }
        });

        return wallet;

    }

    @Override
    public List<WalletStock> getWalletStocks(final long id) {
        StringBuilder getWalletStocksSql = new StringBuilder()
                .append("SELECT \n")
                .append(" wallet_stocks.ID_WALLET_STOCKS,\n")
                .append(" wallet_stocks.CODE,\n")
                .append(" stocks.NAME,\n")
                .append(" stocks.UNIT,\n")
                .append(" stocks.PRICE,\n")
                .append(" stocks.AVAILABLE,\n")
                .append(" wallet_stocks.AMOUNT\n")
                .append(" FROM wallet_stocks JOIN stocks ON wallet_stocks.CODE = stocks.CODE\n")
                .append(" WHERE 1=1\n")
                .append(" AND wallet_stocks.WALLET_ID= ?")
                .append(" ORDER BY stocks.code ASC \n");

        List<WalletStock> walletStocks = jdbcTemplate.query(getWalletStocksSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        }, new WalletStockWithStockMapper());

        return walletStocks;
    }
}
