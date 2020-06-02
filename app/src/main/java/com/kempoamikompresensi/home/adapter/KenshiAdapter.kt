package com.kempoamikompresensi.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kempoamikompresensi.R
import com.kempoamikompresensi.kenshi.KenshiDetail
import com.kempoamikompresensi.kenshi.model.KenshiEntity
import com.kempoamikompresensi.kenshi.model.KenshiModel
import kotlinx.android.synthetic.main.item_kenshi.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class KenshiAdapter : RecyclerView.Adapter<KenshiAdapter.ViewHolder>() {

    var listData: MutableList<KenshiEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_kenshi, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        val context = holder.itemView.context
        data.let { data ->
            holder.name.text = data.name
            holder.nik.text = data.nik

            val number = position + 1
            holder.number.text = "$number."

            holder.itemView.onClick {

                context.startActivity(context.intentFor<KenshiDetail>(
                                        "id" to data.id,
                                        "name" to data.name,
                                        "address" to data.address,
                                        "phone" to data.phone,
                                        "nik" to data.nik,
                                        "born" to data.born,
                                        "gender" to data.gender
                                ))

            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val number = itemView.number_tv
        val name = itemView.name_kenshi_tv
        val nik = itemView.nik_kenshi_tv
    }

    fun add(data: List<KenshiEntity>) {
        listData.addAll(data)
        notifyDataSetChanged()
    }

}