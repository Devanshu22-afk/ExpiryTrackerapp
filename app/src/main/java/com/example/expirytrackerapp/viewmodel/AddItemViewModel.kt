package com.example.expirytrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.expirytrackerapp.data.AppDatabase
import com.example.expirytrackerapp.data.Item
import com.example.expirytrackerapp.data.ItemRepository
import kotlinx.coroutines.launch

class AddItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository
    private val _itemName = MutableLiveData<String>()
    private val _company = MutableLiveData<String>()
    private val _expiryDate = MutableLiveData<Long>()
    private val _mrp = MutableLiveData<String>()
    private val _errorMessage = MutableLiveData<String>()
    private val _successMessage = MutableLiveData<Boolean>()

    val itemName: LiveData<String> = _itemName
    val company: LiveData<String> = _company
    val expiryDate: LiveData<Long> = _expiryDate
    val mrp: LiveData<String> = _mrp
    val errorMessage: LiveData<String> = _errorMessage
    val successMessage: LiveData<Boolean> = _successMessage

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ItemRepository(database.itemDao())
    }

    fun updateItemName(name: String) {
        _itemName.value = name
    }

    fun updateCompany(company: String) {
        _company.value = company
    }

    fun updateExpiryDate(date: Long) {
        _expiryDate.value = date
    }

    fun updateMrp(mrp: String) {
        _mrp.value = mrp
    }

    fun saveItem() {
        val name = _itemName.value?.trim() ?: ""
        val company = _company.value?.trim() ?: ""
        val date = _expiryDate.value ?: 0L
        val mrpStr = _mrp.value?.trim() ?: ""

        if (name.isEmpty() || company.isEmpty() || date == 0L || mrpStr.isEmpty()) {
            _errorMessage.value = "All fields are required"
            return
        }

        val mrp = try {
            mrpStr.toDouble()
        } catch (e: NumberFormatException) {
            _errorMessage.value = "Invalid MRP value"
            return
        }

        if (mrp < 0) {
            _errorMessage.value = "MRP cannot be negative"
            return
        }

        val item = Item(
            itemName = name,
            company = company,
            expiryDate = date,
            mrp = mrp
        )

        viewModelScope.launch {
            repository.insert(item)
            _successMessage.value = true
        }
    }
} 