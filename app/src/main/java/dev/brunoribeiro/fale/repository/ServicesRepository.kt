package dev.brunoribeiro.fale.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.brunoribeiro.fale.entities.Contact
import dev.brunoribeiro.fale.entities.User

abstract class ServicesRepository {

    private val _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User>
        get() = _userDetail

    private val _allContacts = MutableLiveData<List<Contact>>()
    val allContacts: LiveData<List<Contact>>
        get() = _allContacts


    fun updateUserInformation(user: User) {
        _userDetail.value = user
    }

    fun updateContactList( contacts: List<Contact>){
        _allContacts.value = contacts
    }


    companion object {
        private var INSTANCE: ServicesRepository? = null

        fun getInstance(): ServicesRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = object : ServicesRepository() {

                    }
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}