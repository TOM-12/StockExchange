package org.t.stock.model;

//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import java.io.Serializable;
import org.t.stock.model.stock.StockAbstract;
import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author TOM
 */
public class Publication<T extends StockAbstract> implements Serializable {

    private static final long serialVersionUID = -1830089485896819658L;

//    @JsonSerialize
    @JsonSerialize(using = DateTimeSerializer.class)
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
