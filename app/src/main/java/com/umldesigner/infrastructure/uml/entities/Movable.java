package com.umldesigner.infrastructure.uml.entities;

/**
 * defines that an object is movable, this is android specific and should not be added to the shared
 * directory
 *
 * @implNote this should use absolute position
 */
public interface Movable {
    void move(float x, float y);

}

