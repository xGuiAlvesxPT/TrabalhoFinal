package pt.ipg.trabalhofinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalhofinal.databinding.FragmentVerFuncionariosBinding

class FragmentVerFuncionarios : Fragment(){

    private var _binding: FragmentVerFuncionariosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVerFuncionariosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.buttonEntrar.setOnClickListener {
       //     findNavController().navigate(R.id.action_FragmentPaginaInicial_to_PaginaGestao)
       // }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}