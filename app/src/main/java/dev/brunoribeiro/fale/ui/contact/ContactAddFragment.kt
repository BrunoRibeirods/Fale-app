package dev.brunoribeiro.fale.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.databinding.FragmentContactAddBinding
import dev.brunoribeiro.fale.databinding.FragmentHomeBinding

class ContactAddFragment : Fragment() {

    private var _binding: FragmentContactAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       _binding = FragmentContactAddBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }


}