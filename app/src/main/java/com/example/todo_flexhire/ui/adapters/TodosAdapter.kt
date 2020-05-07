package com.example.todo_flexhire.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_flexhire.backend.model.Todos
import com.example.todo_flexhire.databinding.SingleTodoBinding

/**
 * https://www.untitledkingdom.com/blog/refactoring-recyclerview-adapter-to-data-binding-5631f239095f-0
 */
class TodosAdapter() :
    RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

    // TODO try to use the live data directly here!
    var items: List<Todos> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTodoBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: SingleTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Todos) {
            binding.todoModel = item
            binding.executePendingBindings()
        }
    }

}