package com.example.firebasecrud

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrud.databinding.ItemMahasiswaBinding

class AdapterMahasiswa(val list: ArrayList<User>): RecyclerView.Adapter<AdapterMahasiswa.ViewHolder>() {

    private var onItemClickCallback: onAdapterListener? = null

    inner class ViewHolder(private val binding: ItemMahasiswaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bind: User){
            binding.itmNama.text = bind.nama
            binding.itmNpm.text = bind.npm.toString()
            binding.btnDelete.setOnClickListener { onItemClickCallback?.onClickDel(bind) }
            binding.content.setOnClickListener { onItemClickCallback?.onClick(bind) }
        }
    }

    interface onAdapterListener{
        fun onClick(list: User)
        fun onClickDel(list:User)
    }

    fun setOnItemClick(onItemClickCallback: AdapterMahasiswa.onAdapterListener){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val x = ItemMahasiswaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(x)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}