package pt.ipg.trabalhofinal

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class AdapterJogos (val fragment: FragmentVerJogos) : RecyclerView.Adapter<AdapterJogos.ViewHolderJogo>() {
    var cursor: Cursor? = null
        get() = field
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

   inner class ViewHolderJogo(itemJogo: View) : RecyclerView.ViewHolder(itemJogo), View.OnClickListener {
        val textViewNomeJogo =
            itemJogo.findViewById<TextView>(R.id.textViewNomeJogo)
        val textViewPrecoJogo =
            itemJogo.findViewById<TextView>(R.id.textViewPrecoJogo)
        val textViewJogoDataLancamento =
            itemJogo.findViewById<TextView>(R.id.textViewJogoDataLancamento)
        val textViewJogoGenero =
            itemJogo.findViewById<TextView>(R.id.textViewJogoGenero)
        val textViewJogoPlataforma =
            itemJogo.findViewById<TextView>(R.id.textViewJogoPlataforma)
        val textViewJogoPublicadora =
            itemJogo.findViewById<TextView>(R.id.textViewJogoPublicadora)

        init {
            itemJogo.setOnClickListener(this)
        }

        var Jogo: Jogo? = null
            get() = field
            set(value: Jogo?) {
                field = value

                textViewNomeJogo.text = Jogo?.nome ?: ""
                textViewPrecoJogo.text = "${Jogo?.preco}"
                textViewJogoPlataforma.text = Jogo?.plataforma?.nome ?: ""
                textViewJogoGenero.text = Jogo?.genero?.nome ?: ""
                textViewJogoPublicadora.text = Jogo?.publicadora?.nome ?: ""
                val dateFormat = SimpleDateFormat("dd-MM-yyy")
                val dataNasc = Jogo?.data_de_lancamento
                val data= dateFormat.format(dataNasc)
                textViewJogoDataLancamento.text = data
            }
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            seleccionado?.desseleciona()
            this.seleciona()
        }

        private fun seleciona() {
            seleccionado = this
            fragment.jogoSelecionado = Jogo
            itemView.setBackgroundResource(android.R.drawable.alert_light_frame)
        }

        private fun desseleciona() {
            itemView.setBackgroundResource(itemViewType)
        }


    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJogo {
        val itemFuncionario =
            fragment.layoutInflater.inflate(R.layout.item_jogo, parent, false)
        return ViewHolderJogo(itemFuncionario)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolderJogo, position: Int) {
        cursor!!.moveToPosition(position)
        holder.Jogo = Jogo.fromCursor(cursor!!)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }
    companion object {
        var seleccionado : ViewHolderJogo? = null
    }
}
