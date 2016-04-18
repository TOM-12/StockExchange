package org.t.stock.dao;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
public interface PublicationsDAO {

    Publication<Stock> getCurrentExchangeRate();

    long insertPublicationData(final DateTime publicationDate);

    void insertPublishedStocks(final long publicationID, final ArrayList<PublicationStock> items);

    void updateStocksUnitPriceName(final long publicationID, final ArrayList<PublicationStock> items);

}
