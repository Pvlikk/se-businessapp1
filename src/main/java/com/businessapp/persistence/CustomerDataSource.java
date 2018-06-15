package com.businessapp.persistence;

import com.businessapp.Component;
import com.businessapp.ControllerIntf;
import com.businessapp.logic.CustomerDataIntf;
import com.businessapp.pojos.Customer;

import java.io.IOException;
import java.util.Collection;

public class CustomerDataSource implements CustomerDataIntf {
    private final GenericEntityContainer<Customer> customers;
    private PersistenceProviderIntf persistenceProvider = null;
    /**
     * Factory method that returns a CatalogItem data source. * @return new instance of data source.
     */
    public static CustomerDataIntf getController( String name, PersistenceProviderIntf persistenceProvider ) {
        CustomerDataIntf cds = new CustomerDataSource( name ); cds.inject( persistenceProvider );
        return cds;
    }
    /**
     * Private constructor.
     */
    private CustomerDataSource( String name ) {
        this.customers = new GenericEntityContainer<Customer>( name, Customer.class );
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
                persistenceProvider.loadInto( customers.getId(), entity -> {
                    this.customers.store( (Customer) entity );
                return true;
                });
            } catch( IOException e ) {
                System.out.print( ", " );
                System.err.print( "No data: " + customers.getId() );
            }
        }
    }

    @Override
    public void stop() {
        if (persistenceProvider != null) {
            persistenceProvider.save(customers, customers.getId());
        }
    }

    @Override
    public Customer findCustomerById(String id) {
        Customer c = customers.findById(id);
        return c;
    }

    @Override
        public Collection<Customer> findAllCustomers() { return customers.findAll();
        }
        @Override
        public Customer newCustomer(String firstname, String lastname) {
            Customer c = new Customer( null, firstname, lastname );
            customers.store( c );
            if (persistenceProvider != null) {
                persistenceProvider.save(customers, customers.getId());
            }
        return c;
        }

    @Override
    public void updateCustomer(Customer c) {
        String msg = "updated: ";
        customers.update(c);
        System.err.println(msg + c.getId());
        if (persistenceProvider != null) {
            persistenceProvider.save(customers, customers.getId());
        }
    }

    @Override
    public void deleteCustomers(Collection<String> ids) {
        customers.delete(ids);
        if (persistenceProvider != null) {
            persistenceProvider.save(customers, customers.getId());
        }
    }
}