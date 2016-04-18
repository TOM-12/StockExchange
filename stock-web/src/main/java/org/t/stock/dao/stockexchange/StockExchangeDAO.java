package org.t.stock.dao.stockexchange;

import java.util.ArrayList;
import java.util.List;
import org.t.stock.model.Wallet;
import org.t.stock.model.stock.WalletStock;

/**
 *
 * @author TOM
 */
public interface StockExchangeDAO {

    public void createWalletStocks(long walletId, ArrayList<WalletStock> walletStocks);

    public Wallet getUserWalletBasicInfo(String username);

    public List<WalletStock> getWalletStocks(long id);

}
