package dev.brunoribeiro.fale.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.databinding.FragmentContactAddBinding
import dev.brunoribeiro.fale.databinding.FragmentHomeBinding
import dev.brunoribeiro.fale.entities.Contact
import dev.brunoribeiro.fale.entities.Message
import dev.brunoribeiro.fale.repository.ServicesRepository
import dev.brunoribeiro.fale.ui.home.HomeViewModel

class ContactAddFragment : Fragment() {


    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    private var _binding: FragmentContactAddBinding? = null
    private val binding get() = _binding!!
    lateinit var repository: ServicesRepository
    private lateinit var auth: FirebaseAuth

    val viewModel by viewModels<ContactAddViewModel>(){
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ContactAddViewModel(repository) as T
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       _binding = FragmentContactAddBinding.inflate(inflater, container, false)
        repository = ServicesRepository.getInstance()
        auth = FirebaseAuth.getInstance()
        connectDB()
        val view = binding.root

        binding.toolbarAdd.setNavigationOnClickListener { activity?.onBackPressed() }

        binding.btnAddContact.setOnClickListener { }

        return view
    }

    fun connectDB(){
        database = FirebaseDatabase.getInstance()
        reference = database.getReference(auth.uid.toString())
    }

    fun getContact(name: String, img: String, message: Message): Contact {

        return Contact(name, img, listOf(message))
    }

    fun sendContactDB(contact: Contact, friendEmail: String): String{
        var res = reference.child(friendEmail).setValue(contact)
        return res.toString()
    }


}