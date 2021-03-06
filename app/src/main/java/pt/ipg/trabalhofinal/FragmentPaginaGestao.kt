package pt.ipg.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalhofinal.databinding.FragmentPaginagestaoBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentPaginaGestao : Fragment() {

    private var _binding: FragmentPaginagestaoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPaginagestaoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFuncionarios.setOnClickListener() {
           findNavController().navigate(R.id.action_FragmentPaginaGestao_to_fragmentverfuncionarios)
        }
        binding.buttonClientes.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentPaginaGestao_to_fragmentVerClientes)
        }
        binding.buttonJogos.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentPaginaGestao_to_fragmentVerJogos)
        }
        binding.buttonVendas.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentPaginaGestao_to_fragmentVerVendas)
        }

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_main
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_settings -> true
            else -> false
        }
}