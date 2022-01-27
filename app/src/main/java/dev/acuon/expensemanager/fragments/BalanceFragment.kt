package dev.acuon.expensemanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.acuon.expensemanager.R
import dev.acuon.expensemanager.models.DatabaseRoom
import dev.acuon.expensemanager.models.MoneyDAO
import dev.acuon.expensemanager.repository.MoneyRepo
import dev.acuon.expensemanager.viewModel.MoneyViewModel
import dev.acuon.expensemanager.viewModel.MoneyViewModelFactory
import kotlinx.android.synthetic.main.fragment_balance.*

class BalanceFragment : Fragment(R.layout.fragment_balance) {

    lateinit var dbRoom: DatabaseRoom
    lateinit var dao: MoneyDAO
    lateinit var viewModel: MoneyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRoom = context?.let { DatabaseRoom.getDatabaseObject(it) }!!
        dao = dbRoom?.getMoneyDAO()

        val repo = MoneyRepo(dao)
        val viewModelFactory = MoneyViewModelFactory(repo)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoneyViewModel::class.java)

        var totalIncome = 0.0
        var totalExpense = 0.0

        viewModel.getAllIncomeSum().observe(viewLifecycleOwner, Observer {
            //totalIncome = it.toString()
            if (it == null) {
                tvOverallIncome.text = "0.0"
                totalIncome = 0.0
                if (totalExpense == 0.0) {
                    tvOverallTotal.text = "0.0"
                } else {
                    tvOverallTotal.text = "-$totalExpense"
                }

            } else {
                tvOverallIncome.text = it.toString()
                totalIncome = it
                tvOverallTotal.text = (totalIncome - totalExpense).toString()
            }

        })

        viewModel.getAllExpenseSum().observe(viewLifecycleOwner, Observer {
            //totalExpense = it.toString()
            //            tvOverallExpense.text = String.format("%.2f", totalExpense)
            if (it == null) {
                tvOverallExpense.text = "0.0"
                totalExpense = 0.0
                if (totalIncome == 0.0) {
                    tvOverallTotal.text = "0.0"
                } else {
                    tvOverallTotal.text = totalIncome.toString()
                }

            } else {
                tvOverallExpense.text = it.toString()
                totalExpense = it
                tvOverallTotal.text = (totalIncome - totalExpense).toString()
            }

        })


//        total = (totalIncome.toDouble()-totalExpense.toDouble()).toString()
//        tvOverallTotal.text = String.format("%.2f", total)

    }

}