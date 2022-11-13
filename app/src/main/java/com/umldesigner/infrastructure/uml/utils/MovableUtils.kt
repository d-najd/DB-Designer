package com.umldesigner.infrastructure.uml.utils

import com.umldesigner.infrastructure.uml.entities.Movable
import com.umldesigner.infrastructure.uml.logic.app.SSettings
import java.lang.AssertionError

class MovableUtils private constructor() {
    // Noninstantiable utility class
    init {
        // Suppress default constructor for noninstantiablity
        throw AssertionError()
    }

    companion object {
        /**
         * moves uml view to given position using grid spaces
         * @param movable the object that we want to move
         * @param x position in grid spaces, not absolute
         * @param y position in grid spaces, not absolute
         * @implNote notifying that an object has been moved should be done in the view itself
         * @see .moveViewAbsolute
         */
        fun moveView(movable: Movable, x: Float, y: Float) {
            val spacedX = x * SSettings.getInstance().get
            val spacedY = y * SSettings.getInstance().spacing
            movable.move(spacedX, spacedY)
        }

        /**
         * moves uml view to a given absolute position
         * @param movable the object that we want to move
         * @param x absolute position
         * @param y absolute position
         * @implNote notifying that an object has been moved should be done in the view itself
         * @see .moveView
         */
        @JvmStatic
        fun moveViewAbsolute(movable: Movable, x: Float, y: Float) {
            movable.move(x, y)
        }
    }
}