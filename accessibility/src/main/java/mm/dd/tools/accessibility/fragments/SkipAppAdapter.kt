package mm.dd.tools.accessibility.fragments

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_app.view.*
import mm.dd.Tools.accessibility.R
import mm.dd.tools.accessibility.db.SkipEntity

class SkipAppAdapter(private val ctx: Context, var appList: List<SkipEntity>) : RecyclerView.Adapter<SkipViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SkipViewHolder {
        return SkipViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_app, null))

    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun onBindViewHolder(holder: SkipViewHolder, position: Int) {
        val skipEntity = appList[position]
        with(holder.itemView) {
            icon.setImageDrawable(skipEntity.appIcon)
            packageName.text = skipEntity.packageName
            appName.text = skipEntity.appName
        }
    }

    fun updateData(newData: List<SkipEntity>) {
        val diff = DiffUtil.calculateDiff(DiffCallback(appList, newData), true)
        appList = newData
        diff.dispatchUpdatesTo(this)
    }

}

class SkipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class DiffCallback(val oldList: List<SkipEntity>, val newList: List<SkipEntity>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].packageName == newList[newItemPosition].packageName
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.packageName == new.packageName && old.enable == new.enable &&
                old.appName == new.appName
    }
}