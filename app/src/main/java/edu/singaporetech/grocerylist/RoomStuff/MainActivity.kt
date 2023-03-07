package edu.singaporetech.madata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import edu.singaporetech.madata.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels

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
class MainActivity : AppCompatActivity() {
    // view binding to access view elements

    private lateinit var binding: ActivityMainBinding
    private val fourDigitViewModel: FourDigitViewModel by viewModels {
        TaskItemModelFactory((application as FourDigitApplication).repository)
    }

    /**
     * Initialize view and callbacks during activity creation.
     * @param savedInstanceState the usual bundle of joy
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize view binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // TODO Show the latest 4D number on screen,
        //      - create other classes to implement android architecture components
        //      - e.g., you'd likely talk to a ViewModel for the four-digit data
        binding.generateButton.setOnClickListener() {
            val randomFourDigit = generateNumber()
            binding.textViewFourDigitNum.text = randomFourDigit.toString()
            var list = listOf<FourDigit>()
            fourDigitViewModel.digitItems.observe(this){
                list = it
            }
            val newDigit = FourDigit(randomFourDigit, list.size+1)
            fourDigitViewModel.addDigits(newDigit)
        }
    }
    //TODO implement the callback for "Generate and Store" button
    private fun generateNumber(): Int {
        return Utils.generateRandomFourDigitNumber()
    }

    //TODO implement the menu for selecting the ListActivity.
    //     Hint: you'll need to implement an onOptionsItemSelected()
    //     override to invoke the proper intent.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.actionDigitList) {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            return true
        }
        return false
    }

    /**
     * class level shared static stuff....
     */
    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}