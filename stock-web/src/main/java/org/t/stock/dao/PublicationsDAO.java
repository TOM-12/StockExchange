package org.t.stock.dao;

import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
public interface PublicationsDAO {

    @Transactional
    void insertPublication(final Publication<PublicationStock> publication);

    Publication<Stock> getCurrentExchangeRate();
}
