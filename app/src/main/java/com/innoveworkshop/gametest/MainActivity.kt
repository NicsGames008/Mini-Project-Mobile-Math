package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.DroppingCircle
import com.innoveworkshop.gametest.assets.DroppingRectangle
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var downButton: Button? = null
    protected var leftButton: Button? = null
    protected var rightButton: Button? = null

    protected var game: Game? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        gameSurface!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> Log.e("ACTION DOWN", "this shits stinks correctly")// touoch start
                    MotionEvent.ACTION_UP -> Log.e("ACTION UP", "aaaaaaaaaaaaaaaaaaaaaaaa")// touoch ended
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
        game = Game()
        gameSurface!!.setRootGameObject(game)

        setupControls()
    }

    private fun setupControls() {
        upButton = findViewById<View>(R.id.up_button) as Button
        upButton!!.setOnClickListener { game!!.circle!!.position.y -= 10f }

        downButton = findViewById<View>(R.id.down_button) as Button
        downButton!!.setOnClickListener { game!!.circle!!.position.y += 10f }

        leftButton = findViewById<View>(R.id.left_button) as Button
        leftButton!!.setOnClickListener { game!!.circle!!.position.x -= 10f }

        rightButton = findViewById<View>(R.id.right_button) as Button
        rightButton!!.setOnClickListener { game!!.circle!!.position.x += 10f }
    }

    inner class Game : GameObject() {
        var circle: Circle? = null

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            circle = Circle(
                (surface!!.width / 2).toFloat(),
                (surface.height / 2).toFloat(),
                100f,
                Color.RED
            )
            surface.addGameObject(circle!!)

            surface.addGameObject(
                Rectangle(
                    Vector((surface.width / 3).toFloat(), (surface.height / 3).toFloat()),
                    200f, 100f, Color.GREEN
                )
            )

            surface.addGameObject(
                DroppingRectangle(
                    Vector((surface.width / 3).toFloat(), (surface.height / 3).toFloat()),
                    100f, 100f, 10f, Color.rgb(0, 14, 80)
                )
            )

            surface.addGameObject(
                DroppingCircle(
                    surface.width / 4.toFloat(), surface.height / 4.toFloat(),
                    100f, 10f, Color.rgb(128, 14, 80)
                )
            )
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()

            if (!circle!!.isFloored && !circle!!.hitRightWall() && !circle!!.isDestroyed) {
                circle!!.setPosition(circle!!.position.x + 1, circle!!.position.y + 1)
            } else {
                circle!!.destroy()
            }
        }
    }
}