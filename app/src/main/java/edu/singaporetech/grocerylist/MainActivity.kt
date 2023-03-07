package edu.singaporetech.grocerylist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Lab 03: Displaying Lists and Activity Lifecycle
 */
class MainActivity : AppCompatActivity() {
    companion object {
        var checkedArray: MutableList<String> = mutableListOf()
    }
    val TAG: String = "MainActivity"
    //TODO declare the grocery list as an ArrayList?
    val groceryArray = arrayOf<String>("Cilantro", "Beans", "Cheese", "Oil", "Tomato", "Salt", "Pepper", "Flour", "Corn", "Garlic", "Lime", "Onion", "Rice", "Cabbage", "Avocado")
    //TODO implement the adapter for the recyclerview IN A SEPARATE CLASS
    private lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO implement the recyclerview
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewGroceryList)
        recyclerAdapter = RecyclerAdapter(groceryArray)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()
    }

    //TODO override all the activity callbacks, don't forget to call super!
    override fun onStart() {
        Log.d(TAG, "onStart()")
        super.onStart()
    }
    override fun onRestart() {
        Log.d(TAG, "onRestart()")
        super.onRestart()
    }
    override fun onResume() {
        Log.d(TAG, "onResume()")
        super.onResume()
    }
    override fun onPause() {
        Log.d(TAG, "onPause()")
        super.onPause()
    }
    override fun onStop() {
        Log.d(TAG, "onStop()")
        super.onStop()
    }
    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()
    }
}