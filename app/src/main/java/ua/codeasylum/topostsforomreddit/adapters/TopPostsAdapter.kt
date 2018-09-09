package ua.codeasylum.topostsforomreddit.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.view.*
import ua.codeasylum.topostsforomreddit.R
import ua.codeasylum.topostsforomreddit.models.TopPost
import java.text.DecimalFormat
import java.util.*

private const val THOUSAND = 1000
private const val TYPE_POST = 0
private const val TYPE_LOADER = 1

class TopPostsAdapter(private val context: Context, private val posts: MutableList<TopPost>, private val onTopPostItemClick: (post: TopPost, position: Int) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var loaderCountFlag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = if (viewType == TYPE_POST)
        TopPostsAdapterViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_post, parent, false))
    else {
        ProgressViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.item_progress, parent, false)
        )

    }


    override fun getItemCount(): Int = posts.size + loaderCountFlag

    override fun getItemViewType(position: Int): Int {
        return if (position > posts.lastIndex) {
            TYPE_LOADER
        } else {
            TYPE_POST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TopPostsAdapterViewHolder) {
            val postData = posts[position].topPostData
            with(holder.itemView) {
                setOnClickListener {
                    onTopPostItemClick.invoke(posts[position], position)
                }
                tv_subreddit.text = postData.subredditNamePrefixed
                tv_author_time.text = String.format(context.getString(R.string.author_and_time), postData.author, getCountOfHoursSincePostWasCreated(postData.createdUtc))
                tv_title.text = postData.title
                Picasso.with(context).load(postData.thumbnail).into(iv_post_image)
                tv_rating.text = String.format(context.getString(R.string.rating), formatToThousand(postData.ups))
                tv_comments.text = if (postData.numComments > THOUSAND)
                    String.format(context.getString(R.string.comments_more_thousand), formatToThousand(postData.numComments))
                else
                    String.format(context.getString(R.string.comments_less_thousand), postData.numComments)
            }
        }

    }


    private fun getCountOfHoursSincePostWasCreated(createdAt: Long): Int {
        return ((Calendar.getInstance(TimeZone.getDefault()).timeInMillis / 1000L - createdAt) / 3600).toInt()
    }

    private fun formatToThousand(number: Int): String {
        val beautyRating = number.toDouble() / THOUSAND
        if (beautyRating >= 1)
            return DecimalFormat("##.#").format(beautyRating)
        else return number.toString()

    }

    fun showProgressBar(show: Boolean) {
        if (show) {
            loaderCountFlag = 1
            notifyItemInserted(itemCount - 1)
        } else {
            loaderCountFlag = 0
            notifyItemRemoved(itemCount - 1)
        }

    }
}

class TopPostsAdapterViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
class ProgressViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)