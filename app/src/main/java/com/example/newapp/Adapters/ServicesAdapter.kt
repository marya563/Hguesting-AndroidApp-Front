package com.example.newapp.Adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.R

class ServicesAdapter: RecyclerView.Adapter<ServicesAdapter.ViewHolder>(){
    private var title =  arrayOf("Hotels","Room Services","Rental Cars","Guided Tours")
    private var images = intArrayOf(
        R.drawable.hotel,
        R.drawable.servicesr,
        R.drawable.car,
        R.drawable.eljam2,
    )
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.services_item, parent, false)
        return ViewHolder(itemView,mListener)
    }
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun getItemCount(): Int{
        return title.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.item_title.text = title[position]
        holder.itemImage.setImageResource(images[position])

    }
    inner class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        var itemImage: ImageView
        var item_title: TextView
        init {
            itemImage = itemView.findViewById(R.id.itemImage)
            item_title = itemView.findViewById(R.id.item_title)

        }
        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)

            }
            /* inner class MainHolder(var binding: CarAdapter): RecyclerView.ViewHolder(binding.true) {
                 init {
                     binding.root.setOnClickListener {
                         onItemClick?.invoke()
                     }
                 }

                 val itemImage = binding.images
                 val item_title = binding.title
                 val item_price = binding.prices
                 val item_details = binding.details
                 val item_engine = binding.engine

             }*/


        }

    } }
