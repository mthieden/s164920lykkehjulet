package views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.s164920lykkehjulet.MainActivityViewModel
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.custom_view.view.*

class RecyclerViewAdapter(private val colors: List<String>,
                          private val viewModel: MainActivityViewModel )
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_view,parent,false)

        // return the view holder
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the current color
        holder.color.text = colors[position]
        holder.itemView.setOnClickListener {
            if(viewModel.buttonBool.value == true)
            {
                viewModel.guessLetter.value=colors[position]
                it.visibility=View.INVISIBLE
                viewModel.buttonBool.value =false
            }
        }
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return colors.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val color: TextView = itemView.tvColor
    }


    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}