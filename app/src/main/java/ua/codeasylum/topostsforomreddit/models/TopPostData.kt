package ua.codeasylum.topostsforomreddit.models

import com.google.gson.annotations.SerializedName

data class TopPostData(
        @SerializedName("title") val title: String,
        @SerializedName("subreddit_name_prefixed") val subredditNamePrefixed: String,
        @SerializedName("ups") val ups: Int,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("created") val created: Long,
        @SerializedName("author") val author: String,
        @SerializedName("num_comments") val numComments: Int,
        @SerializedName("permalink") val permalink: String,
        @SerializedName("created_utc") val createdUtc: Long
)