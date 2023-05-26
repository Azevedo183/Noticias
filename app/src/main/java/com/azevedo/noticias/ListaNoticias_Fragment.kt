package com.azevedo.noticias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.azevedo.noticias.databinding.FragmentListaNoticiasBinding

class ListaNoticias_Fragment : Fragment() {
    private var _binding: FragmentListaNoticiasBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate
        return inflater.inflate(R.layout.fragment_lista_noticias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterNoticias = AdapterNoticias()
        binding.RecyclerViewNoticias.adapter = adapterNoticias
        binding.RecyclerViewNoticias.layoutManager = LinearLayoutManager(requireContext())
    }
    companion object {

    }
}