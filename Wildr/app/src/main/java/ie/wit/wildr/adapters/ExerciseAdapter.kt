package ie.wit.wildr.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import ie.wit.wildr.R
import ie.wit.wildr.databinding.CardAnimalBinding
import ie.wit.wildr.models.AnimalModel
import ie.wit.wildr.utils.customTransformation

interface AnimalClickListener {
    fun onAnimalClick(animal: AnimalModel)
}

class AnimalAdapter constructor(private var animals: ArrayList<AnimalModel>,
                                  private val listener: AnimalClickListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<AnimalAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAnimalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
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

    inner class MainHolder(val binding : CardAnimalBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(animal: AnimalModel, listener: AnimalClickListener) {
            binding.root.tag = animal
            binding.animal = animal

            Picasso.get().load(animal.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onAnimalClick(animal) }
            binding.executePendingBindings()
        }
    }
}