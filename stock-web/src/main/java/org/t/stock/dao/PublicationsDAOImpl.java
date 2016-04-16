package org.t.stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.Publication;
import org.t.stock.model.Stock;

/**
 *
 * @author TOM
 */
@Repository
public class PublicationsDAOImpl implements PublicationsDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PublicationsDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public void insertPublication(final Publication publication) {
        long publicationID = insertPublicationData(publication.getPublicationDate());
        if (0 == publicationID) {
            return;
        }
        insertPublishedStocks(publicationID, publication.getItems());
        updateStocksUnitPriceName(publicationID, publication.getItems());

    }

    private long insertPublicationData(final DateTime publicationDate) {
        final StringBuilder insertPublicationSql = new StringBuilder()
                .append(" INSERT IGNORE  INTO publications \n")
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
                PreparedStatement ps = con.prepareStatement(insertPublicationSql.toString(), new String[]{"ID"});
                ps.setTimestamp(1, new Timestamp(publicationDate.getMillis()));
                return ps;
            }
        }, keyHolder);
        if (null == keyHolder.getKey()) {
            return 0;
        }
        return keyHolder.getKey().longValue();
    }

    private void insertPublishedStocks(final long publicationID, final ArrayList<Stock> items) {
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

        jdbcTemplate.batchUpdate(insertPubStocksSql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Stock stock = items.get(i);
                ps.setString(1, stock.getName());
                ps.setString(2, stock.getCode());
                ps.setInt(3, stock.getUnit());
                ps.setBigDecimal(4, stock.getPrice());
                ps.setLong(5, publicationID);
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }

    private void updateStocksUnitPriceName(final long publicationID, final ArrayList<Stock> items) {
        final StringBuilder updateStocksSql = new StringBuilder()
                .append(" UPDATE stocks SET \n")
                .append(" NAME = ? ,\n")
                .append(" UNIT = ?, \n")
                .append(" PRICE  = ?,\n")
                .append(" LAST_PUB_ID = ?  \n")
                .append("WHERE 1=1 \n")
                .append(" AND CODE = ?  \n");

        jdbcTemplate.batchUpdate(updateStocksSql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Stock stock = items.get(i);
                ps.setString(1, stock.getName());
                ps.setInt(2, stock.getUnit());
                ps.setBigDecimal(3, stock.getPrice());
                ps.setLong(4, publicationID);
                ps.setString(5, stock.getCode());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }

}
