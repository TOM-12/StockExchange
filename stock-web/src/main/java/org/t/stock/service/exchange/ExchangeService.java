package org.t.stock.service.exchange;

import java.util.Date;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.t.stock.dao.PublicationsDAO;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;
import org.t.stock.service.exchange.client.ExchangeClient;

/**
 *
 * @author TOM
 */
@Service
public class ExchangeService {

    @Autowired
    ExchangeClient exchangeFPPublicationClient;

    @Autowired
    PublicationsDAO publicationsDAOImpl;

    private static boolean status = false;
    private Date publicationDateTime = null;

    public ExchangeService() {
        publicationDateTime = null;
    }

    public void updatePublications() {

        Publication publication = exchangeFPPublicationClient.getPublication();
        status = (null != publication);
        if (status
                && (null == publicationDateTime || publicationDateTime.getTime() != (publication.getPublicationDate().getTime()))) {
            publicationDateTime = publication.getPublicationDate();
            publicationsDAOImpl.insertPublication(publication);
        }
    }

    public static boolean getStatus() {
        return status;
    }

}
