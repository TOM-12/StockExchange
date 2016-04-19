package org.t.stock.dao.stockexchange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.t.stock.model.Wallet;
import org.t.stock.model.stock.Stock;
import org.t.stock.model.stock.WalletStock;

/**
 *
 * @author TOM
 */
public interface StockExchangeDAO {

    public void createWalletStocks(final long walletId, final ArrayList<WalletStock> walletStocks);

    public Wallet getUserWalletBasicInfo(final String username);

    public List<WalletStock> getWalletStocks(final long id);

    public Stock getStockInfo(final long stockId, final boolean isFromStocks);

    public BigDecimal getAvailableMoneyInWallet(final String username);

    public long getAmountOfStockInWallet(final long walletStockId);

    public void transferMoneyFromWallet(final String username, final BigDecimal batchStockPrice);

    public void transferMoneyToWallet(final String username, final BigDecimal transactionValue);

    public void buyStock(final String username, final long stockId, final long stockAmountToBuy);

    public void sellStock(final String username, final long walletStockId, final long stockAmountToSell);

    public boolean checkWalletForStockByWalletStockId(final String username, final long walletStockId);

    public boolean checkWalletForStockByStockId(final String username, final long stockId);

}
