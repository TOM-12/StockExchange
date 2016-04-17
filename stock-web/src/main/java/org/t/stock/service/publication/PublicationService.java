
package org.t.stock.service.publication;

import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
public interface PublicationService {

    Publication<Stock> getCurrentExchangeRate();
    
}
