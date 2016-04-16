package org.t.stock.service.exchange;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.t.stock.dao.PublicationsDAO;
import org.t.stock.model.Publication;
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

    public void updatePublications() {
        System.out.println("org.t.stock.service.exchange.client.ExchangeClient.updatePublications()" + new DateTime());
        status = true;
        Publication publication = exchangeFPPublicationClient.getPublication();
        status = null != publication;
        System.out.println(status);
        System.out.println(publication);
        
        publicationsDAOImpl.insertPublication(publication);
        System.out.println("******done*****");
    }

    public static boolean getStatus() {
        return status;
    }

}
