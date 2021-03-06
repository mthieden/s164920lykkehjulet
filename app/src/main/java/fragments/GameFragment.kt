package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.s164920lykkehjulet.MainActivityViewModel
import com.example.s164920lykkehjulet.R
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import org.json.JSONObject
import views.LetterRecyclerViewAdapter

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

        val letterObserver = Observer<String> { newName ->
            pickLetter(newName)
            if (lives <= 0) {
                endGame()
            } else if ("?" !in shown_word) {
                endGame()
            }
        }
        model.guessLetter.observe(viewLifecycleOwner, letterObserver)

        return view
    }

    private fun Cast(view: View) {
        btn_nav = view.findViewById(R.id.btn_gameFragment_nav)
        btn_roll = view.findViewById(R.id.btn_gameFragment_Roll)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = listOf<String>("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "??", "??", "??")


        val chosen_word: String? = arguments?.getString("secretWord")
        val chosen_category: String? = arguments?.getString("category")

        if ((chosen_word?.length ?: 0 >= 1) and (chosen_category?.length ?: 0 >= 1) ) {
            secret_word = chosen_word.toString()
            shown_word = hideWord(chosen_word.toString())
            secretWord.text = shown_word
            chosenCategory.text="Kategori : $chosen_category"
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
        val adapter = LetterRecyclerViewAdapter(categories, model).apply {
            recyclerView.adapter = this
        }

        btn_roll.setOnClickListener {
            playTurn()
        }

        btn_nav.setOnClickListener {
            val data = Bundle()
            data.putString("data", end_text)
            Navigation.findNavController(btn_nav).navigate(R.id.action_gameFragment_to_endFragment,data)
        }
    }

    private fun playTurn() {
        if(model.buttonBool.value!=true) {
            spinTheWheel()
            if (lives <= 0) {
                endGame()
            } else if ("?" !in shown_word) {
                endGame()
            }
        }
    }

    private fun spinTheWheel() {
        val x = (1..20).random()
        when (x) {
            in 1..3 -> {
                gameText.text="G??t et bogstav for 2000 point"
                points= 2000
                model.buttonBool.value=true
            }
            in 4..8 -> {
                gameText.text="G??t et bogstav for 1000 point"
                points= 1000
                model.buttonBool.value=true

            }
            in 9..15 -> {
                gameText.text="G??t et bogstav for 500 point"
                points= 500
                model.buttonBool.value=true
            }
            16 -> {
                lives++
                gameText.text="+Ekstra liv!"
                setLives()

            }
            in 17..19 -> {
                lives--
                gameText.text="Bang, Du mistede et liv!"
                setLives()
            }
            20 -> {
                gameText.text="Bankerot, du mistede alle dine point"
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
        val regex =("[^ $chosen_letters]").toRegex(RegexOption.IGNORE_CASE)
        val result = secret_word.replace(regex,"?")
        secretWord.text = result
        shown_word=result
        when {
            letter =="" -> {
                gameText.text="Velkommen til spillet, tryk p?? knappen for at komme i gang"
            }
            hit -> {

                gameText.text= "\"$letter\" er Korrekt"
                totalPoints +=points
                setPoints()
            }
            else -> {
                gameText.text= "\"$letter\" er Forkert"
                lives--
                setLives()
            }
        }
        points=0
    }

    private fun endGame() {

        if(lives <= 0)
        {
            gameText.text= gameText.text.toString() + "\nSpillet er slut, tryk p?? videre for at afslutte"
            end_text = "Du Tabte Spillet :'("
        }
        else
        {
            gameText.text="Spillet er slut, tryk p?? videre for at afslutte"
            end_text += " Antal point: " + totalPoints
        }
        btn_nav.visibility=View.VISIBLE
        btn_roll.visibility=View.INVISIBLE
    }

    private fun setPoints() {
        pointText.text = "Points: $totalPoints"
    }

    private fun setLives() {
        livesText.text = "Antal liv: $lives"
    }


}
