package ua.codeasylum.topostsforomreddit.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.codeasylum.topostsforomreddit.api.ERR_CONNECT
import ua.codeasylum.topostsforomreddit.api.ERR_NULL
import ua.codeasylum.topostsforomreddit.api.ERR_WITH_CODE
import ua.codeasylum.topostsforomreddit.api.RetrofitApi
import ua.codeasylum.topostsforomreddit.models.Top

private const val LIMIT_COUNT = "20"
private const val LIMIT_KEY = "limit"
private const val AFTER_KEY = "after"

class TopPostsRepository(private val api: RetrofitApi) : Repository {

    private val parameters: MutableMap<String, String> = mutableMapOf(LIMIT_KEY to LIMIT_COUNT, AFTER_KEY to "")

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback) {
        api.getTopPosts(parameters).enqueue(object : Callback<Top> {
            override fun onResponse(call: Call<Top>?, response: Response<Top>?) {
                when {
                    response == null -> onGetDataResultCallback.onError(ERR_NULL)
                    response.isSuccessful -> {
                        onGetDataResultCallback.onSuccess(response.body()!!.data.children)
                        updateAfter(response.body()!!.data.after)
                    }
                    else -> {
                        onGetDataResultCallback.onError("${ERR_WITH_CODE}${response.code()}")
                    }
                }

            }

            override fun onFailure(call: Call<Top>?, t: Throwable?) {
                onGetDataResultCallback.onError(ERR_CONNECT)
            }

        })

    }

    private fun updateAfter(after: String?) {
        if (after != null) {
            parameters.remove(AFTER_KEY)
            parameters[AFTER_KEY] = after
        }
    }

}