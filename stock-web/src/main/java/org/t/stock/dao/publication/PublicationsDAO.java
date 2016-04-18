package org.t.stock.dao.publication;

import java.util.Map;
import org.joda.time.DateTime;
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

    void insertPublishedStocks(final long publicationID, final Map<String, PublicationStock> items);

    void updateStocksUnitPriceName(final long publicationID, final Map<String, PublicationStock> items);

}
