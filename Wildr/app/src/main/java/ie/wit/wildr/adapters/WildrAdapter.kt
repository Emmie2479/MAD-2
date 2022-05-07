package ie.wit.wildr.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.wildr.R
import ie.wit.wildr.databinding.CardWildrBinding
import ie.wit.wildr.models.WildrModel

interface WildrClickListener {
    fun onWildrClick(animal: WildrModel)
}

class WildrAdapter constructor(private var animals: ArrayList<WildrModel>,
                                  private val listener: WildrClickListener)
    : RecyclerView.Adapter<WildrAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWildrBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val animal = animals[holder.adapterPosition]
        holder.bind(animal,listener)
    }

    fun removeAt(position: Int) {
        animals.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = animals.size

    inner class MainHolder(val binding : CardWildrBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: WildrModel, listener: WildrClickListener) {
            binding.root.tag = animal
            binding.animal = animal
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onWildrClick(animal) }
            binding.executePendingBindings()
        }
    }
}