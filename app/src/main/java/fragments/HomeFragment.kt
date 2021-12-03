package fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import views.CategoryRecyclerViewAdapter
import kotlin.random.Random


class HomeFragment : Fragment() {

    private lateinit var  JSONString : String
    private lateinit var JSONcategory : JSONObject
    private lateinit var categories : List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var JSONcategory = JSONObject(view.context.getString(R.string.category_json))
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
        btn_random.setOnClickListener {
            val data = Bundle()
            val ran = Random.nextInt(categories.size);
            val list = JSONcategory.getJSONArray(categories[ran])
            val secretWord = list.getString(Random.nextInt(0, list.length()))
            data.putString("category", categories[ran])
            data.putString("secretWord", secretWord)

            Navigation.findNavController(btn_random).navigate(R.id.action_homeFragment_to_gameFragment, data)

        }
    }
}
