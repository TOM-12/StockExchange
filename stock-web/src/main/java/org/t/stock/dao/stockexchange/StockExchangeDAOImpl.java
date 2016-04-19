package org.t.stock.dao.stockexchange;

import java.math.BigDecimal;
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
import org.t.stock.model.mapper.StockMapper;
import org.t.stock.model.mapper.WalletStockWithStockMapper;
import org.t.stock.model.stock.Stock;
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
                .append(" AND wallet_stocks.AMOUNT > 0")
                .append(" ORDER BY stocks.code ASC \n");

        List<WalletStock> walletStocks = jdbcTemplate.query(getWalletStocksSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        }, new WalletStockWithStockMapper());

        return walletStocks;
    }

    @Override
    public Stock getStockInfo(final long stockId, final boolean isFromStocks) {
        StringBuilder getStockInfoSql = new StringBuilder()
                .append("SELECT\n")
                .append("    stocks.ID_STOCK,\n")
                .append("    stocks.CODE,\n")
                .append("    stocks.NAME,\n")
                .append("    stocks.UNIT,\n")
                .append("    stocks.PRICE,\n")
                .append("    stocks.AVAILABLE,\n")
                .append("    stocks.LAST_PUB_ID\n")
                .append("FROM stocks\n")
                .append("WHERE 1=1\n");
        if (isFromStocks) {
            getStockInfoSql.append("AND stocks.ID_STOCK =?");
        } else {
            getStockInfoSql.append("AND stocks.ID_STOCK = (SELECT ID_STOCK FROM stocks WHERE stocks.CODE = (SELECT wallet_stocks.CODE FROM wallet_stocks WHERE wallet_stocks.ID_WALLET_STOCKS = ? ))");
        }

        Stock stock = jdbcTemplate.queryForObject(getStockInfoSql.toString(), new Object[]{stockId}, new StockMapper());

        return stock;
    }

    @Override
    public BigDecimal getAvailableMoneyInWallet(final String username) {
        StringBuilder getMoneyInWalletSql = new StringBuilder()
                .append("SELECT\n")
                .append("MONEY\n")
                .append("FROM wallets\n")
                .append("WHERE 1=1\n")
                .append("AND USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?)");

        BigDecimal money = jdbcTemplate.queryForObject(getMoneyInWalletSql.toString(), new Object[]{username}, BigDecimal.class);
        return money;
    }

    @Override
    public long getAmountOfStockInWallet(long walletStockId) {
        StringBuilder getMoneyInWalletSql = new StringBuilder()
                .append("SELECT\n")
                .append("AMOUNT\n")
                .append("FROM wallet_stocks\n")
                .append("WHERE 1=1\n")
                .append("AND ID_WALLET_STOCKS = ?");

        long amount = jdbcTemplate.queryForObject(getMoneyInWalletSql.toString(), new Object[]{walletStockId}, long.class);
        return amount;

    }

    @Override
    public void transferMoneyFromWallet(final String username, final BigDecimal batchStockPrice) {
        StringBuilder transferSql = new StringBuilder()
                .append("UPDATE wallets\n")
                .append("SET\n")
                .append("MONEY = MONEY - ?\n")
                .append("WHERE 1=1\n")
                .append("AND USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?)");

        jdbcTemplate.update(transferSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setBigDecimal(1, batchStockPrice);
                ps.setString(2, username);
            }
        });

    }

    @Override
    public void transferMoneyToWallet(final String username, final BigDecimal transactionValue) {
        StringBuilder transferSql = new StringBuilder()
                .append("UPDATE wallets\n")
                .append("SET\n")
                .append("MONEY = MONEY + ?\n")
                .append("WHERE 1=1\n")
                .append("AND USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?)");

        jdbcTemplate.update(transferSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setBigDecimal(1, transactionValue);
                ps.setString(2, username);
            }
        });
    }

    @Override
    public void buyStock(final String username, final long stockId, final long stockAmountToBuy) {
        StringBuilder sql = new StringBuilder();
        if (checkWalletForStockByStockId(username, stockId)) {
            sql.append("UPDATE wallet_stocks\n")
                    .append("SET\n")
                    .append("AMOUNT =  AMOUNT + (? *(SELECT stocks.UNIT FROM stocks WHERE stocks.ID_STOCK = ?) )\n")
                    .append("WHERE 1=1\n")
                    .append("AND CODE = (SELECT stocks.CODE FROM stocks WHERE stocks.ID_STOCK = ?)\n")
                    .append("AND WALLET_ID = (SELECT wallets.ID_WALLET FROM wallets WHERE wallets.USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?))");
        } else {
            sql.append("INSERT INTO wallet_stocks\n")
                    .append("(\n")
                    .append("AMOUNT,\n")
                    .append("CODE,\n")
                    .append("WALLET_ID)\n")
                    .append("VALUES\n")
                    .append("(\n")
                    .append(" (? *(SELECT stocks.UNIT FROM stocks WHERE stocks.ID_STOCK = ?) ,\n")
                    .append("(SELECT stocks.CODE FROM stocks WHERE stocks.ID_STOCK = ?),\n")
                    .append("(SELECT wallets.ID_WALLET FROM wallets WHERE wallets.USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?))\n")
                    .append(")");
        }
        jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, stockAmountToBuy);
                ps.setLong(2, stockId);
                ps.setLong(3, stockId);
                ps.setString(4, username);
            }
        });
        changeStockValueByAmount(stockId, (-stockAmountToBuy));
    }

    @Override
    public void sellStock(final String username, final long walletStockId, final long stockAmountToSell) {

        StringBuilder getStockIdFromWalletStockIdSql = new StringBuilder()
                .append("SELECT stocks.UNIT FROM stocks WHERE stocks.CODE = (SELECT wallet_stocks.CODE FROM wallet_stocks WHERE wallet_stocks.ID_WALLET_STOCKS = ?)");

        final long baseUnit = jdbcTemplate.queryForObject(getStockIdFromWalletStockIdSql.toString(), new Object[]{walletStockId}, Long.class);

        StringBuilder sellSql = new StringBuilder()
                .append("UPDATE wallet_stocks\n")
                .append("SET\n")
                .append("AMOUNT =  AMOUNT - ( ?* ?)\n")
                .append("WHERE 1=1\n")
                .append("AND ID_WALLET_STOCKS = ?");
        jdbcTemplate.update(sellSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, stockAmountToSell);
                ps.setLong(2, baseUnit);
                ps.setLong(3, walletStockId);
            }
        });

        StringBuilder sql = new StringBuilder()
                .append("SELECT \n")
                .append("stocks.ID_STOCK \n")
                .append("FROM stocks \n")
                .append("WHERE 1=1\n")
                .append("AND stocks.CODE = (SELECT wallet_stocks.CODE FROM wallet_stocks WHERE wallet_stocks.ID_WALLET_STOCKS = ? ) \n");

        long idx = jdbcTemplate.queryForObject(sql.toString(), new Object[]{walletStockId}, Long.class);
        changeStockValueByAmount(idx, +stockAmountToSell);

    }

    @Override
    public boolean checkWalletForStockByWalletStockId(final String username, final long walletStockId) {
        StringBuilder checkSql = new StringBuilder()
                .append(" SELECT COUNT(*)\n")
                .append("FROM wallet_stocks\n")
                .append("WHERE 1=1\n")
                .append("AND WALLET_ID = (SELECT wallets.ID_WALLET FROM wallets WHERE wallets.USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?))\n")
                .append("AND ID_WALLET_STOCKS = ?");

        Integer cnt = jdbcTemplate.queryForObject(checkSql.toString(), new Object[]{username, walletStockId}, Integer.class);

        return cnt != null && cnt > 0;
    }

    @Override
    public boolean checkWalletForStockByStockId(String username, long stockId) {
        StringBuilder checkSql = new StringBuilder()
                .append(" SELECT COUNT(*)\n")
                .append("FROM wallet_stocks\n")
                .append("WHERE 1=1\n")
                .append("AND WALLET_ID = (SELECT wallets.ID_WALLET FROM wallets WHERE wallets.USER_ID = (SELECT users.ID_USER FROM users WHERE users.LOGIN=?))\n")
                .append("AND CODE = (SELECT stocks.CODE FROM stocks WHERE stocks.ID_STOCK = ?)");

        Integer cnt = jdbcTemplate.queryForObject(checkSql.toString(), new Object[]{username, stockId}, Integer.class);

        return cnt != null && cnt > 0;
    }

    private void changeStockValueByAmount(final long stockId, final long amount) {
        StringBuilder amountChangeSql = new StringBuilder()
                .append("UPDATE stocks\n")
                .append("SET \n")
                .append("AVAILABLE = AVAILABLE + (? * UNIT)\n")
                .append("WHERE 1=1\n")
                .append("AND ID_STOCK = ?");

        jdbcTemplate.update(amountChangeSql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, amount);
                ps.setLong(2, stockId);
            }
        });
    }

}
