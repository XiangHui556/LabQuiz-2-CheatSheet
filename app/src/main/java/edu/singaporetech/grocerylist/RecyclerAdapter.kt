package edu.singaporetech.grocerylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import edu.singaporetech.grocerylist.MainActivity.Companion.checkedArray


internal class RecyclerAdapter(private var groceryList: Array<String>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTextView: CheckedTextView = view.findViewById(R.id.groceryListTextView)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = groceryList[position]
        holder.itemTextView.text = item
        if(item in checkedArray){
            holder.itemTextView.isChecked = true
        }

        holder.itemTextView.setOnClickListener{
            if(item in checkedArray){
                holder.itemTextView.isChecked = false
                checkedArray.remove(item)
            }
            else{
                holder.itemTextView.isChecked = true
                checkedArray.add(item)
            }
        }

    }
    override fun getItemCount(): Int {
        return groceryList.size
    }
}