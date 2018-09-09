package ua.codeasylum.topostsforomreddit.repository

interface Repository {
    fun getData(onGetDataResultCallback: OnGetDataResultCallback)
}