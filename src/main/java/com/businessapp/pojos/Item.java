package com.businessapp.pojos;

import com.businessapp.logic.IDGen;

import java.util.ArrayList;
import java.util.List;

public class Item implements EntityIntf {

    private static IDGen IDG = new IDGen( "C.", IDGen.IDTYPE.AIRLINE, 6 );


    public enum ItemStatus { NEU, USED, BROKEN };

    //properties
    private String name = "";

    private String id = null;

    private int quantity = 0;

    private List<LogEntry> notes = new ArrayList<LogEntry>();

    private ItemStatus status = ItemStatus.NEU;


    public Item(String name){
        this.id = id==null? IDG.nextId() : id;
        this.name = name;
        this.notes.add( new LogEntry( "Item created." ) );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<LogEntry> getNotes() {
        return notes;
    }

    public Item setNotes(String note){
        this.notes.add(new LogEntry(note));
        return this;
    }

    public Item setName(String name){
        this.name = name;
        return this;
    }

    public Item setQuantity(int q){
        this.quantity = q;
        return this;
    }

    public Item setStatus(ItemStatus status){
        this.status = status;
        return this;
    }
}

