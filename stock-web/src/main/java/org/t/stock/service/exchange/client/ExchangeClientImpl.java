package org.t.stock.service.exchange.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Repository;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;

/**
 *
 * @author TOM
 */
@Repository(value = "exchangeFPPublicationClient")
public class ExchangeClientImpl implements ExchangeClient {

    Publication<PublicationStock> publication;

        private static final String REST_SERVICE_URL = "http://webtask.future-processing.com:8068/stocks";
    @Override
    public Publication<PublicationStock> getPublication() {
        
        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        publication  = client.target(REST_SERVICE_URL).request().header("accept", "json").accept("json").buildGet().invoke(new GenericType<Publication<PublicationStock>>(){});
        
        return publication;
    }
//        try {
//            Client client = Client.create(clientConfig);
//
//            String URL = ("http://webtask.future-processing.com:8068/stocks");
//            WebResource webResourceGet = client.resource(URL);
//            webResourceGet.accept("format=json");
//            ClientResponse response = webResourceGet.get(ClientResponse.class);
//
//            publication = response.getEntity(new GenericType<Publication<PublicationStock>>() {
//            });
//            if (response.getStatus() != 200) {
//                publication = null;
//            }
//        } catch (ClientHandlerException exception) {
//            return null;
//        }
//        return publication;
//    }

}
