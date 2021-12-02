package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.s164920lykkehjulet.MainActivityViewModel
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import org.json.JSONObject
import views.RecyclerViewAdapter

class GameFragment : Fragment() {

    private val model: MainActivityViewModel by viewModels()
    lateinit var btn_nav: Button
    lateinit var btn_roll: Button
    var end_text = "Tillykke du har Vundet"
    var secret_word = ""
    var shown_word = ""
    var points = 0
    var lives = 5




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        Cast(view)

        /*
        val nameObserver = Observer<String> { newName ->
            secretWord.text = newName
        }
        model.secretWord.observe(viewLifecycleOwner, nameObserver)
        */
        return view
    }

    private fun Cast(view: View) {
        btn_nav = view.findViewById(R.id.btn_gameFragment_nav)
        btn_roll = view.findViewById(R.id.btn_gameFragment_Roll)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email =  model.letters.value
        var JSONcategory = JSONObject(email)
        val categories = JSONcategory.keys().asSequence().toList()
        val data: String? = arguments?.getString("data")

        if (data?.length ?: 0 >= 1)
            secretWord.text = hideWord(data.toString())
        else
            secretWord.text = "FEJL"

        StaggeredGridLayoutManager(
        8, // span count
        StaggeredGridLayoutManager.VERTICAL // orientation
        ).apply {
            // specify the layout manager for recycler view
            recyclerView.layoutManager = this
        }
        val adapter = RecyclerViewAdapter(categories).apply {
            recyclerView.adapter = this
        }

        btn_roll.setOnClickListener {
            btn_nav.visibility=View.VISIBLE
            btn_roll.visibility=View.INVISIBLE
        }

        btn_nav.setOnClickListener {
            val data = Bundle()
            data.putString("data", end_text)
            Navigation.findNavController(btn_nav).navigate(R.id.action_gameFragment_to_endFragment,data)
        }
    }

    private fun playTurn() {
    }

    private fun spinTheWheel() {
    }

    private fun hideWord(word: String): CharSequence? {
        return word.replace("\\w".toRegex(), "?")
    }

    private fun pickLetter() {

    }

    private fun endGame() {
        btn_nav.visibility=View.VISIBLE
        btn_roll.visibility=View.INVISIBLE
    }

}
