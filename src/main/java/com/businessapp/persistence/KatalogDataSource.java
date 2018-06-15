package com.businessapp.persistence;

import com.businessapp.Component;
import com.businessapp.ControllerIntf;
import com.businessapp.logic.ItemDataIntf;
import com.businessapp.pojos.Item;

import java.io.IOException;
import java.util.Collection;

public class KatalogDataSource implements ItemDataIntf {
    private final GenericEntityContainer<Item> items;
    private PersistenceProviderIntf persistenceProvider = null;
    /**
     * Factory method that returns a CatalogItem data source. * @return new instance of data source.
     */
    public static ItemDataIntf getController( String name, PersistenceProviderIntf persistenceProvider ) {
        ItemDataIntf cds = new KatalogDataSource( name ); cds.inject( persistenceProvider );
        return cds;
    }
    /**
     * Private constructor.
     */
    private KatalogDataSource(String name ) {
        this.items = new GenericEntityContainer<Item>( name, Item.class );
    }
    @Override
    public void inject( ControllerIntf dep ) {
        if( dep instanceof PersistenceProviderIntf ) {
            this.persistenceProvider = (PersistenceProviderIntf)dep;
        } }

    @Override
    public void inject(Component parent) {

    }

    @Override
    public void start() {
        if( persistenceProvider != null ) {
            try {
                /*
                * Attempt to load container from persistent storage.
                */
                persistenceProvider.loadInto( items.getId(), entity -> {
                    this.items.store( (Item) entity );
                return true;
                });
            } catch( IOException e ) {
                System.out.print( ", " );
                System.err.print( "No data: " + items.getId() );
            }
        }
    }

    @Override
    public void stop() {
        if (persistenceProvider != null) {
            persistenceProvider.save(items, items.getId());
        }
    }

    @Override
    public Item findItemById(String id) {
        Item c = items.findById(id);
        return c;
    }

    @Override
        public Collection<Item> findAllItems() { return items.findAll();
        }
        @Override
        public Item newItem(String name) {
            Item c = new Item(  name );
            items.store( c );
            if (persistenceProvider != null) {
                persistenceProvider.save(items, items.getId());
            }
        return c;
        }

    @Override
    public void updateItem(Item c) {
        String msg = "updated: ";
        items.update(c);
        System.err.println(msg + c.getId());
        if (persistenceProvider != null) {
            persistenceProvider.save(items, items.getId());
        }
    }

    @Override
    public void deleteItems(Collection<String> ids) {
        items.delete(ids);
        if (persistenceProvider != null) {
            persistenceProvider.save(items, items.getId());
        }
    }
}