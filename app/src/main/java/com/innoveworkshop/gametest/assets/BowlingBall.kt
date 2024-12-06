package com.innoveworkshop.gametest.assets

import android.util.Log
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.Vector
import kotlin.math.absoluteValue

class BowlingBall(
    x: Float,
    y: Float,
    radius: Float,
    color: Int,
    var mass: Float
) : Circle(x, y, radius, color) {
    var velocity: Vector = Vector(0f, 0f) // Ball's current velocity
    private val friction: Float = 0.9f // Friction coefficient

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        // Apply velocity to position
        position.x += velocity.x
        position.y += velocity.y

        // Apply friction to reduce velocity
        velocity.x *= friction
        velocity.y *= friction

        // Stop the ball if velocity is very small
        if (velocity.x.absoluteValue < 0.1f) velocity.x = 0f
        if (velocity.y.absoluteValue < 0.1f) velocity.y = 0f

        Log.e("PHYSICS", "Position: (${position.x}, ${position.y}), Velocity: (${velocity.x}, ${velocity.y})")
    }

    fun applyForce(force: Vector, scaleFactor: Float = 0.1f) {
        // Acceleration = Force / Mass
        val acceleration = Vector(force.x / mass, force.y / mass)

        velocity.x += acceleration.x
        velocity.y += acceleration.y
    }
}
