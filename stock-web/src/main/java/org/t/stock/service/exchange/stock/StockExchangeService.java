package org.t.stock.service.exchange.stock;

import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.TransactionStatusEnum;
import org.t.stock.model.Wallet;

/**
 *
 * @author TOM
 */
public interface StockExchangeService {

    @Transactional
    public Wallet getUserWallet(final String username);

    @Transactional
    public TransactionStatusEnum buyStock(final String username, final long stockId, final long stockAmountToBuy);

    @Transactional
    public TransactionStatusEnum sellStock(final String username, final long walletStockId, final long stockAmountToSell);

}
