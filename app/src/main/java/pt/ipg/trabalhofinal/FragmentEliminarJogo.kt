package pt.ipg.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import pt.ipg.trabalhofinal.databinding.FragmentEliminarJogoBinding


class FragmentEliminarJogo : Fragment() {

    private lateinit var jogo: Jogo
    private var _binding: FragmentEliminarJogoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarJogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        jogo = FragmentEliminarJogoArgs.fromBundle(arguments!!).jogo

        binding.textViewNomeJogoEli.text = jogo.nome
        binding.textViewPrecoJogoEli.text = jogo.preco
        binding.textViewGeneroJogoEli.text = jogo.genero
        binding.textViewPublicadoraJogoEli.text = jogo.publicadora
        binding.textViewDataLanJogoEli.text = jogo.data_de_lancamento
        binding.textViewPlataformaJogoEli.text = jogo.plataforma.nome
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                true
            }
            R.id.action_cancelar -> {
                true
            }
            else -> false
        }
    }
}