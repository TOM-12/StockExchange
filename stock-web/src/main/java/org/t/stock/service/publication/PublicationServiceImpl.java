package org.t.stock.service.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.t.stock.dao.PublicationsDAO;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    PublicationsDAO publicationsDAOImpl;

    @Override
    public Publication<Stock> getCurrentExchangeRate() {
        return publicationsDAOImpl.getCurrentExchangeRate();
    }

}
