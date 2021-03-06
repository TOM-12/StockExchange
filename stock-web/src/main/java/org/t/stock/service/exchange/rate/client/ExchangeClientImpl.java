package org.t.stock.service.exchange.rate.client;

import org.springframework.stereotype.Repository;
import org.t.stock.model.Publication;
import org.t.stock.model.stock.PublicationStock;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.t.stock.model.FPPublication;

/**
 *
 * @author TOM
 */
@Repository(value = "exchangeFPPublicationClient")
public class ExchangeClientImpl implements ExchangeClient {

    private static final Logger LOGGER = LogManager.getLogger(ExchangeClientImpl.class.getName());

    private static final String REST_SERVICE_URL = "http://webtask.future-processing.com:8068/stocks";

    @Override
    public Publication<PublicationStock> getPublication() {

        Publication<PublicationStock> publication = new Publication<>();

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        WebResource webResourceGet = client.resource(REST_SERVICE_URL);
        ClientResponse response;
        try {
            response = webResourceGet.get(ClientResponse.class);
        } catch (ClientHandlerException | UniformInterfaceException exception) {
            LOGGER.error("cannot connect to publications server", exception);
            return null;
        }
        if (response.getStatus() != 200) {
            LOGGER.error("cannot connect to publications server");
            return null;
        }

        FPPublication entity = response.getEntity(FPPublication.class);
        for (PublicationStock stock : entity.getItems()) {
            publication.getItems().put(stock.getCode(), stock);
        }
        publication.setPublicationDate(entity.getPublicationDate());
        return publication;
    }

}
