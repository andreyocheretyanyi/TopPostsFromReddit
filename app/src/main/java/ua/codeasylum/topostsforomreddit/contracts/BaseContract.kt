package ua.codeasylum.topostsforomreddit.contracts

interface BaseContract {

    interface Presenter<in V : View> {

        fun onAttachView(view: V)

        fun onDetachView()


    }


    interface View {

        fun showGlobalProgressProgress()

        fun hideGlobalProgress()

        fun showToast(stringRes: Int)

        fun showToast(message: String)

    }
}