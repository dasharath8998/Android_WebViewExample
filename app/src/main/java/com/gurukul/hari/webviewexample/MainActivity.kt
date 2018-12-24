package com.gurukul.hari.webviewexample

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() , View.OnClickListener{

    var stack = Stack<String>()
    var url:String? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener (this@MainActivity)
        btnFb.setOnClickListener (this@MainActivity)
        btnGoogle.setOnClickListener (this@MainActivity)
        btnYoutube.setOnClickListener (this@MainActivity)
        btnHtml.setOnClickListener (this@MainActivity)

        //wView.webViewClient = WebViewClient()
        wView.settings.javaScriptEnabled = true
        wView.settings.builtInZoomControls = false

        val pDialog = ProgressDialog(this@MainActivity)
        pDialog.setTitle("Message")
        pDialog.setMessage("Please wait page is loading...")

        wView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                pDialog.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pDialog.dismiss()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                Toast.makeText(this@MainActivity,view!!.url,Toast.LENGTH_LONG).show()
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    override fun onBackPressed() {
        if(stack.size > 1){
            stack.pop()
        }
        url = stack.peek()
        wView.loadUrl(url)
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.btnSearch -> {
                    if(etUrl.text.toString().startsWith("http://",false)) {
                        wView.loadUrl(etUrl.text.toString())
                        stack.add(etUrl.text.toString())
                    }else{
                        wView.loadUrl("http://"+etUrl.text.toString())
                        stack.add("http://"+etUrl.text.toString())
                    }
                }

                R.id.btnFb -> {
                    wView.loadUrl("http://www.facebook.com")
                    stack.add("http://www.facebook.com")
                }

                R.id.btnGoogle -> {
                    wView.loadUrl("http://www.google.com")
                    stack.add("http://www.google.com")
                }

                R.id.btnYoutube -> {
                    wView.loadUrl("http://www.youtube.com")
                    stack.add("http://www.youtube.com")
                }

                R.id.btnHtml -> {
                    wView.loadUrl("file:///android_asset/welcome.html")
                    stack.add("file:///android_asset/welcome.html")
                }
            }
        }
    }
}
