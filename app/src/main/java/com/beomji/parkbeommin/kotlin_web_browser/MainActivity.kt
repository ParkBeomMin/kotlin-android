package com.beomji.parkbeommin.kotlin_web_browser

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            // 자바스크립트 기능이 잘 동작하도록 설정
            settings.javaScriptEnabled = true
            // 자체 웹 브라우저가 실행되지 않고, 웹뷰에서 페이지가 표시되도록 설정
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")

        // 에딧텍스트가 선택디고 글자가 입력될 때마다 호출
        urlEdt.setOnEditorActionListener { _, actionId, _ ->
            // 반응한 뷰, 액션ID, 이벤트
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(urlEdt.text.toString())
                true
            } else {
                false
            }
        }

        // 컨텍스트 메뉴가 표시될 대상 뷰 설정
        registerForContextMenu(webView)
    }

    // 뒤로가기 버튼 이벤트 재정의
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://daum.com")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-2434-7280")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
//                val intent = Intent(Intent.ACTION_SEND)
//                intent.apply {
//                    type = "text/plain"
//                    putExtra(Intent.EXTRA_TEXT, "보낼 문자열")
//                    var chooser = Intent.createChooser(intent, null)
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivity(intent)
//                    }
//                }

                sendSMS("010-0000-0000", webView.url)
                return true
            }
            R.id.action_email -> {
//                val intent = Intent(Intent.ACTION_SEND)
//                intent.putExtra(Intent.EXTRA_EMAIL, "club20608@gmail.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "보내질 email 제목");
//                intent.putExtra(Intent.EXTRA_TEXT, "보낼 email 내용을 미리 적어 놓을 수 있습니다.\n");

                email("club20608@gmail.com", "Good Site!", webView.url)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                share(webView.url)
                return true
            }
            R.id.action_browser -> {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("http://example.com")
//                if (intent.resolveActivity(packageManager) != null) {
//                    startActivity(intent)
//                }

                browse(webView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
