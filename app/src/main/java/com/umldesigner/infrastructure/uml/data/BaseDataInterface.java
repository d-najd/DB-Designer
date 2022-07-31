package com.umldesigner.infrastructure.uml.data;

/**
 * base interface for all android specific data objects
 *
 * this interface should define all android specific methods and should be implemented when creating
 * android specific data object
 *
 * @implNote Id is used instead of Uuid because a view uses int instead of string for the setId
 * method
 */
public interface BaseDataInterface {
    public Integer getId();
}
