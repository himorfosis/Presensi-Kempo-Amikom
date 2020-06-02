package com.kempoamikompresensi.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kempoamikompresensi.R
import com.kempoamikompresensi.presensi.PresensiDetail
import com.kempoamikompresensi.presensi.model.PresensiEntity
import com.kempoamikompresensi.util.date.DateSet
import kotlinx.android.synthetic.main.item_presensi.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class PresensiAdapter : RecyclerView.Adapter<PresensiAdapter.ViewHolder>() {

    var listData: MutableList<PresensiEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_presensi, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listData[position]
        val context = holder.itemView.context
        item.let {item ->

            val number = position + 1
            holder.number.text = "$number."

            holder.name.text = item.name
            holder.nik.text = item.nik

            if (item.datetime!! > 0) {
                holder.time.text = DateSet.descripTimestampToTime(item.datetime!!)
            }

            holder.itemView.onClick {
                context.startActivity(context.intentFor<PresensiDetail>(
                        "id" to item!!.id,
                        "id_kenshi" to item.id_kenshi,
                        "nik" to item.nik,
                        "name" to item.name,
                        "time" to item.datetime.toString()
                    )
                )
            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val number = itemView.number_tv
        val time = itemView.time_tv
        val name = itemView.name_kenshi_tv
        val nik = itemView.nik_kenshi_tv
    }

    fun add(data: List<PresensiEntity>) {
        listData!!.addAll(data)
        notifyDataSetChanged()
    }


}