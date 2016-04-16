package org.t.stock.service.exchange.client;

import org.springframework.stereotype.Repository;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;

/**
 *
 * @author TOM
 */
@Repository(value = "exchangeFPPublicationClient")
public class ExchangeClientImpl implements ExchangeClient {

    private static final String REST_SERVICE_URL = "http://webtask.future-processing.com:8068/stocks";

    @Override
    public Publication<PublicationStock> getPublication() {

        Publication<PublicationStock> publication = null;

        System.out.println("???? " + publication.getPublicationDate());

        return publication;
    }

}
