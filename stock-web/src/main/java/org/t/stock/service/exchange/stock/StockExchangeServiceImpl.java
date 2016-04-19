package org.t.stock.service.exchange.stock;

import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t.stock.dao.stockexchange.StockExchangeDAO;
import org.t.stock.model.TransactionStatusEnum;
import org.t.stock.model.Wallet;
import org.t.stock.model.stock.Stock;
import org.t.stock.service.exchange.rate.ExchangeRateService;

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
    public Wallet getUserWallet(final String username) {
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
    public TransactionStatusEnum buyStock(final String username, final long stockId, final long stockAmountToBuy) {
        if (ExchangeRateService.getStatus()) {
            Stock stock = stockExchangeDAOImpl.getStockInfo(stockId, true);

            if (stock.getAvailable() < (stockAmountToBuy * stock.getUnit())) {
                return TransactionStatusEnum.NOT_AVAILABLE;
            } else {
                BigDecimal batchStockPrice = new BigDecimal(stockAmountToBuy)
                        .multiply(stock.getPrice());
                BigDecimal availableMoney = stockExchangeDAOImpl.getAvailableMoneyInWallet(username);
                if (batchStockPrice.compareTo(availableMoney) != 1) {
                    try {
                        stockExchangeDAOImpl.buyStock(username, stockId, stockAmountToBuy);
                        stockExchangeDAOImpl.transferMoneyFromWallet(username, batchStockPrice);
                        return TransactionStatusEnum.SUCCESS;
                    } catch (Exception e) {
                        LOGGER.catching(e);
                        throw e;
                    }
                } else {
                    return TransactionStatusEnum.NOT_ENOUGH_MONEY;
                }
            }
        } else {
            return TransactionStatusEnum.NO_CONNECTION;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionStatusEnum sellStock(final String username, final long walletStockId, final long stockAmountToSell) {

        if (ExchangeRateService.getStatus()) {
            if (stockExchangeDAOImpl.checkWalletForStockByWalletStockId(username, walletStockId)) {
                long stockInWallet = stockExchangeDAOImpl.getAmountOfStockInWallet(walletStockId);
                if (stockAmountToSell > stockInWallet) {
                    return TransactionStatusEnum.TOO_MUCH;
                } else {
                    Stock stock = stockExchangeDAOImpl.getStockInfo(walletStockId, false);
                    BigDecimal transactionValue = new BigDecimal(stockAmountToSell)
                            .multiply(stock.getPrice());

                    try {
                        stockExchangeDAOImpl.sellStock(username, walletStockId, stockAmountToSell);
                        stockExchangeDAOImpl.transferMoneyToWallet(username, transactionValue);
                        return TransactionStatusEnum.SUCCESS;

                    } catch (Exception e) {
                        LOGGER.catching(e);
                        throw e;
                    }
                }
            } else {
                return TransactionStatusEnum.NO_STOCK_IN_WALLET;
            }
        } else {
            return TransactionStatusEnum.NO_CONNECTION;
        }

    }
}
