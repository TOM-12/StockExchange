package org.t.stock.dao.publication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.t.stock.model.Publication;
import org.t.stock.model.mapper.StockMapper;
import org.t.stock.model.stock.PublicationStock;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
@Repository
public class PublicationsDAOImpl implements PublicationsDAO {

    private static final Logger LOGGER = LogManager.getLogger(PublicationsDAOImpl.class.getName());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PublicationsDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long insertPublicationData(final DateTime publicationDate) {
        final StringBuilder insertPublicationSql = new StringBuilder()
                .append(" INSERT INTO publications \n")
                .append("( \n")
                .append("PUB_DATE \n")
                .append(" ) \n")
                .append("VALUES ( \n")
                .append(" (?) \n")
                .append(" ) \n");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertPublicationSql.toString(), new String[]{"ID_PUBLICATION"});
                ps.setTimestamp(1, new Timestamp(publicationDate.getMillis()));
                return ps;
            }
        }, keyHolder);
        if (null == keyHolder.getKey()) {
            return 0;
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public void insertPublishedStocks(final long publicationID, final Map<String, PublicationStock> items) {
        final StringBuilder insertPubStocksSql = new StringBuilder()
                .append(" INSERT INTO  pub_stocks \n")
                .append("( \n")
                .append(" NAME ,\n")
                .append(" CODE ,\n")
                .append(" UNIT , \n")
                .append(" PRICE ,\n")
                .append(" PUB_ID \n")
                .append(" ) \n")
                .append("VALUES ( \n")
                .append(" ?, \n")
                .append(" ?, \n")
                .append(" ?, \n")
                .append(" ?, \n")
                .append(" ? \n")
                .append(" ) \n");

        jdbcTemplate.batchUpdate(insertPubStocksSql.toString(), items.values(), items.size(), new ParameterizedPreparedStatementSetter<PublicationStock>() {
            @Override
            public void setValues(PreparedStatement ps, PublicationStock argument) throws SQLException {
                if (null != argument) {
                    ps.setString(1, argument.getName());
                    ps.setString(2, argument.getCode());
                    ps.setInt(3, argument.getUnit());
                    ps.setBigDecimal(4, argument.getPrice());
                    ps.setLong(5, publicationID);
                }
            }
        });

    }

    @Override
    public void updateStocksUnitPriceName(final long publicationID, final Map<String, PublicationStock> items) {
        final StringBuilder updateStocksSql = new StringBuilder()
                .append(" UPDATE stocks SET \n")
                .append(" NAME = ? ,\n")
                .append(" UNIT = ?, \n")
                .append(" PRICE  = ?,\n")
                .append(" LAST_PUB_ID = ?  \n")
                .append("WHERE 1=1 \n")
                .append(" AND CODE = ?  \n");

        jdbcTemplate.batchUpdate(updateStocksSql.toString(), items.values(), items.size(), new ParameterizedPreparedStatementSetter<PublicationStock>() {
            @Override
            public void setValues(PreparedStatement ps, PublicationStock argument) throws SQLException {
                if (null != argument) {
                    ps.setString(1, argument.getName());
                    ps.setInt(2, argument.getUnit());
                    ps.setBigDecimal(3, argument.getPrice());
                    ps.setLong(4, publicationID);
                    ps.setString(5, argument.getCode());
                }
            }
        });
    }

    @Override
    public Publication<Stock> getCurrentExchangeRate() {

        final StringBuilder selectStockSql = new StringBuilder()
                .append(" SELECT \n")
                .append(" stocks.ID_STOCK, \n")
                .append(" stocks.CODE, \n")
                .append(" stocks.NAME, \n")
                .append(" stocks.UNIT, \n")
                .append(" stocks.PRICE, \n")
                .append(" stocks.AVAILABLE, \n")
                .append(" publications.ID_PUBLICATION \n")
                .append(" FROM stocks , publications \n")
                .append(" WHERE 1=1 \n")
                .append(" AND PUB_DATE = (SELECT MAX(PUB_DATE) FROM publications) \n")
                .append(" ORDER BY stocks.code ASC \n");

        final ArrayList<Stock> stocks = new ArrayList<>(jdbcTemplate.query(selectStockSql.toString(), new StockMapper()));

        final StringBuilder selectPublicationDateSql = new StringBuilder()
                .append(" SELECT  \n")
                .append(" PUB_DATE  \n")
                .append(" FROM publications \n")
                .append(" WHERE 1=1 \n")
                .append(" AND ID_PUBLICATION = ?  \n");

        Publication<Stock> publication = new Publication<>();
        publication.setPublicationDate(
                jdbcTemplate.query(selectPublicationDateSql.toString(), new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, stocks.get(0).getPublicationId());
                    }
                }, new ResultSetExtractor<DateTime>() {
                    @Override
                    public DateTime extractData(ResultSet rs) throws SQLException, DataAccessException {
                        rs.beforeFirst();
                        rs.next();
                        return new DateTime(rs.getTimestamp("PUB_DATE").getTime());
                    }
                })
        );
        Map<String, Stock> map = new LinkedHashMap<>(0);
        for (Stock stock : stocks) {
            map.put(stock.getCode(), stock);
        }
        publication.setItems(map);

        return publication;
    }

}
