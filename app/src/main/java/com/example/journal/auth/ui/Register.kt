package com.example.journal.auth.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.journal.auth.events.AuthEvent
import com.example.journal.auth.viewmodels.RegisterViewModel
import com.example.journal.data.model.RegistrationRequest
import com.example.journal.data.model.Status
import com.example.journal.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class Register : Fragment() {

    init {
        val threadInfo = Thread.currentThread().name
        Log.i("register frag", "running on thread $threadInfo")
    }

    private lateinit var registerBinding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerBinding = FragmentRegisterBinding
            .inflate(inflater, container, false)
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val btn = registerBinding.registerBtn
        val loginLink = registerBinding.registerLink
        val navController = findNavController()
        loginLink.setOnClickListener {
            val action = RegisterDirections.actionRegisterToLogin()
            navController.navigate(action)
        }
        registerViewModel.isFormValid.observe(viewLifecycleOwner) {
            it?.let { isValid ->
                if (!isValid) {
                    val snackbar = Snackbar.make(
                        registerBinding.root,
                        "Please complete all fields",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
            }
        }
        registerViewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                if (it.status == Status.LOADING) {
                    btn.text = "Loading ..."
                }
                if (it.status == Status.ERROR) {
                    btn.text = "Register"
                    val snackbar = Snackbar.make(
                        registerBinding.root,
                        it.message ?: "Looks like something went wrong",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
                if (it.status == Status.SUCCESS) {
                    val snackbar = Snackbar.make(
                        registerBinding.root,
                        "Registration Success",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setAction("Dismiss") {
                        val action = RegisterDirections.actionRegisterToLogin()
                        navController.navigate(action)
                    }
                    // Add a callback in case the user doesn't dismiss via our action
                    snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                val action = RegisterDirections.actionRegisterToLogin()
                                navController.navigate(action)
                            }
                        }
                    })
                    snackbar.show()
                }
            }
        }

        btn.setOnClickListener {
            val firstname = registerBinding.firstNameInput.text.toString()
            val lastname = registerBinding.lastNameInput.text.toString()
            val email = registerBinding.emailInput.text.toString()
            val password = registerBinding.passwordInput.text.toString()
            val request = RegistrationRequest(firstname, lastname, email, password)
            val event = AuthEvent.RegistrationEvent(request)
            Log.i("register-event", event.toString())
            registerViewModel.onEvent(event)

        }

    }

}