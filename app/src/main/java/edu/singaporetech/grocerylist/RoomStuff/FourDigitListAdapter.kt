package edu.singaporetech.madata

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class FourDigitListAdapter(private var digitItems: List<FourDigit>): RecyclerView.Adapter<FourDigitListAdapter.MyViewHolder>()
{
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idTextView: TextView = view.findViewById(R.id.normalIDTextView)
        var itemTextView: TextView = view.findViewById(R.id.normalDigitTextView)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.digits_normal, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = digitItems[position]
        holder.idTextView.text = "#" + item.id.toString()
        holder.itemTextView.text = item.number.toString()
    }

    override fun getItemCount(): Int = digitItems.size
}
