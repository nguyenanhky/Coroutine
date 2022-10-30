package kynvfhn.fsoft.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import kynvfhn.fsoft.coroutinedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private var isShowedResult =false
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            requestData()
            binding.processBar.visibility = View.VISIBLE
//            requestDataWithSuspend()

            Log.d(TAG, "onCreate: ")
        }

        binding.btnIncrease.setOnClickListener {
            if(!isShowedResult){
                count++
                binding.txtData.text = "count :$count"
            }
        }
    }
    private  fun requestDataWithSuspend() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            Log.d(TAG, "requestDataWithSuspend: ${Thread.currentThread().name}")
            val startExtuteTime = System.currentTimeMillis();
            delay(6000L)
            val  executeTime = System.currentTimeMillis()-startExtuteTime;
            binding.processBar.visibility = View.GONE
            Log.d(TAG, "Exe Time: "+executeTime)
            isShowedResult = true
            binding.txtData.text = "data from server"
        }
    }

    private fun requestData(){
        Log.d(TAG, "requestData on : ${Thread.currentThread().name} ")
        Thread.sleep(10000L)
        binding.processBar.visibility = View.GONE
        isShowedResult = true
        binding.txtData.text = "data from server"
    }
}