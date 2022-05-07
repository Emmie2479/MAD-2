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
import ie.wit.wildr.databinding.CardWildrBinding
import ie.wit.wildr.models.WildrModel
import ie.wit.wildr.utils.customTransformation

interface WildrClickListener {
    fun onWildrClick(animal: WildrModel)
}

class WildrAdapter constructor(private var animals: ArrayList<WildrModel>,
                                  private val listener: WildrClickListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<WildrAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWildrBinding
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

    inner class MainHolder(val binding : CardWildrBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(animal: WildrModel, listener: WildrClickListener) {
            binding.root.tag = animal
            binding.animal = animal

            Picasso.get().load(animal.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onWildrClick(animal) }
            binding.executePendingBindings()
        }
    }
}