package dev.acuon.expensemanager.models

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.acuon.expensemanager.models.ExpenseTable
import dev.acuon.expensemanager.models.IncomeTable


@Dao
interface MoneyDAO {

    @Insert
    fun insertToIncome(incomeTable: IncomeTable)

    @Update
    fun updateIncome(incomeTable: IncomeTable)

    @Delete
    fun deleteFromIncome(incomeTable: IncomeTable)

    @Query("SELECT * FROM INCOME_TABLE")
    fun getIncomes(): LiveData<MutableList<IncomeTable>>

    @Query("SELECT SUM(amount) AS TOTAL FROM INCOME_TABLE")
    fun getAllIncomeSum(): LiveData<Double>


    @Insert
    fun insertToExpense(expenseTable: ExpenseTable)

    @Update
    fun updateExpense(expenseTable: ExpenseTable)

    @Delete
    fun deleteFromExpense(expenseTable: ExpenseTable)

    @Query("SELECT * FROM EXPENSE_TABLE")
    fun getExpenses(): LiveData<MutableList<ExpenseTable>>

    @Query("SELECT SUM(amount) AS TOTAL FROM EXPENSE_TABLE")
    fun getAllExpenseSum(): LiveData<Double>

}