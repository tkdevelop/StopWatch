package com.example.originalmultilingualapp

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init() //初期化
    }

    /**
     * ボタンテキストカラー列挙クラス
     */
    enum class BtnTextColor(val value:String){
        IsEnabled("#FFFFFF"),IsDisabled("#282830")
    }

    private val stopWatch = StopWatch(this) //ストップウォッチ

    /**
     * 初期化メソッド
     */
    private fun init(){
        val btnClickedEffectSound = MediaPlayer.create(this,R.raw.enter15) //ボタンクリック時効果音

        //スタートボタンクリックリスナ
        startBtn.setOnClickListener {
            stopWatch.start() //ストップウォッチ開始

            startBtn.isEnabled = false
            stopBtn.isEnabled = true
            resetBtn.isEnabled = false
            startBtn.setTextColor(Color.parseColor(BtnTextColor.IsDisabled.value))
            stopBtn.setTextColor(Color.parseColor(BtnTextColor.IsEnabled.value))
            resetBtn.setTextColor(Color.parseColor(BtnTextColor.IsDisabled.value))

            if(btnClickedEffectSound.isPlaying) {
                btnClickedEffectSound.stop()
            }
            btnClickedEffectSound.start() //効果音を再生
        }
        //ストップボタンクリックリスナ
        stopBtn.setOnClickListener {
            stopWatch.stop() //ストップウォッチ停止

            startBtn.isEnabled = true
            stopBtn.isEnabled = false
            resetBtn.isEnabled = true
            startBtn.setTextColor(Color.parseColor(BtnTextColor.IsEnabled.value))
            stopBtn.setTextColor(Color.parseColor(BtnTextColor.IsDisabled.value))
            resetBtn.setTextColor(Color.parseColor(BtnTextColor.IsEnabled.value))

            if(btnClickedEffectSound.isPlaying) {
                btnClickedEffectSound.stop()
            }
            btnClickedEffectSound.start() //効果音を再生
        }
        //リセットボタンクリックリスナ
        resetBtn.setOnClickListener {
            stopWatch.reset() //ストップウォッチリセット

            hourTextView.text = "00"
            minuteTextView.text = "00"
            secondTextView.text = "00"
            millisecondTextView.text = "000"

            startBtn.isEnabled = true
            stopBtn.isEnabled = false
            resetBtn.isEnabled = false
            startBtn.setTextColor(Color.parseColor(BtnTextColor.IsEnabled.value))
            stopBtn.setTextColor(Color.parseColor(BtnTextColor.IsDisabled.value))
            resetBtn.setTextColor(Color.parseColor(BtnTextColor.IsDisabled.value))

            if(btnClickedEffectSound.isPlaying) {
                btnClickedEffectSound.stop()
            }
            btnClickedEffectSound.start() //効果音を再生
        }

        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.city) //背景動画Uri
        backgroundVideoView.setVideoURI(videoUri) //背景動画を設定
        backgroundVideoView.setOnPreparedListener{backgroundVideoView.start()} //背景動画ビュープリペアードリスナ
        backgroundVideoView.setOnCompletionListener { backgroundVideoView.start() } //背景動画ビューコンプリーションリスナ
    }

    /**
     * タイムテキスト更新メソッド
     */
    fun updateTimeText(){
        //時間を更新表示
        millisecondTextView.text = "%03d".format(stopWatch.elapsedTime.toInt() % 1000)
        secondTextView.text = "%02d".format(stopWatch.second % 60)
        minuteTextView.text = "%02d".format(stopWatch.minute % 60)
        hourTextView.text = "%02d".format(stopWatch.hour) //24時間以上の場合も有り得るので24時間表記にしない
    }
}
