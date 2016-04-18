package org.t.stock.service.exchange.stock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t.stock.dao.stockexchange.StockExchangeDAO;
import org.t.stock.model.Wallet;

/**
 *
 * @author TOM
 */
@Service
public class StockExchangeServiceImpl implements StockExchangeService {

    private static final Logger LOGGER = LogManager.getLogger(StockExchangeServiceImpl.class.getName());
    @Autowired
    private StockExchangeDAO stockExchangeDAOImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Wallet getUserWallet(String username) {
        try {
            Wallet wallet = stockExchangeDAOImpl.getUserWalletBasicInfo(username);

            wallet.setStocks(stockExchangeDAOImpl.getWalletStocks(wallet.getId()));

            return wallet;
        } catch (Exception e) {
            LOGGER.catching(e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyStock(String username, long stockId, long stockAmount) {
        try {
            stockExchangeDAOImpl.buyStock(username,stockId,stockAmount);

        } catch (Exception e) {
            LOGGER.catching(e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sellStock(String username, long stockId, long stockAmount) {
        try {
            stockExchangeDAOImpl.sellStock(username,stockId,stockAmount);

        } catch (Exception e) {
            LOGGER.catching(e);
            throw e;
        }
    }

}
