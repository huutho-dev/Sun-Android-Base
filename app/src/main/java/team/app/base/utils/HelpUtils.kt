package team.app.base.utils

class HelpUtils {
    companion object {
        private const val DELAY_TIME_CLICK = 800
        private var lastClickTime = 0L

        fun blockDoubleClick(): Boolean {
            if (System.currentTimeMillis() - lastClickTime < DELAY_TIME_CLICK) {
                return true
            }
            lastClickTime = System.currentTimeMillis()
            return false
        }
    }
}

