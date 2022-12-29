import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Update
import com.example.hotelbooking.models.ServiceRoom
import com.example.hotelbooking.models.ServiceRoomQuantite

class ShoppingCart private constructor() {
    // Shopping cart instance
    private object Holder { val INSTANCE = ShoppingCart() }
    companion object { val instance: ShoppingCart by lazy { Holder.INSTANCE } }

    // Shopping cart items
    private val items = mutableListOf<ServiceRoom>()
    private val itemsQuantite = ArrayList<ServiceRoomQuantite>()
    private val shoppingCartLiveData = MutableLiveData<ShoppingCart>()

    // Shopping cart methods
    fun addProduct(product: ServiceRoom) {
        items.add(product)
        itemsQuantite.add(ServiceRoomQuantite(product.name,1,product.price.toInt()))
        shoppingCartLiveData.value = this
    }

    fun removeProduct(product: ServiceRoom) {
        items.remove(product)
        shoppingCartLiveData.value = this

    }

    fun getProducts(): List<ServiceRoom> {
        return items
    }

    fun TotalPrice():Int{
        var p = 0
        for (i in 0 until itemsQuantite.size) {
            p += itemsQuantite[i].price
        }
        return p
    }

    fun getTotalQuantityByName(name: String): Int {
        val item = itemsQuantite.find { it.name == name }
        return item!!.quantite

    }


    fun getShoppingCartLiveData(): LiveData<ShoppingCart> {
        return shoppingCartLiveData
    }

    fun getItems(): List<ServiceRoom> {
        return items
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun containsProduct(product: ServiceRoom): Boolean {
        return items.any { it.name == product.name }
    }

    fun updateQuantity(name: String, quantity: Int):Int {
        val item = itemsQuantite.find { it.name == name }
        if (item != null && item.quantite >= 1 ) {
            item.quantite = quantity
        }
        return  item!!.quantite

    }

    fun updatePrice(name: String, price: Int):Int {
        val item = itemsQuantite.find { it.name == name }
        if (item != null  ) {
            item.price = price
        }
        return  item!!.price

    }

    // Other methods
}