package org.t.stock.model;

import java.io.Serializable;
import org.t.stock.model.stock.Stock;

/**
 *
 * @author TOM
 */
public class AjaxResponse implements Serializable {

    private static final long serialVersionUID = 3781817538063820137L;

    private final boolean status;
    private final Publication<Stock> publication;
    private final Wallet wallet;

    public AjaxResponse(boolean status, Publication<Stock> publication, Wallet wallet) {
        this.status = status;
        this.publication = publication;
        this.wallet = wallet;
    }

    public boolean isStatus() {
        return status;
    }

    public Publication<Stock> getPublication() {
        return publication;
    }

    public Wallet getWallet() {
        return wallet;
    }

}
