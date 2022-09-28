package com.umldesigner.infrastructure.uml.data;

/**
 * this interface should define all android specific methods and should be implemented when creating
 * android specific data object
 *
 * @implSpec classes that implement this interface should implement field "id" preferably of type Integer, and it should
 * be independent of the "uuid" which is used for server side things.
 * for server calls clone method is called on the pojos and each pojo has to implement cloneable because of a oversight
 * made by me to not use composition in S*Data classes, if more problems are caused in the future rework may be required
 *
 * @implNote id is used instead of Uuid because a view uses int instead of string for the setId
 * method
 *
 * @implNote TODO the id generation can be improved with an annotation that that will be added at the class declaration,
 *              and it would generate id's automatically and possibly automate or improve other stuff as well
 */
public interface BaseDataInterface<T> {
    Object getId();

}
