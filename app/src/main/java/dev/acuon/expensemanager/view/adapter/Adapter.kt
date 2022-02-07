package dev.acuon.expensemanager.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.expensemanager.MainActivity
import dev.acuon.expensemanager.R
import dev.acuon.expensemanager.models.ExpenseTable
import dev.acuon.expensemanager.models.IncomeTable
import kotlinx.android.synthetic.main.item_layout.view.*

class MoneyIncomeAdapter(
    private var list: MutableList<IncomeTable>,
    private var onItemClickListener: IncomeClickListener
) :
    RecyclerView.Adapter<MoneyIncomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyIncomeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MoneyIncomeViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MoneyIncomeViewHolder, position: Int) {
        val money = list[position]
        holder.setData(money)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

// view Holder
class MoneyIncomeViewHolder(itemView: View, var onItemClickListener: IncomeClickListener) :
    RecyclerView.ViewHolder(itemView) {

    fun setData(money: IncomeTable) {
        itemView.apply {
            tvAmount.text = money.amount.toString()
            tvDescription.text = money.description
            tvDate.text = MainActivity().date(money.date)
            tvMonth.text = MainActivity().month(money.date)

            ibEdit.setOnClickListener {
                onItemClickListener.editClick(money, adapterPosition)
            }

            ibDelete.setOnClickListener {
                onItemClickListener.deleteClick(money, adapterPosition)
            }
        }
    }
}

class MoneyExpenseAdapter(
    private var list: MutableList<ExpenseTable>,
    private var onItemClickListener: ExpenseClickListener
) :
    RecyclerView.Adapter<MoneyExpenseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyExpenseViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MoneyExpenseViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MoneyExpenseViewHolder, position: Int) {
        val money = list[position]
        holder.setData(money)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

// view Holder
class MoneyExpenseViewHolder(itemView: View, var onItemClickListener: ExpenseClickListener) :
    RecyclerView.ViewHolder(itemView) {

    fun setData(money: ExpenseTable) {
        itemView.apply {
            tvAmount.text = money.amount.toString()
            tvDescription.text = money.description
//            tvDate.text = money.date
            tvDate.text = MainActivity().date(money.date)
            tvMonth.text = MainActivity().month(money.date)

            ibEdit.setOnClickListener {
                onItemClickListener.editClick(money, adapterPosition)
            }

            ibDelete.setOnClickListener {
                onItemClickListener.deleteClick(money, adapterPosition)
            }
        }
    }


}