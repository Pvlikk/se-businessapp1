package com.businessapp.logic;

import java.util.Collection;
import java.util.HashMap;

import com.businessapp.Component;
import com.businessapp.ControllerIntf;
import com.businessapp.pojos.Item;


/**
 * Implementation of Customer data.
 *
 */
class ItemDataMockImpl implements ItemDataIntf {

    private final HashMap<String,Item> _data;	// HashMap as data container
    private final ItemDataIntf DS;				// Data Source/Data Store Intf
    private Component parent;						// parent component

    /**
     * Constructor.
     */
    ItemDataMockImpl() {
        this._data = new HashMap<String,Item>();
        this.DS = this;
    }

    /**
     * Dependency injection methods.
     */
    @Override
    public void inject( ControllerIntf dep ) {
    }

    @Override
    public void inject( Component parent ) {
        this.parent = parent;
    }

    /**
     * Start.
     */
    @Override
    public void start() {

        String name = parent.getName();
        if( name.equals( "Katalog" ) ) {
            // Items list 1
            Item piano = DS.newItem("Piano Casio CTK-3500");
            piano.setStatus(Item.ItemStatus.USED);
            piano.setNotes("Anmerkung");
            piano.setQuantity(3);

            Item gitarre_orcestral = DS.newItem("Gitarre orcestral");
            gitarre_orcestral.setStatus(Item.ItemStatus.USED);
            gitarre_orcestral.setNotes("Geile Gitarre");
            gitarre_orcestral.setQuantity(1);

            Item midi = DS.newItem("MIDI MPK-mini AKAI");
            midi.setStatus(Item.ItemStatus.NEU);
            midi.setNotes("Anmerkung");
            midi.setQuantity(4);

            Item lautsprecher = DS.newItem("Lautsprecher Pioneer ST2500");
            lautsprecher.setStatus(Item.ItemStatus.USED);
            lautsprecher.setNotes("Doppeltseitig");
            lautsprecher.setQuantity(2);

            Item piano_yamaha = DS.newItem("Piano Yamaha T1000");
            piano_yamaha.setStatus(Item.ItemStatus.BROKEN);
            piano_yamaha.setNotes("Anmerkung");
            piano_yamaha.setQuantity(1);

            Item bassGitarre = DS.newItem("Gitarre Bass");
            bassGitarre.setStatus(Item.ItemStatus.USED);
            bassGitarre.setNotes("Anmerkung");
            bassGitarre.setQuantity(5);

            Item geige = DS.newItem("Geige");
            geige.setStatus(Item.ItemStatus.BROKEN);
            geige.setNotes("Anmerkung");
            geige.setQuantity(1);

            Item synth = DS.newItem("Syntethizer Yamaha F550");
            synth.setStatus(Item.ItemStatus.NEU);
            synth.setNotes("Anmerkung");
            synth.setQuantity(2);

            Item mixer = DS.newItem("Mixer PIONEER FST30");
            mixer.setStatus(Item.ItemStatus.USED);
            mixer.setNotes("Anmerkung");
            mixer.setQuantity(3);

            Item hulle = DS.newItem("Hülle für Gitarre");
            hulle.setStatus(Item.ItemStatus.USED);
            hulle.setNotes("Anmerkung");
            hulle.setQuantity(30);
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public Item findItemById(String id) {
        return _data.get(id);
    }

    @Override
    public Collection<Item> findAllItems() {
        return _data.values();
    }

    @Override
    public Item newItem( String name) {
        Item i = new Item(name);
        _data.put(i.getId(), i);
        return i;
    }

    @Override
    public void updateItem(Item i) {
        String msg = "updated: ";
        if( i != null ) {
            Item i2 = _data.get( i.getId() );
            if( i != i2 ) {
                if( i2 != null ) {
                    _data.remove( i2.getId() );
                }
                msg = "created: ";
                _data.put( i.getId(), i );
            }
            //save( msg, i );
            System.err.println( msg + i.getId() );
        }
    }

    @Override
    public void deleteItems(Collection<String> ids) {
        String showids = "";
        for( String id : ids ) {
            _data.remove( id );
            showids += ( showids.length()==0? "" : ", " ) + id;
        }
        if( ids.size() > 0 ) {
            //save( "deleted: " + idx, customers );
            System.err.println( "deleted: " + showids );
        }
    }


}
