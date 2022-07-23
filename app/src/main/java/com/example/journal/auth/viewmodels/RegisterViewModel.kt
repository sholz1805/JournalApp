package com.example.journal.auth.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.journal.auth.events.AuthEvent
import com.example.journal.data.model.RegistrationRequest
import com.example.journal.data.model.RegistrationResponse
import com.example.journal.data.model.Resource
import com.example.journal.data.remote.RetrofitBuilder
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class RegisterViewModel : ViewModel() {

    val isFormValid: MutableLiveData<Boolean> = MutableLiveData(true)
    var status: MutableLiveData<Resource<*>> = MutableLiveData()
    private val api = RetrofitBuilder.gossipCentralApi

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.RegistrationEvent -> {
                val request = event.request
                if (
                    request.email.isEmpty() ||
                    request.firstName.isEmpty() ||
                    request.lastName.isEmpty() ||
                    request.password.isEmpty()
                ) {
                    isFormValid.value = false
                    return
                }
                register(request)
            }
        }
    }


    private fun register(request: RegistrationRequest) {
        status.value = Resource.loading(data = null);
        viewModelScope.launch(Dispatchers.IO) {
            val threadInfo = Thread.currentThread().name
            Log.i("register", "register running on thread $threadInfo")
            val result: RegistrationResponse?
            try {
                result = api.registerUser(request)
                if (result.successful) {
                    Log.i("register-success", result.toString())
                    val resource = Resource.success(data = result.data)
                    status.postValue(resource)
                } else {
                    Log.i("register-fail", result.data.toString())
                    val resource = Resource.error(data = null, message = result.data.message)
                    status.postValue(resource)
                }
            } catch (e: HttpException) {
                val resource: Resource<*>
                if (e.code() == 400) {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        e.response()?.errorBody()!!.charStream(),
                        RegistrationResponse::class.java
                    )
                    Log.i("register-error ", errorResponse.toString())
                    resource = Resource.error(data = null, message = errorResponse.data.message)
                    status.postValue(resource)
                } else {
                    Log.i("register-error", e.toString())
                    Log.i("register-error", e.message())
                    Log.i("register-error", e.response().toString())
                    resource = Resource.error(
                        data = null,
                        message = e.localizedMessage ?: "Looks like something went wrong"
                    )
                    status.postValue(resource)
                }

            } catch (e: Exception) {
                Log.i("register", e.toString())
                val resource = Resource.error(
                    data = null,
                    message = e.localizedMessage ?: "Looks like something went wrong"
                )
                status.postValue(resource)
            }

        }
    }
}