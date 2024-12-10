package com.innoveworkshop.gametest.assets

import android.annotation.SuppressLint
import com.innoveworkshop.gametest.engine.Circle
import kotlin.math.sqrt

class Pin(
    x: Float,
    y: Float,
    radius: Float,
    color: Int
) : Circle(x, y, radius , color) {

    override fun onFixedUpdate() {
        super.onFixedUpdate()
    }

}
