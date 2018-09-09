package ua.codeasylum.topostsforomreddit.views

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_posts_top.*
import ua.codeasylum.topostsforomreddit.R
import ua.codeasylum.topostsforomreddit.adapters.TopPostsAdapter
import ua.codeasylum.topostsforomreddit.contracts.TopPostsContract
import ua.codeasylum.topostsforomreddit.dialogs.ProgressDialogHelper
import ua.codeasylum.topostsforomreddit.models.TopPost
import ua.codeasylum.topostsforomreddit.presenters.TopPostsPresenter

class TopPostsActivity : AppCompatActivity(), TopPostsContract.View {


    private lateinit var presenter: TopPostsContract.Presenter
    private lateinit var topPostsAdapter: TopPostsAdapter
    private var isLoadInProgress = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_top)
        presenter = TopPostsPresenter()
        presenter.onAttachView(this)
        rv_top_posts.layoutManager = LinearLayoutManager(this)
    }

    override fun onInitPostRecyclerAdapter(postsArrayList: ArrayList<TopPost>) {
        topPostsAdapter = TopPostsAdapter(this, postsArrayList, { post, position ->
            presenter.onPostItemClicked(position)
        })
        rv_top_posts.adapter = topPostsAdapter
        rv_top_posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 && (rv_top_posts.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() >= topPostsAdapter.itemCount - 1 && !isLoadInProgress) {
                    isLoadInProgress = true
                    presenter.onRecyclerEnded()
                }
            }
        })
    }


    override fun onInsertNewData(startPosition: Int, count: Int) {
        topPostsAdapter.notifyItemRangeInserted(startPosition, count)
    }

    override fun showToast(stringRes: Int) {
        toast(stringRes)
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun showGlobalProgressProgress() {
        isLoadInProgress = true
        ProgressDialogHelper.show(this)

    }

    override fun hideGlobalProgress() {
        isLoadInProgress = false
        ProgressDialogHelper.dismiss()

    }

    override fun showPaginationProgress() {
        isLoadInProgress = true
        topPostsAdapter.showProgressBar(true)
    }

    override fun hidePaginationProgress() {
        isLoadInProgress = false
        topPostsAdapter.showProgressBar(false)
    }


    override fun openSinglePost(url: String) {
        CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url))
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }

    fun Context.toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun Context.toast(stringId: Int) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show()
    }
}
