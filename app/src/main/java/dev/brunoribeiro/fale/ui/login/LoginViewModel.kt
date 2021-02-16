package dev.brunoribeiro.fale.ui.login

import androidx.lifecycle.ViewModel
import dev.brunoribeiro.fale.entities.User
import dev.brunoribeiro.fale.repository.ServicesRepository

class LoginViewModel(val repository: ServicesRepository): ViewModel() {


    fun getUser(user: User){
        repository.updateUserInformation(user)
    }
}