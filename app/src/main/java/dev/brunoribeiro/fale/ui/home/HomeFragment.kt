package dev.brunoribeiro.fale.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.adapters.ContactsAdapter
import dev.brunoribeiro.fale.databinding.FragmentHomeBinding
import dev.brunoribeiro.fale.entities.Contact
import dev.brunoribeiro.fale.entities.Message
import dev.brunoribeiro.fale.repository.ServicesRepository


class HomeFragment : Fragment() {

    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var repository: ServicesRepository
    private lateinit var auth: FirebaseAuth

    val viewModel by viewModels<HomeViewModel>(){
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
            }

        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        repository = ServicesRepository.getInstance()
        auth = FirebaseAuth.getInstance()
        val view = binding.root
        connectDB()
        readData()

        viewModel.repository.allContacts.observe(viewLifecycleOwner){
            binding.rvHomeContacts.apply {
                adapter = ContactsAdapter(it)
                layoutManager = LinearLayoutManager(view.context)
                setHasFixedSize(true)
            }
        }

        binding.addContact.setOnClickListener {
            addContact()
        }


        return view
    }

    fun connectDB(){
        database = FirebaseDatabase.getInstance()
        reference = database.getReference(auth.uid.toString())
    }

    fun readData(){
        val listContact = mutableListOf<Contact>()
        listContact.add(Contact("Bruno", "null", listOf(Message("eitch", "15:30"))))
        listContact.add(Contact("Bruno", "null", listOf(Message("eitch", "15:30"))))
        viewModel.getAllContacts(listContact)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.children.forEach {
                        val contact = it.getValue(Contact::class.java)
                        Log.i("contact", contact.toString())
                        if (contact != null) {
                            listContact.add(contact)
                        }

                    }

                    viewModel.getAllContacts(listContact)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("error ", error.toString())
            }
        })


    }

    fun addContact(){
        findNavController().navigate(R.id.action_homeFragment_to_contactAddFragment)
    }

}