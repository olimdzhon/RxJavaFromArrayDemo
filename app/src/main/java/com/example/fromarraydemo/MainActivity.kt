package com.example.fromarraydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val nums = arrayOf(1,2, 3)
    private lateinit var myObservable: Observable<Array<Int>>
    private lateinit var myObserver: DisposableObserver<Array<Int>>

    private lateinit var textView: TextView
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)
        myObservable = Observable.fromArray(nums)

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<Array<Int>> {

        myObserver = object : DisposableObserver<Array<Int>>() {

            override fun onNext(s: Array<Int>) {
                Log.d("MAN","onNext: $s")
                textView.text = s[0].toString()
            }

            override fun onError(e: Throwable) {
                Log.d("MAN","onError")
            }

            override fun onComplete() {
                Log.d("MAN","onComplete")
            }

        }

        return myObserver
    }
}