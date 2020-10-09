package com.lamnt.nguyentunglam_roomdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lamnt.nguyentunglam_roomdb.R
import com.lamnt.nguyentunglam_roomdb.model.Student
import kotlinx.android.synthetic.main.item_student.view.*

class ListStudentAdapter(var data : List<Student>, var onItemClick : OnItemClickListener) :
    RecyclerView.Adapter<ListStudentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = data[position]
        holder.bind(item,onItemClick)
    }
    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item : Student, onItemClick: OnItemClickListener){
            with(itemView) {
                txtName.text = item.name
                btnEdit.setOnClickListener{
                    onItemClick.onEdit(item,adapterPosition)
                }

                btnRemove.setOnClickListener{
                    onItemClick.onRemove(adapterPosition)
                }}

        }
    }

    interface OnItemClickListener{
        fun onEdit(student : Student, position : Int)
        fun onRemove(position : Int)
    }
}