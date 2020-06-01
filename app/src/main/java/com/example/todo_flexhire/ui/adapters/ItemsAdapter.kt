package com.example.todo_flexhire.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_flexhire.backend.model.TodoItemModel
import com.example.todo_flexhire.databinding.TodoItemBinding
import com.example.todo_flexhire.screens.todoItems.SingleItemViewModel
import kotlinx.android.synthetic.main.todo_item.view.*

class ItemsAdapter() : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    var items: MutableList<TodoItemModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoItemModel) {
            val viewModel =
                SingleItemViewModel(
                    item
                ) // TODO should we instantiate the view model in a different way?
            binding.itemViewModel = viewModel
            binding.executePendingBindings()
            itemView.deleteButton.setOnClickListener {
                viewModel.deleteItem()
                // https://stackoverflow.com/a/38167883/905801
                items.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, items.size)
            }
        }
    }

}