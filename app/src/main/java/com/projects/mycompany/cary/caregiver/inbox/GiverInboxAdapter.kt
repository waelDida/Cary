package com.projects.mycompany.cary.caregiver.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.mycompany.cary.databinding.ItemInboxGiverBinding
import com.projects.mycompany.cary.models.CareSeeker

class GiverInboxAdapter(private val careSeekerListener: CareSeekerListener): ListAdapter<CareSeeker,GiverInboxAdapter.ViewModel>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInboxGiverBinding.inflate(layoutInflater,parent,false)
        return ViewModel(binding)
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val careSeeker = getItem(position)
        holder.bind(careSeeker,careSeekerListener)
    }

    class ViewModel(private val binding: ItemInboxGiverBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(careSeeker: CareSeeker, careSeekerListener: CareSeekerListener){
            binding.seeker = careSeeker
            binding.clickListener = careSeekerListener
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CareSeeker>(){
        override fun areItemsTheSame(oldItem: CareSeeker, newItem: CareSeeker): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CareSeeker, newItem: CareSeeker): Boolean {
            return oldItem == newItem
        }
    }
}

class CareSeekerListener(val clickListener: (careSeeker: CareSeeker) -> Unit){
    fun onClick(careSeeker: CareSeeker) = clickListener(careSeeker)
}