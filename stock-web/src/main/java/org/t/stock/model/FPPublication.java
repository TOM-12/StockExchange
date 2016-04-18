package org.t.stock.model;

import java.io.Serializable;
import org.t.stock.model.stock.BaseStock;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.t.stock.model.stock.PublicationStock;

/**
 *
 * @author TOM
 */
public class FPPublication implements Serializable {

    private static final long serialVersionUID = -1830089485896819658L;

    private DateTime publicationDate;
    private ArrayList<PublicationStock> items;

    public FPPublication() {
    }

    public DateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(DateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ArrayList<PublicationStock> getItems() {
        return items;
    }

    public void setItems(ArrayList<PublicationStock> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Publication [publicationDate = " + publicationDate + ", items = " + items + "]";
    }

}
