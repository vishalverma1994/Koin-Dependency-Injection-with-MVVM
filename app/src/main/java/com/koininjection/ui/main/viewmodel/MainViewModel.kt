package com.koininjection.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koininjection.data.model.User
import com.koininjection.data.repository.MainRepository
import com.koininjection.utils.NetworkHelper
import com.koininjection.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                mainRepository.getUsers().let {
                    if (it.isSuccessful){
                        _users.postValue(Resource.success(it.body()))
                    } else
                        _users.postValue(Resource.error(it.body().toString(), null))
                }
            } else _users.postValue(Resource.error("No internet connection", null))
        }
    }
}