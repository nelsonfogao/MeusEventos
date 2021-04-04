package com.example.meuseventos.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meuseventos.R
import com.example.meuseventos.RecyclerAdapter
import com.example.meuseventos.database.AppUtil
import com.example.meuseventos.database.EventoFirestoreDao
import com.example.meuseventos.database.UsuarioFirebaseDao
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.list_fragment.*
import java.lang.Error

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        verificarUsuario()


        val view =  inflater.inflate(R.layout.list_fragment, container, false)

        val listEventosViewModelFactory = ListEventosViewModelFactory(EventoFirestoreDao())
        listViewModel = ViewModelProvider(this, listEventosViewModelFactory)
            .get(ListViewModel::class.java)


        listViewModel.eventos.observe(viewLifecycleOwner, Observer {
            recyclerViewEventos.adapter = RecyclerAdapter(it) {
                AppUtil.eventoSelecionado = it
                findNavController().navigate(R.id.formFragment)
            }
            recyclerViewEventos.layoutManager = LinearLayoutManager(requireContext())
        })
        listViewModel.atualizarListaEventos()


        MobileAds.initialize(requireActivity()){}



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floatingActionButtonAddEvento.setOnClickListener {
            AppUtil.eventoSelecionado = null
            findNavController().navigate(R.id.formFragment)
        }
        val mAdview = requireActivity().findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdview.loadAd(adRequest)
        mAdview.adListener = object: AdListener(){
            override fun onAdLoaded() {
                Log.i("ad", "anuncio carregado")
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                Log.i("ad", "anuncio carregado")
            }
        }
    }

    fun verificarUsuario(){
        if (UsuarioFirebaseDao.mAuth.currentUser == null)
            findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogout.setOnClickListener {
            listViewModel.encerrarSessao()
            findNavController().navigate(R.id.loginFragment)

        }
    }

}