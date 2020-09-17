package com.example.newsapp.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import kotlinx.android.synthetic.main.item_category_card.view.*

class CategoriesAdapter(private val categories: List<String>) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var selectedPosition: Int = 0

    private var onItemClickListener: ((String) -> Unit)? = null

    fun onItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_category_card, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categories[position]

        holder.itemView.apply {
            categoryName.text = "#$currentCategory"

            if (selectedPosition == position) {
                categoryLayout.setBackgroundResource(R.drawable.category_item_background)
                categoryName.setTextColor(Color.parseColor("#ffffff"))
            } else {
                categoryLayout.setBackgroundResource(0)
                categoryName.setTextColor(Color.parseColor("#263238"))
            }

            setOnClickListener {
                onItemClickListener?.let { it(currentCategory) }

                if (selectedPosition >= 0) {
                    notifyItemChanged(selectedPosition)
                }
                selectedPosition = holder.adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem)
}
