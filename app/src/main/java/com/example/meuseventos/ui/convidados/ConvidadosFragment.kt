package com.example.meuseventos.ui.convidados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.meuseventos.R
import com.example.meuseventos.ui.form.FormViewModel
import kotlinx.android.synthetic.main.convidados_fragment.*

class ConvidadosFragment : Fragment() {

    private lateinit var convidadosViewModel: ConvidadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.convidados_fragment, container, false)


        convidadosViewModel = ViewModelProvider(this).get(ConvidadosViewModel::class.java)


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
        buttonConvidadoSalvar.setOnClickListener {
            val convidado = editTextConvidadoNome.text.toString()
            convidadosViewModel.save(convidado)
            findNavController().popBackStack()
        }
    }

}