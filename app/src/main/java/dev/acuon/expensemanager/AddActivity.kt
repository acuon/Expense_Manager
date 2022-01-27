package dev.acuon.expensemanager

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.acuon.expensemanager.models.DatabaseRoom
import dev.acuon.expensemanager.models.ExpenseTable
import dev.acuon.expensemanager.models.IncomeTable
import dev.acuon.expensemanager.models.MoneyDAO
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {

    private lateinit var dao: MoneyDAO
    private lateinit var dbRoom: DatabaseRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        dbRoom = DatabaseRoom.getDatabaseObject(this)

        dao = dbRoom.getMoneyDAO()

        relative.setOnClickListener {
            this.finish()
        }


        //for date picker
        tvDateSelector.text = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())

        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd-MM-yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                tvDateSelector.text = sdf.format(cal.time)

            }

        tvDateSelector.setOnClickListener {
            DatePickerDialog(
                this@AddActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        //date picker end

        btnSubmit.setOnClickListener {

            val tableName = spinnerIncomeExpense.selectedItem.toString()
            val amount = etAmount.text.toString().toDouble()
            val description = etDescription.text.toString()
            val date = tvDateSelector.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                when (tableName) {
                    "Income" -> {
                        val income = IncomeTable(amount, date, description)
                        dao.insertToIncome(income)
//                        Income().adapter.notifyDataSetChanged()
                        //AddActivity().finish()
                        //this.finish()
                    }
                    "Expense" -> {
                        val expense = ExpenseTable(amount, date, description)
                        dao.insertToExpense(expense)
//                        Income().adapter.notifyDataSetChanged()
                        //this.finish()
                    }
                }

            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)




//            Toast.makeText(
//                baseContext,
//                "Successfully added in ${spinnerIncomeExpense.selectedItem.toString()}s",
//                Toast.LENGTH_LONG
//            ).show()

        }

    }
}