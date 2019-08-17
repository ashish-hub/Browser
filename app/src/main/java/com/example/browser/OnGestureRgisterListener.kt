package com.example.browser

import android.content.Context
import android.view.MotionEvent
import android.view.GestureDetector
import android.view.View
import android.view.View.OnTouchListener
import com.example.browser.OnGestureRegisterListener.Companion.SWIPE_THRESHOLD
import com.example.browser.OnGestureRegisterListener.Companion.SWIPE_VELOCITY_THRESHOLD


abstract class OnGestureRegisterListener(context: Context) : View.OnTouchListener {

    companion object {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }


    private val gestureDetector: GestureDetector
    private var view: View? = null

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        this.view = view
        return gestureDetector.onTouchEvent(event)
    }

    abstract fun onSwipeRight(view: View?)
    abstract fun onSwipeLeft(view: View?)
    abstract fun onSwipeBottom(view: View?)
    abstract fun onSwipeTop(view: View?)
    abstract fun onClick(view: View?,e: MotionEvent)
    abstract fun onLongClick(view: View?): Boolean

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            onLongClick(view)
            super.onLongPress(e)
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onClick(view, e)
            return super.onSingleTapUp(e)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(view)
                        } else {
                            onSwipeLeft(view)
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom(view)
                    } else {
                        onSwipeTop(view)
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }


    }
}