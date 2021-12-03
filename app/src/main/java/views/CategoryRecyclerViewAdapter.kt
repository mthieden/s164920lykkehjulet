package views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.view_category.view.*
import org.json.JSONObject
import kotlin.random.Random


class CategoryRecyclerViewAdapter(private val categories: List<String>)
    : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
    private lateinit var  JSONString : String
    private lateinit var JSONcategory : JSONObject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_category,parent,false)
        // return the view holder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the current animal
        holder.category.text = "${position+1}. ${categories[position]}"
        JSONString =  holder.itemView.context.getString(R.string.category_json)
        JSONcategory = JSONObject(JSONString)

        holder.itemView.setOnClickListener {
            val data = Bundle()
            val list = JSONcategory.getJSONArray(categories[position])
            val secretWord = list.getString(Random.nextInt(0, list.length()))
            data.putString("category", categories[position])
            data.putString("secretWord", secretWord)

            Navigation.findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_gameFragment, data)
        }
    }

    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return categories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val category: TextView = itemView.categoryText
    }

    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}