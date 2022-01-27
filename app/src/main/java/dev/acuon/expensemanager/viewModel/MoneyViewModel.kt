package dev.acuon.expensemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.acuon.expensemanager.models.ExpenseTable
import dev.acuon.expensemanager.models.IncomeTable
import dev.acuon.expensemanager.repository.MoneyRepo

class MoneyViewModel(private val repo: MoneyRepo): ViewModel() {

    fun addToIncome(income: IncomeTable){
        repo.addToIncome(income)
    }

    fun addToExpense(expense: ExpenseTable){
        repo.addToExpense(expense)
    }

    fun getIncomes(): LiveData<MutableList<IncomeTable>> {
        return repo.getIncomes()
    }

    fun getExpenses(): LiveData<MutableList<ExpenseTable>> {
        return repo.getExpenses()
    }

    fun updateIncome(income: IncomeTable){
        repo.updateIncome(income)
    }

    fun updateExpense(expense: ExpenseTable){
        repo.updateExpense(expense)
    }

    fun deleteFromIncome(income: IncomeTable){
        repo.deleteFromIncome(income)
    }

    fun deleteFromExpense(expense: ExpenseTable){
        repo.deleteFromExpense(expense)
    }

    fun getAllIncomeSum(): LiveData<Double> {
        return repo.getAllIncomeSum()
    }

    fun getAllExpenseSum(): LiveData<Double> {
        return repo.getAllExpenseSum()
    }

}