package team.app.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Show the soft input.
 *
 * @param activity The activity.
 */
fun showSoftInput(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}


/**
 * Show the soft input.
 *
 * @param view The view.
 */
fun showSoftInput(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()

    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}


/**
 * Hide the soft input.
 *
 * @param activity The activity.
 */
fun hideSoftInput(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) view = View(activity)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


/**
 * Hide the soft input.
 *
 * @param view The view.
 */
fun hideSoftInput(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


/**
 * Toggle the soft input display or not.
 */
fun toggleSoftInput(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}


fun Context.showKeyboard(view: View?) {
    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Context.hideKeyboard() {
    this as Activity
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

var isShowKeyboard = false
fun Context.keyboardListener(view: View, action: (isShow: Boolean) -> Unit) {
    view.viewTreeObserver.addOnGlobalLayoutListener {
        if (view.context == null) {
            return@addOnGlobalLayoutListener
        }
        val measureRect = Rect()
        view.getWindowVisibleDisplayFrame(measureRect)
        val keypadHeight = view.rootView.height - measureRect.bottom
        if (keypadHeight <= dp2px(150f)) {
            if (isShowKeyboard) {
                view.postDelayed({
                    view.context?.let {
                        action.invoke(false)
                        isShowKeyboard = false
                    }
                }, 300)

            }
        } else {
            if (!isShowKeyboard) {
                action.invoke(true)
            }
            isShowKeyboard = true
        }
    }
}
