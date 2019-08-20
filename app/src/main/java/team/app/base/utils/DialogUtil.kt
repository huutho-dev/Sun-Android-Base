package team.app.base.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

class DialogUtil {

    companion object {
        private var dialog: AlertDialog? = null

        fun dismiss() {
            dialog?.dismiss()
        }

        fun isShowing(): Boolean {
            return dialog?.isShowing ?: false
        }

        /* style dialogShowMessage
        * |-----------------------------|
        * |            title            |
        * |           message           |
        * |-----------------------------|
        * | textNegative | textPositive |
        * |-----------------------------|
        * */
        fun dialogShowMessage(
            context: Context,
            title: String? = null,
            message: String,
            textPositive: String,
            textNegative: String? = null,
            onClickPositive: (() -> Unit)? = null,
            onClickNegative: (() -> Unit)? = null,
            isGravityCenter: Boolean? = true,
            isCancelOnTouchOutside: Boolean = true,
            isBoldMessage: Boolean? = false
        ) {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside)
            alertDialog.setCancelable(isCancelOnTouchOutside)

            if (title.isNullOrEmpty()) {
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            } else {
                alertDialog.setTitle(
                    HtmlCompat.fromHtml(
                        "<b>$title</b>",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                )
            }

            alertDialog.setMessage(
                if (isBoldMessage == true)
                    HtmlCompat.fromHtml("<b>$message</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                else message
            )

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, textPositive
            ) { dialog, _ ->
                dialog.dismiss()
                onClickPositive?.invoke()
            }

            if (!textNegative.isNullOrEmpty()) {
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE, textNegative
                ) { dialog, _ ->
                    dialog.dismiss()
                    onClickNegative?.invoke()
                }
            }

            alertDialog.show()

            dialog = alertDialog

            if (isGravityCenter == true) {
                val messageView = alertDialog
                    .findViewById(android.R.id.message) as TextView
                messageView.gravity = Gravity.CENTER
                val titleView =
                    alertDialog.findViewById(
                        context.resources.getIdentifier("alertTitle", "id", "android")
                    ) as TextView
                titleView.gravity = Gravity.CENTER
                titleView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }

            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams

            if (!textNegative.isNullOrEmpty()) {
                val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                layoutParams.weight = 10f
                btnNegative.layoutParams = layoutParams
            } else {
                if (isGravityCenter == true) {
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                }
            }
            btnPositive.layoutParams = layoutParams
        }

        /* style dialogShowListMessage
         * |-----------------------------|
         * |           title             |
         * |           items[0]          |
         * |           items[1]          |
         * |           items[2]          |
         * |           .....             |
         * |-----------------------------|
         * | textNegative | textPositive |
         * |-----------------------------|
         * */
        fun dialogShowListMessage(
            context: Context,
            title: String? = null,
            items: Array<String>,
            textPositive: String,
            textNegative: String? = null,
            onClickPositive: (() -> Unit)? = null,
            onClickNegative: (() -> Unit)? = null,
            onClickItem: ((Int) -> Unit)? = null,
            isCancelOnTouchOutside: Boolean = true,
            isGravityCenter: Boolean? = true,
            isShowButton: Boolean? = true,
            titleTextSizeDimen: Int = 0,
            titleColor: Int = 0,
            itemChecked: Int = -1
        ) {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                if (!title.isNullOrEmpty()) {
                    setTitle(title)
                }
                if (isGravityCenter == true) {
                    val adapter = object : ArrayAdapter<String>(
                        this.context,
                        android.R.layout.simple_list_item_1, items
                    ) {
                        override fun getView(
                            position: Int,
                            convertView: View?,
                            parent: ViewGroup
                        ): View {
                            val view = super.getView(position, convertView, parent) as TextView
                            view.gravity = Gravity.CENTER
                            view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                            if (itemChecked != -1) {
                                view.setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        if (itemChecked == position) android.R.color.darker_gray else android.R.color.white
                                    )
                                )
                            }

                            return view
                        }
                    }
                    setSingleChoiceItems(adapter, -1) { _, which ->
                        dismiss()
                        onClickItem?.invoke(which)
                    }
                } else
                    setItems(items) { _, which ->
                        dismiss()
                        onClickItem?.invoke(which)
                    }

                setPositiveButton(textPositive) { _: DialogInterface, _: Int ->
                    onClickPositive?.invoke()
                }

                if (!textNegative.isNullOrBlank()) {
                    setNegativeButton(textNegative) { _: DialogInterface, _: Int ->
                        onClickNegative?.invoke()
                    }
                }
            }

            val alertDialog = builder.create()
            alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside)
            alertDialog.setCancelable(isCancelOnTouchOutside)
            alertDialog.show()
            dialog = alertDialog

            val titleView =
                alertDialog.findViewById(
                    context.resources.getIdentifier("alertTitle", "id", "android")
                ) as TextView

            if (isGravityCenter == true) {
                val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                layoutParams.gravity = Gravity.CENTER
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                if (isShowButton == false) {
                    layoutParams.height = 1
                }
                btnPositive.layoutParams = layoutParams
                val titleView = alertDialog.findViewById(
                    context.resources.getIdentifier("alertTitle", "id", "android")
                ) as TextView
                titleView.gravity = Gravity.CENTER
                titleView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }

            if (titleTextSizeDimen != 0) {
                titleView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(titleTextSizeDimen)
                )
            }

            if (titleColor != 0) {
                titleView.setTextColor(ContextCompat.getColor(context, titleColor))
            }
        }
    }
}
