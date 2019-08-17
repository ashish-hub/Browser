package com.example.browser


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_blank_fragment2.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BlankFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false)

        view!!.findViewById<WebView>(R.id.webView2).webViewClient = BlankFragment.MyWebViewClient()
        view!!.findViewById<WebView>(R.id.webView2).settings.javaScriptEnabled = true
        view!!.findViewById<WebView>(R.id.webView2).loadUrl("https://duckduckgo.com/")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button4.setOnClickListener { Navigation.createNavigateOnClickListener(R.id.action_blankFragment2_to_blankFragment) }
        button4.setOnClickListener {
            it.findNavController().navigate(R.id.blankFragment)
        }
        button6.setOnClickListener { Navigation.createNavigateOnClickListener(R.id.action_blankFragment2_to_blankFragment) }
        button6.setOnClickListener {
            it.findNavController().navigate(R.id.blankFragment)
        }
    }

}
