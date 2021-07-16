package com.example.presentation.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

internal class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {

    override val viewModel: DetailViewModel by viewModel {
        parametersOf(
            intent.getSerializableExtra(DETAIL_MODE_KEY),
            intent.getLongExtra(MONEY_ID_KEY, -1)
        )
    }

    override fun getViewBinding(): ActivityDetailBinding  = ActivityDetailBinding.inflate(layoutInflater)

    companion object {
        const val MONEY_ID_KEY = "MoneyId"
        const val DETAIL_MODE_KEY = "DetailMode"

        const val FETCH_REQUEST_CODE = 10

        fun getIntent(context: Context, detailMode: DetailMode) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(DETAIL_MODE_KEY, detailMode)
            }

        fun getIntent(context: Context, id: Long, detailMode: DetailMode) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(MONEY_ID_KEY, id)
                putExtra(DETAIL_MODE_KEY, detailMode)
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_OK)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun observeData() = viewModel.moneyDetailLiveData.observe(this){
        when(it){

            is MoneyDetailState.UnInitialized -> {
                initViews(binding)
            }
            is MoneyDetailState.Loading -> {
                handleLoadingState()
            }
            is MoneyDetailState.Success -> {
                handleSuccessState(it)
            }
            is MoneyDetailState.Modify -> {
                handleModifyState()
            }
            is MoneyDetailState.Delete -> {
                Toast.makeText(this, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            is MoneyDetailState.Error -> {
                Toast.makeText(this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            is MoneyDetailState.Write -> {
                handleWriteState()

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViews(binding: ActivityDetailBinding) = with(binding) {

        moneyEditText.isEnabled = false
        sepEditText.isEnabled = false
        useEditText.isEnabled = false
        descriptionEditText.isEnabled = false

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = true

        checkBtnClickListener()

        btnSetDate.setOnClickListener {

            setDate()

        }

        deleteButton.setOnClickListener {
            viewModel.deleteMoney()
        }
        modifyButton.setOnClickListener {
            viewModel.setModifyMode()
        }
        updateButton.setOnClickListener {
            val checked = checkTextView.text.toString()
            val date = dateTextView.text.toString()
            val money = moneyEditText.text.toString()
            val separation = sepEditText.text.toString()
            val use = useEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if(money == ""){
                Toast.makeText(this@DetailActivity,"금액을 입력해 주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.writeMoney(
                checked,date,money, separation, use, description
            )
            finish()
        }
    }
    private fun handleLoadingState() = with(binding) {
        progressBar.isGone = false
    }

    private fun handleModifyState() = with(binding) {
        moneyEditText.isEnabled = true
        sepEditText.isEnabled = true
        useEditText.isEnabled = true
        descriptionEditText.isEnabled = true

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = false
    }

    private fun handleWriteState() = with(binding) {
        moneyEditText.isEnabled = true
        sepEditText.isEnabled = true
        useEditText.isEnabled = true
        descriptionEditText.isEnabled = true

        updateButton.isGone = false
    }

    private fun handleSuccessState(state: MoneyDetailState.Success) = with(binding) {
        progressBar.isGone = true

        moneyEditText.isEnabled = false
        sepEditText.isEnabled = false
        useEditText.isEnabled = false
        descriptionEditText.isEnabled = false


        deleteButton.isGone = false
        modifyButton.isGone = false
        updateButton.isGone = true

        val moneyItem = state.moneyItem

        moneyEditText.setText(moneyItem.money)
        sepEditText.setText(moneyItem.separation)
        useEditText.setText(moneyItem.use)
        descriptionEditText.setText(moneyItem.description)
    }

    private fun checkBtnClickListener() = with(binding){

        incomeTextView.setOnClickListener {
            checkTextView.text = "수입"
            incomeTextView.setTextColor(Color.BLUE)
            outcomeTextView.setTextColor(Color.BLACK)

        }

        outcomeTextView.setOnClickListener {
            checkTextView.text = "지출"
            outcomeTextView.setTextColor(Color.RED)
            incomeTextView.setTextColor(Color.BLACK)
        }

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setDate(){
        val today = GregorianCalendar()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val date = today.get(Calendar.DATE)

        val dialog = DatePickerDialog(this,
            { _, y, m, dayOfMonth -> binding.dateTextView.text = "${y}년 ${m+1}월 ${dayOfMonth}일" },year,month,date)

        dialog.show()
    }

}