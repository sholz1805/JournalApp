package com.example.journal.auth.events

import com.example.journal.data.model.LoginRequest
import com.example.journal.data.model.RegistrationRequest

sealed class AuthEvent {
    data class RegistrationEvent(val request: RegistrationRequest) : AuthEvent()
    data class LoginEvent(val request: LoginRequest) : AuthEvent()
}
