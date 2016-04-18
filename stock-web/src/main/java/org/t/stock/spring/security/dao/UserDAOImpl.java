package org.t.stock.spring.security.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.t.stock.spring.security.model.UserDetail;

/**
 *
 * @author TOM
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {

        StringBuilder sql = new StringBuilder()
                .append("  SELECT \n")
                .append(" users.ID_USER,\n")
                .append(" users.FIRST_NAME,\n")
                .append("  users.LAST_NAME,\n")
                .append(" users.LOGIN,\n")
                .append(" users.PASSWORD,\n")
                .append("  users.ROLE,\n")
                .append(" users.ENABLED\n")
                .append(" FROM users\n")
                .append(" WHERE 1=1\n")
                .append(" AND LOGIN = ? ");

        UserDetails userDetails;
        userDetails = jdbcTemplate.query(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, username);
            }
        }, new ResultSetExtractor<UserDetails>() {
            @Override
            public UserDetail extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.beforeFirst();
                rs.next();
                ArrayList<GrantedAuthority> arrayList = new ArrayList<>(0);
                arrayList.add(new SimpleGrantedAuthority(rs.getString("ROLE")));

                UserDetail ud = new UserDetail.UserDetailBuilder()
                        .setUsername(rs.getString("LOGIN"))
                        .setFirstName(rs.getString("FIRST_NAME"))
                        .setLastName(rs.getString("LAST_NAME"))
                        .setPassword(rs.getString("PASSWORD"))
                        .setEnabled(rs.getBoolean("ENABLED"))
                        .setAuthoritys(arrayList)
                        .createUserDetail();

                return ud;
            }
        });
        return userDetails;
    }

    @Override
    public boolean checkIUserExists(final String login) {
        StringBuilder sql = new StringBuilder()
                .append("SELECT \n")
                .append(" COUNT(*) \n ")
                .append(" FROM users \n")
                .append(" WHERE 1=1 \n")
                .append(" AND LOGIN =  ?");

        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, login);
        return null != count && count > 0;
    }

    @Override
    public void createUser(final String firstName, final String lastName, final String login, final String password) {
        StringBuilder sql = new StringBuilder()
                .append("INSERT INTO users \n")
                .append(" ( \n")
                .append(" FIRST_NAME, \n")
                .append(" LAST_NAME, \n")
                .append(" LOGIN, \n")
                .append(" PASSWORD \n")
                .append(" )\n")
                .append(" VALUES\n")
                .append(" (\n")
                .append(" ?,\n")
                .append("  ?,\n")
                .append(" ?,\n")
                .append(" ?\n ")
                .append(" ) ");
        jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, login);
                ps.setString(4, password);
            }
        });

    }

    @Override
    public long createWallet(final String login, final BigDecimal money) {
        final StringBuilder sql = new StringBuilder()
                .append(" INSERT INTO wallets \n")
                .append("( \n")
                .append("USER_ID, \n")
                .append("MONEY \n")
                .append(") \n")
                .append(" VALUES\n")
                .append("( \n")
                .append("(SELECT ID_USER FROM users WHERE LOGIN=?), \n")
                .append(" ?\n")
                .append(") \n");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql.toString(), new String[]{"ID_WALLET"});
                ps.setString(1, login);
                ps.setBigDecimal(2, money);
                return ps;
            }
        }, keyHolder);

        if (null == keyHolder.getKey()) {
            return 0;
        }
        return keyHolder.getKey().longValue();

    }
}
