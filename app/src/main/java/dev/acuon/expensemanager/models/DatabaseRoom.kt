package dev.acuon.expensemanager.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [IncomeTable::class, ExpenseTable::class], version = 1)
abstract class DatabaseRoom: RoomDatabase() {

    abstract fun getMoneyDAO(): MoneyDAO

    companion object {

        private var INSTANCE: DatabaseRoom? = null

        fun getDatabaseObject(context: Context): DatabaseRoom {
            if(INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext, DatabaseRoom::class.java, "money"
                )
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
                return INSTANCE!!
            } else {
                return INSTANCE!!
            }
        }
    }
}