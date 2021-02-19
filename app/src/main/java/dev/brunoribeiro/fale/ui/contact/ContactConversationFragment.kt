package dev.brunoribeiro.fale.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.databinding.FragmentContactAddBinding
import dev.brunoribeiro.fale.databinding.FragmentContactConversationBinding

class ContactConversationFragment : Fragment() {

    private var _binding: FragmentContactConversationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactConversationBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.getString("name").let {
            binding.toolbarConv.title = it.toString()
        }

        binding.toolbarConv.setNavigationOnClickListener { activity?.onBackPressed() }


        return view
    }

}