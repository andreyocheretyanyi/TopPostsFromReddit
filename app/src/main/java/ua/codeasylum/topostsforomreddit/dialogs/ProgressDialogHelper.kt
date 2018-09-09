package ua.codeasylum.topostsforomreddit.dialogs

import android.app.ProgressDialog
import android.content.Context

object ProgressDialogHelper {

    private var mAlertDialog: ProgressDialog? = null

    fun show(context: Context?) {
        if (mAlertDialog == null && context != null) {
            mAlertDialog = ProgressDialog(context)
            mAlertDialog!!.setCancelable(false)
            mAlertDialog!!.show()
        }
    }

    fun dismiss() {
        if (mAlertDialog != null) {
            mAlertDialog!!.dismiss()
            mAlertDialog = null
        }
    }
}