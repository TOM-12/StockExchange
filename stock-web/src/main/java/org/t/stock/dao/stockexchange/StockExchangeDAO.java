
package org.t.stock.dao.stockexchange;

import java.util.ArrayList;
import org.t.stock.model.stock.WalletStock;

/**
 *
 * @author TOM
 */
public interface StockExchangeDAO {

    public void createWalletStocks(long walletId, ArrayList<WalletStock> walletStocks);
    
}
