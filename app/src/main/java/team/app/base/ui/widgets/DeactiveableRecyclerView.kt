package team.app.base.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class DeactiveableRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun isClickable(): Boolean {
        return isEnabled
    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        if (isEnabled) super.performClick()
    }

    override fun performClick(): Boolean {
        return if (isEnabled) super.performClick() else false
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return if (isEnabled) super.onInterceptTouchEvent(e) else false
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return if (isEnabled) super.onTouchEvent(e) else false
    }
}