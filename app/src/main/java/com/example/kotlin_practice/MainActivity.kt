package com.example.kotlin_practice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.kotlin_practice.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var speechRecognizer:SpeechRecognizer? = null
    private val REQUEST_CODE = 1
    lateinit var tts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        tts = TextToSpeech(binding.root.context,TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                //사용할 언어를 설정
                val result = tts.setLanguage(Locale.KOREA);
                //언어 데이터가 없거나 혹은 언어가 지원하지 않으면...
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(binding.root.context, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    //음성 톤
                    tts.setPitch(0.7f);
                    //읽는 속도
                    tts.setSpeechRate(1.2f);
                }
            }
        })


        if (Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET, android.Manifest.permission.RECORD_AUDIO), REQUEST_CODE)
        binding.STTButton.setOnClickListener { startSTT() }

        binding.button.setOnClickListener {
            tts.speak(binding.textView.text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    private  fun startSTT() {
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(recognitionListener())
            startListening(speechRecognizerIntent)
        }

    }
    /***
     *  SpeechToText 기능 세팅
     */
    private fun recognitionListener() = object : RecognitionListener {

        override fun onReadyForSpeech(params: Bundle?) = Toast.makeText(this@MainActivity, "음성인식 시작", Toast.LENGTH_SHORT).show()

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            when(error) {
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Toast.makeText(this@MainActivity, "퍼미션 없음", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResults(results: Bundle) {
            Toast.makeText(this@MainActivity, "음성인식 종료", Toast.LENGTH_SHORT).show()
            binding.textView.text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0]
            if("오른쪽" in results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0]){
                Log.d("LOG_CHECK", "${results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!!}")
                binding.qq.x = binding.qq.x+10F
            }
        }
    }
}
