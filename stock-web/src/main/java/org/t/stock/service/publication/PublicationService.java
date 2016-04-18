package org.t.stock.service.publication;

import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
public interface PublicationService {

    Publication<Stock> getCurrentExchangeRate();

    @Transactional
    public boolean insertPublication(Publication publication);

}
