package fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s164920lykkehjulet.MainActivityViewModel
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import views.CategoryRecyclerViewAdapter


class HomeFragment : Fragment() {

    private lateinit var  JSONString : String
    private lateinit var JSONcategory : JSONObject
    private lateinit var categories : List<String>
    private val model: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email =  model.categories.value
        var JSONcategory = JSONObject(email)
        val categories = JSONcategory.keys().asSequence().toList()

        val layoutManager = LinearLayoutManager(
            this.requireContext(), // context
            RecyclerView.VERTICAL, // orientation
            false // reverse layout
        ).apply {
            // specify the layout manager for recycler view
            recyclerView.layoutManager = this
        }

        // finally, data bind the recycler view with adapter
        val adapter = CategoryRecyclerViewAdapter(categories).apply {
            recyclerView.adapter = this
        }


    }

}
