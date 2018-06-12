package mm.dd.tools.accessibility.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_list.*
import mm.dd.Tools.accessibility.R
import mm.dd.tools.accessibility.db.DataSource
import mm.dd.tools.accessibility.ext.loge
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AppListFragment : Fragment() {

    companion object {
        const val TYPE_ADAPTATION = 1
        const val TYPE_NORMAL = 2
        fun newInstance(type: Int): AppListFragment {
            val fragment = AppListFragment()
            val args = Bundle()
            args.putInt("type", type)
            fragment.arguments = args
            return fragment
        }
    }

    private var content: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (content == null) {
            content = inflater.inflate(R.layout.layout_list, null)
        }
        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appRecycler.layoutManager = LinearLayoutManager(context!!, LinearLayout.VERTICAL, false)
        appRecycler.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        appRecycler.adapter = SkipAppAdapter(context!!, emptyList())
        updateData()
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateData() {
        doAsync {
            while (true) {
                if (DataSource.isDataReady()) {
                    context?.loge("try get data")
                    val data = when (arguments!!["type"]) {
                        TYPE_ADAPTATION -> DataSource.adapted()
                        TYPE_NORMAL -> DataSource.unadapted()
                        else -> emptyList()
                    }
                    if (data != null && data.isNotEmpty()) {
                        context?.loge("got data")
                        uiThread {
                            (appRecycler.adapter as SkipAppAdapter).updateData(data)
                        }
                    }
                    break
                } else {
                    Thread.sleep(200)
                }
            }
        }
    }
}