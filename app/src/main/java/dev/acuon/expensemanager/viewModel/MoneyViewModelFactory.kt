package dev.acuon.expensemanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.acuon.expensemanager.repository.MoneyRepo

class MoneyViewModelFactory(private val repo: MoneyRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoneyViewModel(repo) as T
    }

}