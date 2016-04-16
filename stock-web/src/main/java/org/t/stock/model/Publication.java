package org.t.stock.model;

import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author TOM
 */
public class Publication {

    private DateTime publicationDate;
    private ArrayList<Stock> items;

    public DateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(DateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ArrayList<Stock> getItems() {
        return items;
    }

    public void setItems(ArrayList<Stock> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ClassPojo [publicationDate = " + publicationDate + ", items = " + items + "]";
    }

}
