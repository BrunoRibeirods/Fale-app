package dev.brunoribeiro.fale.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.databinding.FragmentLoginBinding
import dev.brunoribeiro.fale.databinding.FragmentRegisterBinding
import dev.brunoribeiro.fale.repository.ServicesRepository
import dev.brunoribeiro.fale.ui.MainActivity


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val TAG = "registerFrag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val view = binding.root

        binding.btnVoltarLogin.setOnClickListener { activity?.onBackPressed() }

        binding.btnRegister.setOnClickListener { createAccount(binding.etRegisterEmail.text.toString(), binding.etRegisterPassword.text.toString(), binding.etRegisterName.toString()) }

        return view
    }


    private fun createAccount(email: String, password: String, name: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    //Modificar User
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Email ou senha invalidos.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
        // [END create_user_with_email]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val name = binding.etRegisterName
        val email = binding.etRegisterEmail
        val password = binding.etRegisterPassword
        val passwordConfirm = binding.etRegisterPasswordConfirm

        if (TextUtils.isEmpty(name.text.toString())) {
            valid = false
        }

        if (TextUtils.isEmpty(email.text.toString())) {
            valid = false
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            valid = false
        }

        if (TextUtils.isEmpty(passwordConfirm.text.toString())) {
            valid = false
        }

        if(password.text.toString() != passwordConfirm.text.toString()){
            valid = false
            passwordConfirm.error = "Senhas não conferem"
        }


        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        } else {
            Log.i(TAG, "Nenhum usuario conectado.")
        }
    }


}