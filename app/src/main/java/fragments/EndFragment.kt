package fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.s164920lykkehjulet.R

class EndFragment : Fragment() {

    lateinit var txt: TextView
    lateinit var btn_new_game : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_end, container, false)
        Cast(view)
        return view
    }

    private fun Cast(view: View) {
        txt = view.findViewById(R.id.textView_ResponsFragment_Respons)
        btn_new_game = view.findViewById(R.id.btn_new_game)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: String? = arguments?.getString("data")

        if (data?.length ?: 0 >= 1)
            txt.text = data
        else
            txt.text = "Null"

        btn_new_game.setOnClickListener {
            val navDirections = EndFragmentDirections.actionEndFragmentToHomeFragment()
            Navigation.findNavController(btn_new_game).navigate(navDirections)
        }
    }
}
