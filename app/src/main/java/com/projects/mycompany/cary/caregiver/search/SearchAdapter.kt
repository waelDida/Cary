package com.projects.mycompany.cary.caregiver.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.mycompany.cary.databinding.ItemSeekerBinding
import com.projects.mycompany.cary.models.CareSeeker

class SearchAdapter(private val careSeekerListener: CareSeekerListener): ListAdapter<CareSeeker,SearchAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binder = ItemSeekerBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val careSeeker = getItem(position)
        holder.bind(careSeeker,careSeekerListener)
    }

    class ViewHolder(private val binder: ItemSeekerBinding): RecyclerView.ViewHolder(binder.root){

        fun bind(careSeeker: CareSeeker, careSeekerListener: CareSeekerListener){
            binder.seeker = careSeeker
            binder.clickListener = careSeekerListener
            binder.executePendingBindings()
        }
    }
    class DiffCallback: DiffUtil.ItemCallback<CareSeeker>(){
        override fun areItemsTheSame(oldItem: CareSeeker, newItem: CareSeeker): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CareSeeker, newItem: CareSeeker): Boolean {
            return oldItem == newItem
        }
    }
}

class CareSeekerListener(val clickListener: (item: CareSeeker) -> Unit){
    fun onClick(careSeeker: CareSeeker) = clickListener(careSeeker)
}

