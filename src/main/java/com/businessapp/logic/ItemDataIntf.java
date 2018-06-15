package com.businessapp.logic;

import java.util.Collection;

import com.businessapp.ControllerIntf;
import com.businessapp.pojos.Item;

/**
 * Public interface to Item data.
 *
 */
public interface ItemDataIntf extends ControllerIntf {

    /**
     * Factory method that returns a Customer data source.
     * @return new instance of Customer data source.
     */
    public static ItemDataIntf getController() {
        return new ItemDataMockImpl();
    }

    /**
     * Public access methods to Customer data.
     */
    Item findItemById( String id );

    public Collection<Item> findAllItems();

    public Item newItem( String name );

    public void updateItem( Item i );

    public void deleteItems( Collection<String> ids );

}
