package itba.edu.ar.spi_android_app.utils

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.onUI(): Observable<T> =
        this.observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.toUI(): Observable<T> =
        this.subscribeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.schedule(): Observable<T> =
        this.subscribeOn(Schedulers.io())

fun <T> Observable<T>.scheduleRequest(): Observable<T> =
        this.schedule().onUI()

fun <T,U> Observable<T>.combineLatest(with: Observable<U>): Observable<Pair<T,U>> =
        Observables.combineLatest(this,with,{i,o -> kotlin.Pair(i, o) })

fun <T,U,S> Observable<Pair<T,U>>.combineLatestPair(with: Observable<S>): Observable<Triple<T,U,S>> =
        Observables.combineLatest(this,with,{ (first, second), o -> kotlin.Triple(first, second, o) })

fun <T,U> Observable<T>.zip(with: Observable<U>): Observable<Pair<T,U>> =
        Observables.zip(this,with,{i,o -> kotlin.Pair(i, o) })

fun <T,U,S> Observable<Pair<T,U>>.zipPair(with: Observable<S>): Observable<Triple<T,U,S>> =
        Observables.zip(this,with,{ (first, second), o -> kotlin.Triple(first, second, o) })

fun <T> Observable<T>.stopRefreshing(activity: Activity, swipeRefreshLayout: SwipeRefreshLayout) =
        this.doOnNext { swipeRefreshLayout.stopUI(activity) }
                .doOnError { swipeRefreshLayout.stopUI(activity) }


fun Disposable.bag(bag: MutableCollection<Disposable>) = bag.add(this)