package org.t.stock.service.exchange.stock;

import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.Wallet;

/**
 *
 * @author TOM
 */
public interface StockExchangeService {

    @Transactional
    public Wallet getUserWallet(String username);

}
