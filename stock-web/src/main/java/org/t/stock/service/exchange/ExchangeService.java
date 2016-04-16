package org.t.stock.service.exchange;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.t.stock.model.Publication;
import org.t.stock.service.exchange.client.ExchangeClient;

/**
 *
 * @author TOM
 */
@Service
public class ExchangeService {

    @Autowired
    ExchangeClient exchangeClient;

    private static boolean status = false;

    public void updatePublications() {
        System.out.println("org.t.stock.service.exchange.client.ExchangeClient.updatePublications()" + new DateTime());
        status = true;
        Publication publication = exchangeClient.getPublication();
        status = null != publication;        
        System.out.println(status);
        System.out.println(publication);
    }

    public static boolean getStatus() {
        return status;
    }

}
