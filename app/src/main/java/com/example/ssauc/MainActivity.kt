package com.example.ssauc

import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.ssauc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // WebView 설정
        val webView = binding.webView
        webView.settings.apply {
            javaScriptEnabled = true  // JavaScript 활성화
            domStorageEnabled = true  // 로컬 저장소 (캐싱) 활성화
            cacheMode = WebSettings.LOAD_DEFAULT  // 캐시 기본 설정
        }

        // WebViewClient 설정 (앱 내부에서 웹 탐색)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.url?.let { view?.loadUrl(it.toString()) }
                return true
            }

            // 캐시 문제 방지
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                view?.loadUrl("about:blank")  // 빈 페이지 로드 (에러 회피)
            }
        }

        // 캐시 삭제 (필요 시)
        webView.clearCache(true)

        // 웹 페이지 로드
        webView.loadUrl("http://ssauc.shop")  // 원하는 URL로 변경
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}