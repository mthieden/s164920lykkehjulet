package views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s164920lykkehjulet.MainActivityViewModel
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.view_letter.view.*

class LetterRecyclerViewAdapter(private val letters: List<String>,
                                private val viewModel: MainActivityViewModel )
    : RecyclerView.Adapter<LetterRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_letter,parent,false)

        // return the view holder
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the current color
        holder.letter.text = letters[position]
        holder.itemView.setOnClickListener {
            if(viewModel.buttonBool.value == true)
            {
                viewModel.guessLetter.value=letters[position]
                it.visibility=View.INVISIBLE
                viewModel.buttonBool.value =false
            }
        }
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return letters.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val letter: TextView = itemView.letterButton
    }


    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}