package dev.brunoribeiro.fale.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.databinding.FragmentLoginBinding
import dev.brunoribeiro.fale.entities.User
import dev.brunoribeiro.fale.repository.ServicesRepository
import dev.brunoribeiro.fale.ui.MainActivity


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var repository: ServicesRepository
    private lateinit var auth: FirebaseAuth
    val TAG = "loginFrag"

    val viewModel by viewModels<LoginViewModel>(){
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(repository) as T
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        repository = ServicesRepository.getInstance()
        auth = FirebaseAuth.getInstance()
        val view = binding.root

        binding.btnRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }

        binding.btnLogin.setOnClickListener { signIn(binding.etLoginEmail.text.toString(), binding.etLoginPassword.text.toString()) }

        return view
    }


    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            viewModel.getUser(User(user.displayName.toString(), user.email.toString(), user.photoUrl.toString()))
        } else {
            Log.i(TAG, "Nenhum usuario conectado.")
        }
    }


    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Email ou senha invalidos.", Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }


            }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.etLoginEmail
        val password = binding.etLoginPassword

        if (TextUtils.isEmpty(email.text.toString())) {
            valid = false
            email.error = "Campo vazio"
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            valid = false
        }


        return valid
    }



}