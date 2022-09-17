package com.projects.mycompany.cary.careseeker.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.mycompany.cary.databinding.ItemGiverBinding
import com.projects.mycompany.cary.models.CareGiver


class SearchAdapter(private val careGiverListener: CareGiverListener): ListAdapter<CareGiver, SearchAdapter.ViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemGiverBinding.inflate(inflater,parent,false)
        return ViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val careGiver = getItem(position)
        holder.bind(careGiver!!,careGiverListener)
    }

    class ViewHolder(private val binder: ItemGiverBinding) : RecyclerView.ViewHolder(binder.root){
        fun bind(careGiver: CareGiver, careGiverListener: CareGiverListener){
            binder.careGiver = careGiver
            binder.clickListener = careGiverListener
            binder.executePendingBindings()
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<CareGiver>(){
        override fun areItemsTheSame(oldItem: CareGiver, newItem: CareGiver): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CareGiver, newItem: CareGiver): Boolean {
            return oldItem == newItem
        }

    }
}

class CareGiverListener (val clickListener: (item: CareGiver) -> Unit){
    fun onClick(careGiver: CareGiver) = clickListener(careGiver)
}
