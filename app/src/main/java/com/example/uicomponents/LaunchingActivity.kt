package com.example.uicomponents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_launching.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launching)
        supportActionBar?.hide()
        launchMainActivity()
    }

    private fun launchMainActivity() {
        pbLaunching.show()
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000)
            Log.d("###", "Launching")
            withContext(Dispatchers.Main) {
                pbLaunching.hide()
                startActivity(Intent(this@LaunchingActivity, MainActivity::class.java))
            }
        }
    }
}
