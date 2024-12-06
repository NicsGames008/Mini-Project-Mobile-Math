package com.innoveworkshop.gametest.engine


class Vector(@JvmField var x: Float, @JvmField var y: Float) {
    fun reverse(): Vector {
        return Vector(-x, -y)
    }
}
