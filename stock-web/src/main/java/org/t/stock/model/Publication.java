package org.t.stock.model;

import java.io.Serializable;
import org.t.stock.model.stock.BaseStock;
import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author TOM
 */
public class Publication<T extends BaseStock> implements Serializable {

    private static final long serialVersionUID = -1830089485896819658L;

    private DateTime publicationDate;
    private ArrayList<T> items;

    public Publication() {
    }

    public DateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(DateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Publication [publicationDate = " + publicationDate + ", items = " + items + "]";
    }

}
