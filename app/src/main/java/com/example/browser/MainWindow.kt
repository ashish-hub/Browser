package com.example.browser

import android.app.ActionBar
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_window.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.opengl.ETC1.getHeight
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlin.Exception


private var urlHist = mutableListOf<String>("")
private var urlDataHist = mutableListOf<String>()
private var urlHistIndex = 0
private var editTextUrl : String? = null
class MainWindow : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)




        val onGestureRegisterListener = object : OnGestureRegisterListener(this@MainWindow) {
            override fun onSwipeRight(view: View?) { Log.d("=============================================================", "right") }
            override fun onSwipeLeft(view: View?) { Log.d("=============================================================", "left") }
            override fun onSwipeBottom(view: View?) { Log.d("=============================================================", "bottom") }
            override fun onSwipeTop(view: View?) { Log.d("=============================================================", "top") }
            override fun onClick(view: View?,e:MotionEvent) { Log.d("=============================================================", "click");webView.onTouchEvent(e) }
            override fun onLongClick(view: View?): Boolean { Log.d("=============================================================", "long click")
            return true }
        }


        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient()
        webView.settings.setBuiltInZoomControls(true)
        webView.settings.setDisplayZoomControls(false)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true               // dont know what it does
        //touchevent.setOnTouchListener { view, event ->
        //    Log.d("=============================================================", "webview touch event")
        //    webView.onTouchEvent(event)
        //    false
       // }


        touchevent.setOnTouchListener(onGestureRegisterListener)
        webView.setOnTouchListener{view, event ->
            touchevent.onTouchEvent(event)
            false
        }


        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    // Text has changed, apply filtering?
//                    searchView.layoutParams = Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT,Constraints.LayoutParams.WRAP_CONTENT)
                    val input = searchView.query.toString()
                    if (input == null || input.startsWith("http://") || input.startsWith("https://")) { webView.loadUrl("$input") }
                    else { webView.loadUrl("https://duckduckgo.com/?q=$input") }
                    Log.d("=======================================================================","on query text change")
                    val his = webView.copyBackForwardList()
                    Log.d("=======================================================================back forward list size  ","${his.size}")
                    for (i in 0..his.size) {
                    Log.d("=======================================================================back forward list item   $i    ","${his.getItemAtIndex(i)}")
                    }
                    return false
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchView.clearFocus()
//                    searchView.layoutParams = Constraints.LayoutParams(Constraints.LayoutParams.MATCH_CONSTRAINT,Constraints.LayoutParams.MATCH_CONSTRAINT)
//                    searchView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//                    searchView.setQuery("$editTextUrl", false)
                    Log.d("=======================================================================","on query text submit")
                    return false
                }
            }//Perform te final search
        )

/*        editText.setOnClickListener {
            val input = editText.text.toString()
            if (input == null || input.startsWith("http://") || input.startsWith("https://")) { webView.loadUrl("$input") }
            else { webView.loadUrl("https://duckduckgo.com/?q=$input") }
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager!!.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            editText.setText("$editTextUrl")
            true
        }


        button.setOnClickListener {
            //editText.setText("https://www.computerhope.com/unix/ubash.htm")
            var input = editText.text.toString()
            webView.loadUrl("https://duckduckgo.com/?q=$input")
        }


        button2.setOnClickListener {
            //editText.setText("https://www.computerhope.com/unix/ubash.htm")
            var input = editText.text.toString()
            if (input == null || input.startsWith("http://") || input.startsWith("https://")) {
                Log.d("=============================================================", "url if ")
                }

            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(input))
                startActivity(intent)
                Log.d("=============================================================", "url try ")
            } catch (e: Exception) {
                Log.i("=====================", "shouldOverrideUrlLoading Exception:$e")
                Log.d("=============================================================", "url catch")
            }
            webView.loadUrl("$input")
        }*/


        //button3.setOnClickListener {
        //    startActivity(Intent(this, Temp::class.java).putExtra("data", "${webView.goBack()}"))
        //}
    }

    fun showPopup(view:View) { startActivity(Intent(this@MainWindow, MainActivity::class.java)) }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_left))
//            webView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_right))
            webView.goBack()
            --urlHistIndex
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }


    class MyWebViewClient : WebViewClient() {
        var currentUrl : String? = null
        var nextUrl : String? = null
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Log.d("=============================================================", "url loading")
            editTextUrl = url
            if (url != urlHist[urlHistIndex]) {
                urlHist.add(urlHistIndex, "$url")
                try {
                    while (!urlHist[urlHistIndex+1].isNullOrEmpty()){ urlHist.removeAt(urlHist.size-1) }
                }catch (e:Exception) {
                    Log.d("=========================================================exception","${e.stackTrace}")
                }
                Log.d("============================================================hist", "$urlHist")
            }
            ++urlHistIndex
            currentUrl = url
            Log.d("============================================================hist", "$currentUrl")



            if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false

            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view!!.getContext().startActivity(intent)
                return true
            } catch (e: Exception) {
                Log.i("=====================", "shouldOverrideUrlLoading Exception:$e")
                return true
            }

            //return false

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }

}
