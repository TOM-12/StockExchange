
package org.t.stock.dao;

import org.springframework.transaction.annotation.Transactional;
import org.t.stock.model.Publication;

/**
 *
 * @author TOM
 */
public interface PublicationsDAO {

    @Transactional
    void insertPublication(final Publication publication);
    
}
