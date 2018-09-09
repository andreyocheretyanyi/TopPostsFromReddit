package ua.codeasylum.topostsforomreddit.contracts

import ua.codeasylum.topostsforomreddit.models.TopPost

interface TopPostsContract {

    interface View : BaseContract.View {
        fun openSinglePost(url: String)
        fun onInsertNewData(startPosition : Int, count : Int)
        fun onInitPostRecyclerAdapter(postsArrayList: ArrayList<TopPost>)
        fun hidePaginationProgress()
        fun showPaginationProgress()

    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onPostItemClicked(position: Int)
        fun onRecyclerEnded()

    }

}
