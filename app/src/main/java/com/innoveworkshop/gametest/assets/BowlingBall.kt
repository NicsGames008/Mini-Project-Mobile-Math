package com.innoveworkshop.gametest.assets

import android.annotation.SuppressLint
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.Vector
import java.lang.Math.*
import kotlin.math.absoluteValue
import kotlin.math.sqrt

class BowlingBall(
    x: Float,
    y: Float,
    radius: Float,
    color: Int,
    var mass: Float
) : Circle(x, y, radius, color) {
    var velocity: Vector = Vector(0f, 0f) // Ball's current velocity
    var score: Int = 0;
    private val friction: Float = 0.98f // Friction coefficient

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
    }

    fun applyForce(force: Vector) {
        // Acceleration = Force / Mass
        val acceleration = Vector(force.x / mass, force.y / mass)

        velocity.x += acceleration.x
        velocity.y += acceleration.y
    }


    fun CollideWithPin(circleCollided: Pin): Boolean {
        val radiusSum = (this.radius + circleCollided.radius).toDouble()

        val distanceXsquared: Double = pow((circleCollided.position.x - this.position.x).toDouble(), 2.toDouble());
        val directionX: Float = (this.position.x - circleCollided.position.x)

        val distanceYsquared: Double = pow((circleCollided.position.y - this.position.y).toDouble(), 2.toDouble())
        val directionY: Float = (this.position.y - circleCollided.position.y)

        val magnitude: Double = sqrt(distanceXsquared + distanceYsquared)

        if (magnitude <= radiusSum){
            BounceCalculator(directionX, directionY)
            score++
            circleCollided.destroy()
            return true
        }
        else
            return false
    }

        private fun BounceCalculator(directionX: Float, directionY: Float) {
        // Define a scaling factor for the velocity reduction
        val reductionFactor = 0.85f // 10% reduction in velocity

        // Calculate the magnitude of the direction vector
        val magnitude = sqrt((directionX * directionX + directionY * directionY).toDouble()).toFloat()

        if (magnitude != 0f) {
            // Normalize the direction vector
            val normalizedX = directionX / magnitude
            val normalizedY = directionY / magnitude

            // Adjust the velocity by applying the normalized direction and reduction factor
            velocity.x *= reductionFactor
            velocity.y *= reductionFactor

            // Apply the bounce by adding a portion of the normalized direction to velocity
            velocity.x += normalizedX * reductionFactor
            velocity.y += normalizedY * reductionFactor
        }
    }

}
