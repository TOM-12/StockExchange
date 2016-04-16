package org.t.stock.model.stock;

/**
 *
 * @author TOM
 */
public class Stock extends StockAbstract {

    private static final long serialVersionUID = 7974031914562858221L;

    private final long id;
    private final long publicationId;
    private long amount;

    public Stock(long id, long publicationId) {
        this.id = id;
        this.publicationId = publicationId;
    }

    public Stock(long id, long publicationId, long amount) {
        this.id = id;
        this.publicationId = publicationId;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {

        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getPublicationId() {
        return publicationId;
    }

}
