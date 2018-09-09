package ua.codeasylum.topostsforomreddit.repository

import ua.codeasylum.topostsforomreddit.models.TopPost

interface OnGetDataResultCallback {
    fun onSuccess(resultArrayList: ArrayList<TopPost>)

    fun onError(message: String)
}