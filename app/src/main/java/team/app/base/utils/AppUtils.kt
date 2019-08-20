package team.app.base.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import team.app.base.R

/**
 * Add Fragment to host
 * @param containerId layoutId host fragment will show
 * @param fragment target fragment
 * @param fromBottom animation from bottom (slide up) otherwise fragment slide from right to left
 * @param isAddToBackStack check whether fragment add to back stack
 */
fun AppCompatActivity.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    fromBottom: Boolean = false,
    isAddToBackStack: Boolean = true
) {
    try {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (fromBottom) {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_bottom,
                R.anim.exit_to_top,
                R.anim.enter_from_top,
                R.anim.exit_to_bottom
            )
        } else {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
        }

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.name)

        fragmentTransaction.add(containerId, fragment, fragment::class.java.name)
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Add Fragment to host
 * @param containerId layoutId host fragment will show
 * @param fragment target fragment
 * @param fromBottom animation from bottom (slide up) otherwise fragment slide from right to left
 * @param isAddToBackStack check whether fragment add to back stack
 */
fun AppCompatActivity.replaceFragment(
    @IdRes containerId: Int, fragment: Fragment,
    fromBottom: Boolean = false,
    isAddToBackStack: Boolean = true,
    isEnableAnimation: Boolean = true
) {
    try {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isEnableAnimation) {
            if (fromBottom) {
                fragmentTransaction.setCustomAnimations(
                    R.anim.enter_from_bottom,
                    R.anim.exit_to_top,
                    R.anim.enter_from_top,
                    R.anim.exit_to_bottom
                )
            } else {
                fragmentTransaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
            }
        }

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.name)
        fragmentTransaction.replace(containerId, fragment, fragment::class.java.name)
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Add Fragment to host
 * @param containerId layoutId host fragment will show
 * @param fragment target fragment
 * @param fromBottom animation from bottom (slide up)
 * @param isAddToBackStack check whether fragment add to back stack
 */
fun Fragment.addFragment(
    @IdRes containerId: Int, fragment: Fragment,
    fromBottom: Boolean = false,
    isAddToBackStack: Boolean = true,
    addFromActivity: Boolean = false
) {
    try {
        val fm = if (addFromActivity) (activity as AppCompatActivity).supportFragmentManager else childFragmentManager
        val fragmentTransaction = fm.beginTransaction()

        if (fromBottom) {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_bottom,
                R.anim.exit_to_top,
                R.anim.enter_from_top,
                R.anim.exit_to_bottom
            )
        } else {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
        }

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.name)

        fragmentTransaction.add(containerId, fragment, fragment::class.java.name)
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Add Fragment to host
 * @param containerId layoutId host fragment will show
 * @param fragment target fragment
 * @param fromBottom animation from bottom (slide up)
 * @param isAddToBackStack check whether fragment add to back stack
 * @param addFromActivity fragment add to activity
 */
fun Fragment.replaceFragment(
    @IdRes containerId: Int, fragment: Fragment,
    fromBottom: Boolean = false,
    isAddToBackStack: Boolean = true,
    addFromActivity: Boolean = false
) {
    try {
        val fm = if (addFromActivity) (activity as AppCompatActivity).supportFragmentManager else childFragmentManager
        val fragmentTransaction = fm.beginTransaction()

        if (fromBottom) {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_bottom,
                R.anim.exit_to_top,
                R.anim.enter_from_top,
                R.anim.exit_to_bottom
            )
        } else {
            fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
        }

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.name)


        fragmentTransaction.replace(containerId, fragment, fragment::class.java.name)
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 *
 * Find & Check Fragment Visible by TAG
 *
 * */
fun FragmentManager.isFragmentVisible(tag: String): Boolean {
    return this.findFragmentByTag(tag)?.isVisible == true
}

fun <T> jsonToObject(json: String, `class`: Class<T>): T {
    return Gson().fromJson(json, `class`)
}

fun <T> jsonToListObject(json : String, type: TypeToken<T>): T {
    return Gson().fromJson(json, type.type)
}

fun <T> objectToJson(t: T): String {
    return Gson().toJson(t)
}
