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
import dev.acuon.expensemanager.models.IncomeTable
import dev.acuon.expensemanager.models.MoneyDAO
import dev.acuon.expensemanager.repository.MoneyRepo
import dev.acuon.expensemanager.view.adapter.IncomeClickListener
import dev.acuon.expensemanager.view.adapter.MoneyIncomeAdapter
import dev.acuon.expensemanager.viewModel.MoneyViewModel
import dev.acuon.expensemanager.viewModel.MoneyViewModelFactory
import kotlinx.android.synthetic.main.edit_details.view.*
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.item_layout.*
import java.text.SimpleDateFormat
import java.util.*

class IncomeFragment : Fragment(R.layout.fragment_income), IncomeClickListener {

    private lateinit var adapter: MoneyIncomeAdapter
    private var listOfIncomes = mutableListOf<IncomeTable>()

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

        viewModel.getIncomes().observe(viewLifecycleOwner, Observer {
            listOfIncomes.clear()
            listOfIncomes.addAll(it)
            adapter.notifyDataSetChanged()
        })

        adapter = MoneyIncomeAdapter(listOfIncomes, this)
        val linearLayoutManager = LinearLayoutManager(context)
        rcvIncome.adapter = adapter
        rcvIncome.layoutManager = linearLayoutManager
        tvAmount?.setTextColor(Color.parseColor("#0B2AD5"))

    }

    override fun deleteClick(incomeTable: IncomeTable, position: Int) {

        viewModel.deleteFromIncome(incomeTable)

    }

    override fun editClick(incomeTable: IncomeTable, position: Int) {

        editIncomeDetails(incomeTable)
        Toast.makeText(context, "edit clicked", Toast.LENGTH_SHORT).show()

    }

    private fun editIncomeDetails(incomeTable: IncomeTable) {

        val view = layoutInflater.inflate(R.layout.edit_details, null)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Edit Income Details")
        alertDialog.setCancelable(false)

//        alertDialog.setMessage("Your message here")
//        etAmountIncome.text = Editable.Factory.getInstance().newEditable(incomeTable.toString())

        view.etAmountIncome.setText(incomeTable.amount.toString())
        view.etAmountIncome.requestFocus()

        view.etDescriptionIncome.setText(incomeTable.description)

        view.tvDateSelectorIncome.text = incomeTable.date

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
                view.context,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

//        view.etAmountIncome.doAfterTextChanged {
//            if (view.etAmountIncome.text.trim().toString().isEmpty()) {
//                view.etAmountIncome.error = "cannot be empty"
//            }
//        }

//        alertDialog.setButton(
//            AlertDialog.BUTTON_POSITIVE,
//            "SAVE",
//            DialogInterface.OnClickListener { dialog, which ->
////                dialog.cancel()
//            })

        view.btnSave.setOnClickListener {

            if (view.etAmountIncome.text.trim().toString().isEmpty()) {

                view.etAmountIncome.error = "cannot be empty"

            } else {

//                val dbRoom = context?.let { DatabaseRoom.getDatabaseObject(it) }!!
//                val dao = dbRoom.getMoneyDAO()

                incomeTable.amount = view.etAmountIncome.text.toString().toDouble()
                incomeTable.description = view.etDescriptionIncome.text.toString()
                incomeTable.date = view.tvDateSelectorIncome.text.toString()

                viewModel.updateIncome(incomeTable)
//                CoroutineScope(Dispatchers.IO).launch {
//                    dao.updateIncome(incomeTable)
//                }

                alertDialog.cancel()

            }

        }

//        alertDialog.setButton(
//            AlertDialog.BUTTON_NEGATIVE,
//            "CANCEL",
//            DialogInterface.OnClickListener { dialog, which ->
//                dialog.cancel()
//            })

        view.btnCancel.setOnClickListener {
            alertDialog.cancel()
        }

        alertDialog.setView(view)
        alertDialog.show()

    }

}