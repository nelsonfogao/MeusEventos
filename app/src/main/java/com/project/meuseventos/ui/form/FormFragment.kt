package com.project.meuseventos.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.project.meuseventos.CriptoString
import com.project.meuseventos.R
import com.project.meuseventos.database.AppUtil
import com.project.meuseventos.database.EventoFirestoreDao
import com.project.meuseventos.model.Evento
import kotlinx.android.synthetic.main.form_fragment.*

class FormFragment : Fragment() {

    private lateinit var formViewModel: FormViewModel
    var criptoString = CriptoString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.form_fragment, container, false)

        val application = requireActivity().application
        val formEmpresasViewModelFactory =
            FormEventosViewModelFactory(EventoFirestoreDao(), application)

        formViewModel = ViewModelProvider(
            this, formEmpresasViewModelFactory)
            .get(FormViewModel::class.java).apply {
                setUpMsgObserver(this)
                setUpStatusObserver(this)
            }

        formViewModel.eventos.observe(viewLifecycleOwner, Observer {
            if (it != null){
                preencherFormulario(it)
            }
        })

        formViewModel.convidados.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                listViewConvidados.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it
                )
            }
        })

        return view
    }

    private fun setUpStatusObserver(formViewModel: FormViewModel) {
        formViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status)
                findNavController().popBackStack()
        })
    }
    private fun setUpMsgObserver(formViewModel: FormViewModel) {
        formViewModel.msg.observe(viewLifecycleOwner, Observer { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AppUtil.eventoSelecionado != null) {
            buttonEventoSalvar.visibility = View.GONE
            formViewModel.selectEvento(AppUtil.eventoSelecionado!!.id!!)
        }

        else {
            buttonEventoSalvar.visibility = View.VISIBLE
        }
        buttonEventoSalvar.setOnClickListener {
            criptoString.setClearText(editTextEventBairro.text.toString())
            var novo = criptoString.getCriptoBase64()
            val bairro = novo

            var nome = editTextEventName.text.toString()

            var bairroNovo = bairro

            var data = editTextEventDate.text.toString()

            var autor = formViewModel.getUsuario().toString()


            formViewModel.salvarEventos(nome,data,bairroNovo!!,autor)
        }
        floatingActionButtonAddConvidados.setOnClickListener {
            findNavController().navigate(R.id.convidadosFragment)
        }
        floatingActionButtonDeleteEvent.setOnClickListener{
            formViewModel.deletarEvento(AppUtil.eventoSelecionado!!)
        }


    }
    private fun preencherFormulario(eventos: Evento){

        criptoString.setCriptoBase64(eventos.bairro)
        var novoBairro = criptoString.getClearText()
        editTextEventName.setText(eventos.nome)
        editTextEventDate.setText(eventos.data)
        editTextEventBairro.setText(novoBairro)

    }
}