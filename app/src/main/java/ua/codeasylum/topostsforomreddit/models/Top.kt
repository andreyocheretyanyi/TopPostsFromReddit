package ua.codeasylum.topostsforomreddit.models

import com.google.gson.annotations.SerializedName

data class Top(
        @SerializedName("kind") val kind: String,
        @SerializedName("data") val data: Data
)