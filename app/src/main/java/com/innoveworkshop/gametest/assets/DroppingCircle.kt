package com.innoveworkshop.gametest.assets

import com.innoveworkshop.gametest.engine.Circle

class DroppingCircle(
    x: Float,
    y: Float,
    radius: Float,
    dropRate: Float,
    color: Int
) : Circle(x, y, radius , color) {
    var dropRate: Float = 0f

    init {
        this.dropRate = dropRate
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        if (!isFloored) position.y += dropRate
    }
}
