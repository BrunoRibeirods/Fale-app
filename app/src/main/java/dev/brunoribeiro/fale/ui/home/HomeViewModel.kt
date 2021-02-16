package dev.brunoribeiro.fale.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.brunoribeiro.fale.entities.Contact
import dev.brunoribeiro.fale.repository.ServicesRepository

class HomeViewModel(val repository: ServicesRepository): ViewModel() {


    fun getAllContacts(contacts: List<Contact>){
        repository.updateContactList(contacts)
    }
}