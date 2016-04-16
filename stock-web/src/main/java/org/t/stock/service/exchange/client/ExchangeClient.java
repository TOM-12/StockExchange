package org.t.stock.service.exchange.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.springframework.stereotype.Repository;
import org.t.stock.model.Publication;

/**
 *
 * @author TOM
 */
@Repository
public class ExchangeClient {

    Publication publication;

    public Publication getPublication() {
        try {
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);

            // GET request to findBook resource with a query parameter
            String URL = ("http://webtask.future-processing.com:8068/stocks");
            WebResource webResourceGet = client.resource(URL);
            webResourceGet.accept("format=json");
            ClientResponse response = webResourceGet.get(ClientResponse.class);

            publication = response.getEntity(Publication.class);
            System.out.println(response.getStatus());
            if (response.getStatus() != 200) {
                publication = null;
            }
        } catch (ClientHandlerException exception) {
            return null;
        }
        return publication;
    }

}
