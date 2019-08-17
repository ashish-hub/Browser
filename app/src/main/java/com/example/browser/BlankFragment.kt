package com.example.browser


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main_window.*
import kotlinx.android.synthetic.main.fragment_blank.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        view!!.findViewById<WebView>(R.id.webView1).webViewClient = MyWebViewClient()
        view!!.findViewById<WebView>(R.id.webView1).settings.javaScriptEnabled = true
        view!!.findViewById<WebView>(R.id.webView1).loadUrl("https://www.duckduckgo.com/")

        return view
    }

    class MyWebViewClient : WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button3.setOnClickListener { Navigation.createNavigateOnClickListener(R.id.action_blankFragment_to_blankFragment2) }
        button3.setOnClickListener {
            it.findNavController().navigate(R.id.blankFragment2)
        }
        button5.setOnClickListener { Navigation.createNavigateOnClickListener(R.id.blankFragment2) }
        button5.setOnClickListener {
            val bundle:Bundle

            it.findNavController().navigate(R.id.blankFragment2)
        }
    }


}
