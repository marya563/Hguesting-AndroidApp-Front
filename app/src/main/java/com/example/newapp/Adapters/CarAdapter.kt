import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.R
import com.example.newapp.models.Car
import com.squareup.picasso.Picasso


class CarAdapter(private val carList: List<Car>): RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class CarViewHolder(itemView: View,listener:OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val car_type = itemView.findViewById<TextView>(R.id.cartype)
        val car_brand = itemView.findViewById<TextView>(R.id.carbrand)
        val car_engine = itemView.findViewById<TextView>(R.id.carengine)
        val car_price = itemView.findViewById<TextView>(R.id.carprice)
        val car_Pic = itemView.findViewById<ImageView>(R.id.carPic)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.car_type.text =car.cartype
        holder.car_brand.text = car.carbrand
       /* Picasso.get().load(Car.carPic).into(holder.car_Pic)
    }
*/}
    override fun getItemCount(): Int {
        return carList.size
    }
}

