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
    var end_text = "Tillykke du har Vundet "
    var secret_word = ""
    var shown_word = ""
    var chosen_letters = ""
    var totalPoints = 0
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

        if (data?.length ?: 0 >= 1) {
            secret_word = data.toString()
            shown_word = hideWord(data.toString())
            secretWord.text = shown_word
        }
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
            pickLetter("h")
            playTurn()
        }

        btn_nav.setOnClickListener {
            val data = Bundle()
            data.putString("data", end_text)
            Navigation.findNavController(btn_nav).navigate(R.id.action_gameFragment_to_endFragment,data)
        }
    }

    private fun playTurn() {
        spinTheWheel()
        if(lives<=0)
        {
            endGame()
        }
        else if("?" !in shown_word)
        {
            endGame()
        }
    }

    private fun spinTheWheel() {
        val x = (1..20).random()
        when (x) {
            in 1..3 -> {
                gameText.text="Gæt et bogstav for 2000 point"
                points= 2000
                // TODO add active boolean
            }
            in 4..8 -> {
                gameText.text="Gæt et bogstav for 1000 point"
                points= 1000

            }
            in 9..15 -> {
                gameText.text="Gæt et bogstav for 500 point"
                points= 500

            }
            16 -> {
                lives++
                gameText.text="extra turn"
                setLives()

            }
            in 17..19 -> {
                lives--
                gameText.text="miss turn"
                setLives()
            }
            20 -> {
                gameText.text="Bankrupt"
                totalPoints = 0
                setPoints()
            }
            else -> {
                gameText.text="FEJL! :" + x.toString()
            }
        }
    }

    private fun hideWord(word: String): String {
        return word.replace("\\w".toRegex(), "?")
    }

    private fun pickLetter(letter:String) {
        val hit = secret_word.contains(letter, ignoreCase = true)
        chosen_letters += letter
        val regex =("[^ "+chosen_letters+"]").toRegex(RegexOption.IGNORE_CASE)
        val result = secret_word.replace(regex,"?")
        secretWord.text = result
        shown_word=result
        if (hit)
        {
            // TODO: add text for when a letter is hit
            totalPoints +=points
            setPoints()
        }
        else
        {
            // TODO: add text for miss
            lives--
            setLives()
        }

    }

    private fun endGame() {
        // TODO: add text
        gameText.text="END"
        if(lives == 0)
        {
            end_text = "Du Tabte Spillet :'("
        }
        else
        {
            end_text += " Antal point: " + totalPoints
        }
        btn_nav.visibility=View.VISIBLE
        btn_roll.visibility=View.INVISIBLE
    }

    private fun setPoints() {
        pointText.text = "Points: " + totalPoints
    }

    private fun setLives() {
        livesText.text = "Antal liv: "+ lives
    }


}
