package com.example.originalmultilingualapp

import android.os.Handler
import android.os.SystemClock

class StopWatch(private val mainActivity: MainActivity):Runnable {

    /**
     * ストップウォッチ状態列挙クラス
     */
    enum class StopWatchState(val value:Int){
        IsStarted(0),IsStopped(1),IsReset(2)
    }

    private val stopWatchHandler = Handler() //ストップウォッチハンドラ

    private val delay = 50L //ストップウォッチ処理間隔(ミリ)

    var startTime = -1L //ストップウォッチ開始時刻
    var elapsedTime = -1L //経過時間(ミリ)
    var second = -1
    var minute = -1
    var hour = -1

    private var state:Int = StopWatchState.IsReset.value //ストップウォッチの状態

    override fun run() {
        //経過時間を更新
        elapsedTime = SystemClock.uptimeMillis() - startTime
        second = (elapsedTime / 1000).toInt()
        minute = second / 60
        hour = minute / 60

        mainActivity.updateTimeText() //タイムテキスト更新

        //自身をdelay後に呼ぶ
        stopWatchHandler.postDelayed(this, delay)
    }

    /**
     * 開始メソッド
     */
    fun start(){
        when(state){ //ストップウォッチ開始時刻を設定
            StopWatchState.IsReset.value -> { startTime = SystemClock.uptimeMillis() }
            StopWatchState.IsStopped.value -> { startTime = SystemClock.uptimeMillis() - elapsedTime }
        }
        stopWatchHandler.post(this) //ストップウォッチ開始
        state = StopWatchState.IsStarted.value
    }

    /**
     * 停止メソッド
     */
    fun stop(){
        stopWatchHandler.removeCallbacks(this) //ストップウォッチ停止
        state = StopWatchState.IsStopped.value
    }

    /**
     * リセットメソッド
     */
    fun reset(){
        state = StopWatchState.IsReset.value
    }
}