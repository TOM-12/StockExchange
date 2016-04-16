package org.t.stock.service.exchange.client;

import org.springframework.stereotype.Repository;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

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

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        WebResource webResourceGet = client.resource(REST_SERVICE_URL);
        ClientResponse response = webResourceGet.get(ClientResponse.class);

        if (response.getStatus() != 200) {
            return null;
        }

        publication = response.getEntity(new GenericType< Publication<PublicationStock>>() {
        });

        System.out.println("???? " + publication.getPublicationDate());

        return publication;
    }

}
