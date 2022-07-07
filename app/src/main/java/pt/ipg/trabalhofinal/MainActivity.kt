package pt.ipg.trabalhofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import pt.ipg.trabalhofinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var fragment: Fragment? = null

    var idMenuAtual = R.menu.menu_main
        get() = field
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(idMenuAtual, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada: Boolean

        if (fragment is FragmentPaginaGestao) {
            opcaoProcessada = (fragment as FragmentPaginaGestao).processaOpcaoMenu(item)
        } else if (fragment is FragmentVerClientes) {
            opcaoProcessada = (fragment as FragmentVerClientes).processaOpcaoMenu(item)
        } else if (fragment is FragmentEditarCliente) {
            opcaoProcessada = (fragment as FragmentEditarCliente).processaOpcaoMenu(item)
        }  else if (fragment is FragmentEliminarCliente) {
             opcaoProcessada = (fragment as FragmentEliminarCliente).processaOpcaoMenu(item)
        } else if (fragment is FragmentVerFuncionarios) {
            opcaoProcessada = (fragment as FragmentVerFuncionarios).processaOpcaoMenu(item)
        } else if (fragment is FragmentEditarFuncionarios) {
            opcaoProcessada = (fragment as FragmentEditarFuncionarios).processaOpcaoMenu(item)
        }else {
            opcaoProcessada = false
        }



        if (opcaoProcessada) return true

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun atualizaOpcoesLista(mostraAlterarEliminar: Boolean) {
        menu!!.findItem(R.id.action_alterar).setVisible(mostraAlterarEliminar)
        menu!!.findItem(R.id.action_eliminar).setVisible(mostraAlterarEliminar)
    }
}