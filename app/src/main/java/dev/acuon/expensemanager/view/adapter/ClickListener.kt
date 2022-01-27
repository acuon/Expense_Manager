package dev.acuon.expensemanager.view.adapter

import dev.acuon.expensemanager.models.ExpenseTable
import dev.acuon.expensemanager.models.IncomeTable

interface IncomeClickListener {

    //fun onClick(money: Money, position:Int)

    fun deleteClick(incomeTable: IncomeTable, position: Int)

    fun editClick(incomeTable: IncomeTable, position: Int)

}

interface ExpenseClickListener {

    fun deleteClick(expenseTable: ExpenseTable, position: Int)

    fun editClick(expenseTable: ExpenseTable, position: Int)

}