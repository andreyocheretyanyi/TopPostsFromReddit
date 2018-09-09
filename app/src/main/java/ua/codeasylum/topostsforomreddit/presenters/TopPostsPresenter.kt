package ua.codeasylum.topostsforomreddit.presenters

import ua.codeasylum.topostsforomreddit.api.BASE_URL
import ua.codeasylum.topostsforomreddit.api.RetrofitManager
import ua.codeasylum.topostsforomreddit.contracts.TopPostsContract
import ua.codeasylum.topostsforomreddit.models.TopPost
import ua.codeasylum.topostsforomreddit.repository.OnGetDataResultCallback
import ua.codeasylum.topostsforomreddit.repository.TopPostsRepository

class TopPostsPresenter : TopPostsContract.Presenter, OnGetDataResultCallback {

    private val postsArrayList = ArrayList<TopPost>()


    private lateinit var repository: TopPostsRepository
    private var view: TopPostsContract.View? = null
    private var isFirsInit = true


    override fun onAttachView(view: TopPostsContract.View) {
        repository = TopPostsRepository(RetrofitManager.getClient()!!)
        this.view = view
        this.view?.onInitPostRecyclerAdapter(postsArrayList)
        makeGetTopRequest()
    }

    override fun onError(message: String) {
        choiceDialogForClose()
        view?.showToast(message)

    }

    override fun onSuccess(resultArrayList: ArrayList<TopPost>) {
        choiceDialogForClose()
        val startPosition = postsArrayList.size
        postsArrayList.addAll(resultArrayList)
        view?.onInsertNewData(startPosition, resultArrayList.size)
    }


    override fun onPostItemClicked(position: Int) {
        view?.openSinglePost(getUrlByPosition(position))

    }

    override fun onRecyclerEnded() {
        makeGetTopRequest()
    }


    override fun onDetachView() {
        view = null
    }


    private fun getUrlByPosition(position: Int): String = "$BASE_URL${postsArrayList[position].topPostData.permalink}"

    private fun makeGetTopRequest() {
        if (isFirsInit) {
            view?.showGlobalProgressProgress()
        } else {
            view?.showPaginationProgress()
        }
        repository.getData(this)

    }

    private fun choiceDialogForClose() {
        if (isFirsInit) {
            isFirsInit = false
            view?.hideGlobalProgress()
        } else {
            view?.hidePaginationProgress()
        }
    }


}