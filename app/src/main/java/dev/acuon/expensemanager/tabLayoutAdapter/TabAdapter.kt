package dev.acuon.expensemanager.tabLayoutAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.acuon.expensemanager.fragments.BalanceFragment
import dev.acuon.expensemanager.fragments.ExpenseFragment
import dev.acuon.expensemanager.fragments.IncomeFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return IncomeFragment()
            1 -> return ExpenseFragment()
            2 -> return BalanceFragment()
        }
        return IncomeFragment()
    }

}