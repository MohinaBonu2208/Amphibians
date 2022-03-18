package com.bignerdranch.android.amphibians

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.amphibians.network.Amphibian
import com.bignerdranch.android.amphibians.network.Api
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AmphibianStatus { LOADING, ERROR, DONE }
class ViewModel : ViewModel() {

    private var _amphibian = MutableLiveData<Amphibian>()
    val amphibian: LiveData<Amphibian> = _amphibian

    private var _amphibians = MutableLiveData<List<Amphibian>>()
    val amphibians: LiveData<List<Amphibian>> = _amphibians

    private var _status = MutableLiveData<AmphibianStatus>()
    val status: LiveData<AmphibianStatus> = _status

    fun getAmphibianList() {
        _status.value = AmphibianStatus.LOADING
        viewModelScope.launch {
            try {
                val result = Api.retrofitService.getAmphibians()
                _amphibians.value = result
                _status.value = AmphibianStatus.DONE
            } catch (e: Exception) {
                _status.value = AmphibianStatus.ERROR
                _amphibians.value = emptyList()

            }
        }
    }

    fun onItemClicked(amphibian: Amphibian) {
        _amphibian.value = amphibian
    }


}