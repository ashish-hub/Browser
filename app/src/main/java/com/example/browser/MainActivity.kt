package com.example.browser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        action_search.setIconifiedByDefault(false)
        action_search.isSubmitButtonEnabled = true

        button.setOnClickListener { startActivity(Intent(this,MainWindow::class.java)) }
    }


    companion object {
        class adaptor : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getCount(): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
}
