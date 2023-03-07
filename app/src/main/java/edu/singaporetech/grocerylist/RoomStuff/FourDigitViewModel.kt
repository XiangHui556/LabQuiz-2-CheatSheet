package edu.singaporetech.madata

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FourDigitViewModel(private val repository: FourDigitRepository): ViewModel()
{
    val digitItems: LiveData<List<FourDigit>> = repository.allDigits.asLiveData()

    fun addDigits(fourDigit: FourDigit) = viewModelScope.launch {
        repository.insertDigits(fourDigit)
    }

    fun dropTable() = viewModelScope.launch {
        repository.dropTable()
    }

}

class FourDigitViewModelDataStore(application: Application) : AndroidViewModel(application)
{
    val datastore = UserPreferencesRepository(application)

    val test = datastore.userPreferencesFlow().asLiveData(Dispatchers.IO)

    fun updateTest(test: Boolean){
        viewModelScope.launch {
            datastore.updateView(test)
        }
    }

}

class TaskItemModelFactory(private val repository: FourDigitRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(FourDigitViewModel::class.java))
            return FourDigitViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}