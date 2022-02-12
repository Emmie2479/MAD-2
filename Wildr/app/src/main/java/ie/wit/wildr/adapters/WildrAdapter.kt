package ie.wit.wildr.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.wildr.databinding.CardWildrBinding
import ie.wit.wildr.models.WildrModel

interface WildrListener {
    fun onWildrClick(animal: WildrModel)
}

class WildrAdapter constructor(private var animals: List<WildrModel>,
                                   private val listener: WildrListener) :
    RecyclerView.Adapter<WildrAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWildrBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val animal = animals[holder.adapterPosition]
        holder.bind(animal, listener)
    }

    override fun getItemCount(): Int = animals.size

    class MainHolder(private val binding : CardWildrBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: WildrModel, listener: WildrListener) {
            binding.animalName.text = animal.name
            binding.animalSex.text = animal.sex
            Picasso.get().load(animal.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onWildrClick(animal) }
        }
    }
}