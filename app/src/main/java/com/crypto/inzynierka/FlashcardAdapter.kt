package com.crypto.inzynierka

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.crypto.inzynierka.databinding.ItemFlashcardsBinding

class FlashcardAdapter(private val flashcards: MutableList<Triple<String, String, Int>>) :
    RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    inner class FlashcardViewHolder(val binding: ItemFlashcardsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemFlashcardsBinding.inflate(inflater, parent, false)
        return FlashcardViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val (concept, definition, _) = flashcards[position]
        holder.binding.front.text = concept
        holder.binding.back.text = definition

        if (position == 0) {
            holder.binding.front.visibility = View.VISIBLE
            holder.binding.back.visibility = View.INVISIBLE
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.binding.root.context, R.color.light_blue)
            )
        } else {
            holder.binding.front.visibility = View.INVISIBLE
            holder.binding.back.visibility = View.INVISIBLE
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.binding.root.context, R.color.theme2_4)
            )
        }

        holder.binding.cardView.setOnClickListener {
            val flipOut: Animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.flip_out)
            val flipIn: Animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.flip_in)

            if (holder.binding.front.visibility == View.VISIBLE) {
                holder.binding.front.startAnimation(flipOut)
                flipOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        holder.binding.front.visibility = View.INVISIBLE
                        holder.binding.back.visibility = View.VISIBLE
                        holder.binding.back.startAnimation(flipIn)
                    }
                    override fun onAnimationRepeat(animation: Animation) {}
                })
            } else {
                holder.binding.back.startAnimation(flipOut)
                flipOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        holder.binding.back.visibility = View.INVISIBLE
                        holder.binding.front.visibility = View.VISIBLE
                        holder.binding.front.startAnimation(flipIn)
                    }
                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
        }
    }
}
