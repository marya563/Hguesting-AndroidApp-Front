import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.R
import com.example.newapp.addcarr
import com.example.newapp.models.Car
import com.example.newapp.utis.LoadingDialog
import kotlinx.android.synthetic.main.car_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarFragment : Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter : CarAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var carArrayList: ArrayList<Car>


    //    val loadingDialog = LoadingDialog(requireActivity())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_fraghment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())


        initCarList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.carRecycler)
        recyclerView.layoutManager = layoutManager
        adapter = CarAdapter(carArrayList)
        recyclerView.adapter = adapter




        adapter.setOnItemClickListener(object : CarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, addcarr::class.java)
                intent.putExtra("cartype", carArrayList[position].cartype)
                startActivity(intent)
            }
        })

    }

    private fun initCarList(){
        loadingDialog.startLoadingDialog()
        carArrayList = ArrayList()
        val ingredients = ArrayList<String>()
        carArrayList.clear()
        val retIn = RetrofitInstance.api(context)
        val call = retIn.getCar()
        call.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    carArrayList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    loadingDialog.dismissDialog()
                    Log.d("cartype", carArrayList.toString())
                    Log.d("carbran", carbrand.toString())
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.d("Error", t.message.toString())
                loadingDialog.dismissDialog()
            }
        })

    }
}

