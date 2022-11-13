package com.umldesigner.infrastructure.uml.logic.api;

public class Endpoints {
    // Noninstantiable class
    private Endpoints(){
        // Suppress default constructor for noninsatiability
        throw new AssertionError();
    }
    /**
     * endpoint for all schema related stuff
     */
    public static final String SCHEMA = "/s";
    /**
     * Project endpoint which doesn't contain {@value #SCHEMA} before it
     */
    public static final String PROJECT_RAW = "/project";
    /**
     * Project endpoint which comtains {@value #SCHEMA} and {@value #PROJECT_RAW}
     */
    public static final String PROJECT = SCHEMA + "/project";
    /**
     * Table endpoint which doesn't contain {@value #SCHEMA} before it
     */
    public static final String TABLE_RAW = "/table";
    /**
     * Table endpoint which contains {@value #PROJECT} and {@value #TABLE_RAW}
     */
    public static final String TABLE = PROJECT + TABLE_RAW;
    /**
     * Item endpoint which doesn't contain {@value #PROJECT} before it
     */
    public static final String ITEM_RAW = "/item";
    /**
     * Item endpoint which contains {@value #PROJECT} and {@value #ITEM_RAW}
     */
    public static final String ITEM = PROJECT + ITEM_RAW;
    /**
     * The info for the item like if it is a primary key and other stuff
     */
    public static final String ITEM_INFO = ITEM + "/info";
    /**
     * Foreign key between SItems endpoint contains {@value #ITEM} before it
     */
    public static final String ITEM_FK = ITEM + "/foreignKey";
}

