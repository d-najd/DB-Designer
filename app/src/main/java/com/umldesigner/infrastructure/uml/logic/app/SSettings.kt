package com.umldesigner.infrastructure.uml.logic.app

import android.util.Log
import com.umldesigner.MainActivity
import com.umldesigner.infrastructure.uml.error.ErrorTags
import com.umldesigner.infrastructure.uml.logic.app.SSettings
import lombok.Getter

/**
 * singleton which holds stuff like sizes of the tables, elevations etc
 * singleton because we need to get dp from the main activity to calculate sizes
 * @see com.umldesigner.infrastructure.uml.utils.SUtils
 */
@Getter
class SSettings private constructor() {
    private val dp: Float

    @Getter
    private val spacing: Float

    //endregion
    //region sizes
    @Getter
    val tableWidth: Float

    //endregion
    init {
        Log.d("Execute", "Create Schema Settings Singleton")
        spacing = MainActivity.spacing
        dp = MainActivity.dp
        tableWidth = spacing * 9
        if (spacing == 0f || dp == 0f || tableWidth == 0f) {
            Log.e(
                ErrorTags.APP_ERROR, "Invalid spacings set, make sure that the setup() method" +
                        "inside main activity is called before SSettings gets called"
            )
        }
    }

    companion object {
        var instance: SSettings? = null
            get() {
                if (field == null) {
                    field = SSettings()
                }
                return field
            }
            private set

        //region elevations
        const val TABLE_ELEVATION = 0.5f
        const val ARROW_HEAD_ELEVATION = 0.12f
        const val ARROW_BACK_ELEVATION = 0.11f
        const val ARROW_BODY_ELEVATION = 0.10f
    }
}