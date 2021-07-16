package com.example.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Money
import com.example.presentation.R
import com.example.presentation.databinding.ViewholderMoneyItemBinding
import java.text.DecimalFormat

class MoneyAdapter : RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder>() {

    private var moneyList: List<Money> = listOf()
    private lateinit var moneyItemClickListener: (Money) -> Unit
    private lateinit var moneyCheckListener: (Money) -> Unit

    inner class MoneyViewHolder(
        private val binding: ViewholderMoneyItemBinding,
        val moneyItemClickListener: (Money) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Money) = with(binding) {

            val decimal = DecimalFormat("###,###")
            val money = decimal.format(data.money.toInt()).toString()
            checkBox.text = money

            if(data.checked == "수입"){
                checkBox.setTextColor(Color.BLUE)
            }else{
                checkBox.setTextColor(Color.RED)
            }
            useTextView.text = data.use
            dateTextView.text = data.date
            checkMoneyComplete(data.hasCompleted)
        }

        fun bindViews(data: Money) = with(binding) {
            checkBox.setOnClickListener {
                moneyCheckListener(
                    data.copy(hasCompleted = binding.checkBox.isChecked)
                )
                checkMoneyComplete(binding.checkBox.isChecked)
            }
            root.setOnClickListener {
                moneyItemClickListener(data)
            }

        }

        private fun checkMoneyComplete(isChecked: Boolean) = with(binding) {

            checkBox.isChecked = isChecked
            container.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if (isChecked) {
                        R.color.teal_200
                    } else {
                        R.color.white
                    }
                )
            )

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
        moneyCheckListener: (Money) -> Unit
    ) {

        this.moneyList = moneyList
        this.moneyItemClickListener = moneyItemClickListener
        this.moneyCheckListener = moneyCheckListener
        notifyDataSetChanged()

    }
}