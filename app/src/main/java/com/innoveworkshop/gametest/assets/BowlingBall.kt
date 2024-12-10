package com.innoveworkshop.gametest.assets

import android.annotation.SuppressLint
import android.util.Log
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.Vector
import kotlin.math.absoluteValue
import kotlin.math.log
import kotlin.math.sqrt

class BowlingBall(
    x: Float,
    y: Float,
    radius: Float,
    color: Int,
    var mass: Float
) : Circle(x, y, radius, color) {
    var velocity: Vector = Vector(0f, 0f) // Ball's current velocity
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

        //Log.e("PHYSICS", "Position: (${position.x}, ${position.y}), Velocity: (${velocity.x}, ${velocity.y})")
    }

    fun applyForce(force: Vector) {
        // Acceleration = Force / Mass
        val acceleration = Vector(force.x / mass, force.y / mass)

        velocity.x += acceleration.x
        velocity.y += acceleration.y
    }


    @SuppressLint("DefaultLocale")
    fun CollideWithPin(circleCollided: Pin): Boolean {

        val radiusSum = (this.radius + circleCollided.radius).toDouble();

        val distanceXsquared: Double;
        distanceXsquared = Math.pow((circleCollided.position.x - this.position.x).toDouble(), 2.toDouble());
        var directionX: Float
        directionX = (this.position.x - circleCollided.position.x);

        var distanceYsquared: Double;
        distanceYsquared = Math.pow((circleCollided.position.y - this.position.y).toDouble(), 2.toDouble())
        var directionY: Float
        directionY = (this.position.y - circleCollided.position.y);

        val magnitude: Double;
        magnitude = sqrt(distanceXsquared + distanceYsquared)



        if (magnitude <= radiusSum){
            BounceCalculator(directionX, directionY)
            circleCollided.destroy()
            return true
        }
        else
            return false
    }

    @SuppressLint("DefaultLocale")
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
