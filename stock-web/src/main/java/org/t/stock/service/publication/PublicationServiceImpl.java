package org.t.stock.service.publication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t.stock.dao.PublicationsDAO;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
@Service
public class PublicationServiceImpl implements PublicationService {

    private static final Logger LOGGER = LogManager.getLogger(PublicationServiceImpl.class.getName());

    @Autowired
    PublicationsDAO publicationsDAOImpl;

    @Override
    public Publication<Stock> getCurrentExchangeRate() {
        return publicationsDAOImpl.getCurrentExchangeRate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertPublication(Publication publication) {
        try {
            long publicationID = publicationsDAOImpl.insertPublicationData(publication.getPublicationDate());
            if (0L == publicationID) {
                LOGGER.warn("Publication not inserted");
                return false;
            } else {
                publicationsDAOImpl.insertPublishedStocks(publicationID, publication.getItems());
                publicationsDAOImpl.updateStocksUnitPriceName(publicationID, publication.getItems());
            }
        } catch (Exception ex) {
            LOGGER.catching(ex);
            throw ex;
        }
        return true;
    }

}
