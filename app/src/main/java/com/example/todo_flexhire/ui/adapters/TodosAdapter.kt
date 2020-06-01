package com.example.todo_flexhire.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_flexhire.screens.todoItems.TodoItemsActivity
import com.example.todo_flexhire.backend.model.TodoModel
import com.example.todo_flexhire.databinding.SingleTodoBinding
import timber.log.Timber

/**
 * https://www.untitledkingdom.com/blog/refactoring-recyclerview-adapter-to-data-binding-5631f239095f-0
 * also: https://androidwave.com/android-data-binding-recyclerview/
 */
class TodosAdapter() :
    RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

    // TODO try to use the live data directly here!
    var items: MutableList<TodoModel> = mutableListOf()
        set(value) {
            value.sortBy { it.id }
            value.reverse()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTodoBinding.inflate(
            inflater,
            parent,
            false
        ) // To fill the full width: https://stackoverflow.com/a/30692398/905801
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: SingleTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoModel) {
            binding.todoModel = item
            binding.executePendingBindings()
            itemView.setOnClickListener {
                Timber.d("clicking on ${item.title}")
                val intent = Intent(itemView.context, TodoItemsActivity::class.java)
                intent.putExtra("id", item.id)
                itemView.context.startActivity(intent)
            }
        }
    }

}