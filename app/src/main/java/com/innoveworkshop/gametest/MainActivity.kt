package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.innoveworkshop.gametest.assets.BowlingBall
import com.innoveworkshop.gametest.assets.Pin
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Vector
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var gameSurface: GameSurface? = null
    private var controlsLayout: ConstraintLayout? = null
    private var game: Game? = null
    private var bowlingBall: BowlingBall? = null
    private var initialTouch: Vector? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)

        controlsLayout = findViewById<View>(R.id.controls_layout) as ConstraintLayout
        controlsLayout!!.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialTouch = Vector(event.x, event.y)
                    Log.e("ACTION DOWN", "Touch started at: (${event.x}, ${event.y})")
                }
                MotionEvent.ACTION_UP -> {
                    if (initialTouch != null) {
                        val releaseTouch = Vector(event.x, event.y)
                        val forceVector = Vector(
                            releaseTouch.x - initialTouch!!.x,
                            releaseTouch.y - initialTouch!!.y
                        )
                        Log.e("FORCE VECTOR", "Applying force: (${forceVector.x}, ${forceVector.y})")

                        // Reverse the force vector
                        val reversedForce = forceVector.reverse()
                        Log.e("REVERSED VECTOR", "Reversed force: (${reversedForce.x}, ${reversedForce.y})")

                        // Apply the reversed force to the circle
                        bowlingBall?.applyForce(reversedForce, scaleFactor = 0f)
                    }
                }
            }
            true
        }
    }

    inner class Game : GameObject() {
        private var pins: MutableList<Pin?>? = null
        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            // Create the circle and store it in the global variable
            bowlingBall = BowlingBall(
                (surface!!.width.toFloat())/2f, // Center horizontally
                surface.height.toFloat()-100f, // Bottom of the screen, considering the radius
                100f, // Radius of the ball
                Color.rgb(128, 14, 80),
                10f
            )
            // Add the circle to the surface
            surface.addGameObject(bowlingBall!!)

            pins = Obstacles(surface);


        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()


            val iterator = pins!!.iterator()
            while (iterator.hasNext()) {
                val pin = iterator.next()
                val a = bowlingBall!!.CollideWithPin(pin!!)
                if (a) {
                    iterator.remove() // Safely remove the pin
                }
            }
        }
    }


    fun Obstacles(surface: GameSurface?): MutableList<Pin?> {
        val listOfObstacles = mutableListOf<Pin?>() // Create a mutable list of Circle objects
        var line = 1
        var ballsInLine = 0
        val surfaceWidth = surface!!.width.toFloat()
        val d = 100 // Distance between pins
        val initialY = surface.height.toFloat() * (1 / 4f) // Start from the bottom (1/4 of the screen height)

        while (line <= 4) {
            // Adjust the initial X position for centering each row
            val initialX = (surfaceWidth - ((line - 1) * d)) / 2

            while (ballsInLine < line) {
                // Calculate the X position as before
                val ballX = initialX + (ballsInLine * d)
                // Reverse the Y position by subtracting the row offset from the initial Y
                val ballY = initialY - (sqrt((d * d - ((d / 2) * (d / 2))).toDouble()) * line).toFloat()

                // Create a new Pin object
                val pin = Pin(
                    ballX,
                    ballY,
                    30f, // Radius of the pin
                    Color.GREEN // Pin color
                )

                // Add the Pin to the list and to the GameSurface
                listOfObstacles.add(pin)
                surface.addGameObject(pin)

                ballsInLine++
            }
            ballsInLine = 0 // Reset the ballsInLine for the next row
            line++
        }
        return listOfObstacles
    }



}