package org.t.stock.service.exchange;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.t.stock.dao.PublicationsDAO;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;
import org.t.stock.service.exchange.client.ExchangeClient;
import org.t.stock.service.publication.PublicationService;

/**
 *
 * @author TOM
 */
@Service
public class ExchangeService {

    private static final Logger LOGGER = LogManager.getLogger(ExchangeService.class.getName());

    @Autowired
    private ExchangeClient exchangeFPPublicationClient;

    @Autowired
    private PublicationService publicationServiceImpl;

    private static boolean status = false;
    private DateTime publicationDateTime = null;

    public ExchangeService() {
        publicationDateTime = null;
    }

    public void updatePublications() {
        LOGGER.debug("updatePublications");

        Publication publication = exchangeFPPublicationClient.getPublication();
        if (null != publication) {
            status=true;
            LOGGER.debug("status: " + status);
            if (null == publicationDateTime || publicationDateTime.getMillis() != (publication.getPublicationDate().getMillis())) {
                publicationDateTime = publication.getPublicationDate();

                LOGGER.debug("start insert");
                try {
                    status = publicationServiceImpl.insertPublication(publication);
                } catch (Exception ex) {
                    LOGGER.warn("Unsuccesful database update");
                    LOGGER.catching(ex);
                    status = false;
                }
                LOGGER.debug("status: " + status);
            }
        }
    }

    public static boolean getStatus() {
        return status;
    }

}
