package com.projects.mycompany.cary.careseeker.inbox


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.mycompany.cary.databinding.ItemInboxSeekerBinding
import com.projects.mycompany.cary.models.CareGiver

class SeekerInboxAdapter(val clickListener: CareGiverListener): ListAdapter<CareGiver, SeekerInboxAdapter.ViewModel>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binder = ItemInboxSeekerBinding.inflate(layoutInflater,parent,false)
        return ViewModel(binder)
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val careGiver = getItem(position)
        holder.bind(careGiver,clickListener)
    }

    class ViewModel(private val binding: ItemInboxSeekerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(careGiver: CareGiver, clickListener: CareGiverListener){
            binding.giver = careGiver
            binding.clickListener = clickListener
            binding.executePendingBindings()
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
class CareGiverListener(val clickListener: (careGiver: CareGiver) -> Unit){
    fun onClick(careGiver: CareGiver) = clickListener(careGiver)
}