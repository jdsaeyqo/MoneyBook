package com.example.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Money
import com.example.presentation.databinding.ViewholderMoneyItemBinding
import java.text.DecimalFormat

class MoneyAdapter : RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder>() {

    private var moneyList: List<Money> = listOf()
    private lateinit var moneyItemClickListener: (Money) -> Unit


    inner class MoneyViewHolder(
        private val binding: ViewholderMoneyItemBinding,
        val moneyItemClickListener: (Money) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Money) = with(binding) {

            val decimal = DecimalFormat("###,###")
            val money = decimal.format(data.money.toInt()).toString()
            titleTextView.text = money

            if(data.checked == "수입"){
                titleTextView.setTextColor(Color.BLUE)
            }else{
                titleTextView.setTextColor(Color.RED)
            }
            useTextView.text = data.use
            dateTextView.text = data.date
        }

        fun bindViews(data: Money) = with(binding) {

            root.setOnClickListener {
                moneyItemClickListener(data)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {

        val view =
            ViewholderMoneyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoneyViewHolder(view, moneyItemClickListener)

    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        holder.bindData(moneyList[position])
        holder.bindViews(moneyList[position])
    }

    override fun getItemCount(): Int = moneyList.size

    fun setMoneyList(
        moneyList: List<Money>,
        moneyItemClickListener: (Money) -> Unit,

    ) {

        this.moneyList = moneyList
        this.moneyItemClickListener = moneyItemClickListener
        notifyDataSetChanged()

    }
}