package team.app.base.utils

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.setActive(isActive : Boolean = true) {
    visible()
    isClickable = isActive
    isFocusable = isActive
    alpha = if (isActive) 1f else 0.3f
}
/**
 * Set enable for all child view in ViewGroup
 */
fun ViewGroup.setEnable(enable : Boolean){
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        child.isEnabled = enable
        child.isClickable = enable
    }

    this.alpha = if (enable) 1f else 0.5f
}

fun View.setEnableViewAlpha(enable: Boolean) {
    this.alpha = if (enable) 1f else 0.4f
    this.isClickable = enable
    this.isFocusable = enable
    this.isEnabled = enable
}


fun View.setOnAlphaClickListener(onClick: (view: View) -> Unit) {
    setOnClickListener {
        it.animate()
            .alphaBy(1f)
            .alpha(0.2f)
            .setDuration(100)
            .withEndAction {
                it.animate()
                    .alphaBy(0.2f)
                    .alpha(1f)
                    .setDuration(100)
                    .withEndAction { onClick.invoke(it) }
                    .start()
            }
            .start()
    }
}

fun View.alphaClickListener(onClick: (view: View) -> Unit){
    this.animate()
        .alphaBy(1f)
        .alpha(0.2f)
        .setDuration(100)
        .withEndAction {
            this.animate()
                .alphaBy(0.2f)
                .alpha(1f)
                .setDuration(100)
                .withEndAction { onClick.invoke(this) }
                .start()
        }
        .start()
}

/**
 * Value of dp to value of px.
 *
 * @param dpValue The value of dp.
 * @return value of px
 */
fun dp2px(dpValue: Float): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


/**
 * Value of px to value of dp.
 *
 * @param pxValue The value of px.
 * @return value of dp
 */
fun px2dp(pxValue: Float): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * Value of dp to value of px.
 *
 * @param dpValue The value of dp.
 * @return value of px
 */
fun Int.toPx(): Float =  (this * Resources.getSystem().displayMetrics.density)


/**
 * Value of sp to value of px.
 *
 * @param spValue The value of sp.
 * @return value of px
 */
fun sp2px(spValue: Float): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * Value of px to value of sp.
 *
 * @param pxValue The value of px.
 * @return value of sp
 */
fun px2sp(pxValue: Float): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}


/**
 * Force get the size of view.
 *
 * e.g.
 * <pre>
 * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
 * Override
 * public void onGetSize(final View view) {
 * view.getWidth();
 * }
 * });
</pre> *
 *
 * @param view     The view.
 * @param listener The get size listener.
 */
fun View.forceGetViewSize(listener: (view: View) -> Unit) {
    post { listener.invoke(this) }
}


/**
 * Return the width of view.
 *
 * @param view The view.
 * @return the width of view
 */
fun getMeasuredWidth(view: View): Int {
    return measureView(view)[0]
}

/**
 * Return the height of view.
 *
 * @param view The view.
 * @return the height of view
 */
fun getMeasuredHeight(view: View): Int {
    return measureView(view)[1]
}


/**
 * Measure the view.
 *
 * @param view The view.
 * @return arr[0]: view's width, arr[1]: view's height
 */
private fun measureView(view: View): IntArray {
    var lp: ViewGroup.LayoutParams? = view.layoutParams
    if (lp == null) {
        lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
    val lpHeight = lp.height
    val heightSpec: Int
    if (lpHeight > 0) {
        heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
    } else {
        heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    }
    view.measure(widthSpec, heightSpec)
    return intArrayOf(view.measuredWidth, view.measuredHeight)
}


private fun getStatusBarHeight(): Int {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

private fun getNavBarHeight(): Int {
    val res = Resources.getSystem()
    val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId != 0) {
        res.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun getXLocationOnScreen(view: View): Int {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    return location[0]
}

fun View.setLockOnClickListener(onClick: () -> Unit) {
    this.setOnClickListener {
        if (HelpUtils.blockDoubleClick()) return@setOnClickListener
        onClick.invoke()
    }
}


fun View.waitForLayout(f: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}