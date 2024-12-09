package com.innoveworkshop.gametest.assets

import com.innoveworkshop.gametest.engine.Circle

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
