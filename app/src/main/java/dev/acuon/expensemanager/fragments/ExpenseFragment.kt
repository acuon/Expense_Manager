package dev.acuon.expensemanager.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.expensemanager.R
import dev.acuon.expensemanager.models.DatabaseRoom
import dev.acuon.expensemanager.models.ExpenseTable
import dev.acuon.expensemanager.models.MoneyDAO
import dev.acuon.expensemanager.repository.MoneyRepo
import dev.acuon.expensemanager.view.adapter.ExpenseClickListener
import dev.acuon.expensemanager.view.adapter.MoneyExpenseAdapter
import dev.acuon.expensemanager.viewModel.MoneyViewModel
import dev.acuon.expensemanager.viewModel.MoneyViewModelFactory
import kotlinx.android.synthetic.main.edit_details.view.*
import kotlinx.android.synthetic.main.fragment_expense.*
import kotlinx.android.synthetic.main.item_layout.*
import java.text.SimpleDateFormat
import java.util.*

class ExpenseFragment : Fragment(R.layout.fragment_expense), ExpenseClickListener {

    private lateinit var adapter: MoneyExpenseAdapter
    private var listOfExpenses = mutableListOf<ExpenseTable>()

    lateinit var viewModel: MoneyViewModel
    lateinit var dbRoom: DatabaseRoom
    lateinit var dao: MoneyDAO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRoom = context?.let { DatabaseRoom.getDatabaseObject(it) }!!
        dao = dbRoom.getMoneyDAO()

        val repo = MoneyRepo(dao)
        val viewModelFactory = MoneyViewModelFactory(repo)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoneyViewModel::class.java)

        viewModel.getExpenses().observe(viewLifecycleOwner, Observer {
            listOfExpenses.clear()
            listOfExpenses.addAll(it)
            adapter.notifyDataSetChanged()
        })

        adapter = MoneyExpenseAdapter(listOfExpenses, this)
        val linearLayoutManager = LinearLayoutManager(context)
        rcvExpense.adapter = adapter
        rcvExpense.layoutManager = linearLayoutManager
        tvAmount?.setTextColor(Color.parseColor("#0B2AD5"))

    }

    override fun deleteClick(expenseTable: ExpenseTable, position: Int) {
//        val dbRoom = context?.let { DatabaseRoom.getDatabaseObject(it) }!!
//        val dao = dbRoom.getMoneyDAO()
//        CoroutineScope(Dispatchers.IO).launch {
//            dao.deleteFromExpense(expenseTable)
//        }
        viewModel.deleteFromExpense(expenseTable)
    }

    override fun editClick(expenseTable: ExpenseTable, position: Int) {

        editExpenseDetails(expenseTable)
        Toast.makeText(context, "edit clicked", Toast.LENGTH_SHORT).show()

    }

    private fun editExpenseDetails(expenseTable: ExpenseTable) {

        val view = layoutInflater.inflate(R.layout.edit_details, null)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Edit Expense Details")
        alertDialog.setCancelable(false)

        view.etAmountIncome.setText(expenseTable.amount.toString())
        view.etAmountIncome.requestFocus()

        view.etDescriptionIncome.setText(expenseTable.description)

        view.tvDateSelectorIncome.text = expenseTable.date

        view.tvDateSelectorIncome.setOnClickListener {

            val cal = Calendar.getInstance()

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { anotherView, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val myFormat = "dd-MM-yyyy" // mention the format you need
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    view.tvDateSelectorIncome.text = sdf.format(cal.time)
                }

            DatePickerDialog(
                view.context, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        view.btnSave.setOnClickListener {

            if (view.etAmountIncome.text.trim().toString().isEmpty()) {

                view.etAmountIncome.error = "cannot be empty"

            } else {

//                val dbRoom = context?.let { DatabaseRoom.getDatabaseObject(it) }!!
//                val dao = dbRoom.getMoneyDAO()

                expenseTable.amount = view.etAmountIncome.text.toString().toDouble()
                expenseTable.description = view.etDescriptionIncome.text.toString()
                expenseTable.date = view.tvDateSelectorIncome.text.toString()

                viewModel.updateExpense(expenseTable)
//                CoroutineScope(Dispatchers.IO).launch {
//                    dao.updateExpense(expenseTable)
//                }

                alertDialog.cancel()
            }

        }

        view.btnCancel.setOnClickListener {
            alertDialog.cancel()
        }

        alertDialog.setView(view)
        alertDialog.show()

    }

}