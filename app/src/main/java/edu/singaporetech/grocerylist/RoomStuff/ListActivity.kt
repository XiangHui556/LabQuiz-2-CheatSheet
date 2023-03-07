package edu.singaporetech.madata

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.singaporetech.madata.databinding.ActivityListBinding

/**
 * The main view class - entry point for MAData app.
 * as part of
 * Lab 04: MAD Data Management
 *
 * Author: your prof
 * Updated: the day before your lab.
 *
 * (just in case you're wondering, MAD stands for Modern Android Development)
 */
class ListActivity : AppCompatActivity() {
    // view binding to access view elements

    private lateinit var binding: ActivityListBinding
    private lateinit var fourDigitListAdapter: FourDigitListAdapter
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var fourDigitViewModelDataStore: FourDigitViewModelDataStore

    private val fourDigitViewModel: FourDigitViewModel by viewModels {
        TaskItemModelFactory((application as FourDigitApplication).repository)
    }

    /**
     * Initialize view and callbacks during activity creation.
     * @param savedInstanceState the usual bundle of joy
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // initialize view binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        var list = listOf<FourDigit>()
        fourDigitViewModel.digitItems.observe(this){
            list = it
        }
        val recyclerView: RecyclerView = findViewById(R.id.listViewFourDigits)
        fourDigitListAdapter = FourDigitListAdapter(list)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = fourDigitListAdapter
        fourDigitViewModelDataStore = ViewModelProvider(this)[FourDigitViewModelDataStore::class.java]
        userPreferencesRepository = UserPreferencesRepository(this)
        fourDigitListAdapter.notifyDataSetChanged()
        setRecyclerView()
        checkGrid()

        binding.switchGrid.setOnCheckedChangeListener() { _, isChecked ->
            when (isChecked) {
                true -> {
                    binding.listViewFourDigits.layoutManager = GridLayoutManager(this, 4)
                    fourDigitViewModelDataStore.updateTest(true)
                }
                false -> {
                    binding.listViewFourDigits.layoutManager = LinearLayoutManager(this)
                    fourDigitViewModelDataStore.updateTest(false)
                }
            }
        }

        binding.resetDataButton.setOnClickListener() {
            fourDigitViewModel.dropTable()
            setRecyclerView()
        }
    }

    private fun checkGrid() {
        binding.apply {
            fourDigitViewModelDataStore.test.observe(this@ListActivity) {isChecked->
                when(isChecked){
                    true -> {
                        listViewFourDigits.layoutManager = GridLayoutManager(this@ListActivity, 4)
                        switchGrid.isChecked = true
                    }
                    false -> {
                        listViewFourDigits.layoutManager = LinearLayoutManager(this@ListActivity)
                        switchGrid.isChecked = false
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView()
    {
        fourDigitViewModel.digitItems.observe(this){
            binding.listViewFourDigits.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = FourDigitListAdapter(it)
                fourDigitListAdapter.notifyDataSetChanged()
            }
        }
    }
}