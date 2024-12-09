package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.innoveworkshop.gametest.assets.BowlingBall
import com.innoveworkshop.gametest.assets.DroppingCircle
import com.innoveworkshop.gametest.assets.DroppingRectangle
import com.innoveworkshop.gametest.assets.Pin
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class MainActivity : AppCompatActivity() {
    private var gameSurface: GameSurface? = null
    private var controlsLayout: ConstraintLayout? = null
    private var game: Game? = null
    private var bowlingBall: BowlingBall? = null
    private var pin: Pin? = null
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
            surface?.addGameObject(bowlingBall!!)

            pin = Pin(
                (surface!!.width.toFloat())/2f,
                (surface.height.toFloat())/2f,
                75f,
                Color.rgb(0,255,0)
            )

            surface?.addGameObject(pin!!)

        }
    }


}