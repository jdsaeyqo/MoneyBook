package com.example.presentation.list

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.adapter.MoneyAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityListBinding
import com.example.presentation.detail.DetailActivity
import com.example.presentation.detail.DetailMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext


internal class ListActivity : BaseActivity<ListViewModel, ActivityListBinding>() , CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private val adapter = MoneyAdapter()

    override val viewModel: ListViewModel by viewModel()

    override fun getViewBinding() = ActivityListBinding.inflate(layoutInflater)


    private fun initViews() = with(binding){
        recyclerView.layoutManager = LinearLayoutManager(this@ListActivity,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

        addMoneyButton.setOnClickListener {
            startActivityForResult(
                DetailActivity.getIntent(this@ListActivity, DetailMode.WRITE),
                DetailActivity.FETCH_REQUEST_CODE
            )
        }

    }

    override fun observeData() {
        viewModel.moneyListLiveData.observe(this){
            when(it){
                is MoneyState.UnInitialized -> {
                    initViews()
                }
                is MoneyState.Loading -> {
                    handleLoadingState()
                }
                is MoneyState.Success -> {
                    handleSuccessState(it)
                }
                is MoneyState.Error -> {

                }
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: MoneyState.Success) = with(binding) {
        refreshLayout.isEnabled = state.moneyList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if (state.moneyList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
            incomeInfo.text = "0"
            outcomeInfo.text = "0"
            resultInfo.text = "0"
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setMoneyList(
                state.moneyList,
                moneyItemClickListener = {
                    startActivityForResult(
                        DetailActivity.getIntent(this@ListActivity, it.id, DetailMode.DETAIL),
                        DetailActivity.FETCH_REQUEST_CODE
                    )
                }, moneyCheckListener = {
                    viewModel.updateEntity(it)
                }
            )

            val result = viewModel.setMoneyResult(state.moneyList)
            incomeInfo.text = result[0]
            outcomeInfo.text = result[1]
            resultInfo.text = result[2]


        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == DetailActivity.FETCH_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            viewModel.fetchData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.action_delete_all -> {
                viewModel.deleteAll()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu,menu)
        return true
    }

}