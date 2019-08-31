package com.test.testcatsapp.ui.cats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.test.testcatsapp.R
import com.test.testcatsapp.data.entity.Cat
import kotlinx.android.synthetic.main.cat_item_view.view.*

class CatsAdapter(
    private val glide: RequestManager,
    private val cats: List<Cat>,
    private val onClickAction: (Cat) -> Unit,
    private val onLongClickAction: (Cat) -> Boolean
) : RecyclerView.Adapter<CatsAdapter.CatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder =
        CatsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cat_item_view,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) =
        holder.bind(glide, cats[position], onClickAction, onLongClickAction)

    override fun getItemCount(): Int =
        cats.size

    class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            glide: RequestManager,
            cat: Cat,
            onClickAction: (Cat) -> Unit,
            onLongClickAction: (Cat) -> Boolean
        ) {

            itemView.apply {
                setOnClickListener { onClickAction(cat) }
                setOnLongClickListener { onLongClickAction(cat) }
            }

            glide
                .load(cat.imageUrl)
                .centerCrop()
                .into(itemView.catImageView)
        }
    }
}