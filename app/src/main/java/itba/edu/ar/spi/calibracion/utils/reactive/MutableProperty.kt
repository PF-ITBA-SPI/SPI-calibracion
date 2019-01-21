package com.peanutproductionsdev.questdex.utils.reactive

import io.reactivex.Observable
import io.reactivex.observables.ConnectableObservable

open class MutableProperty<T>(initialValue: T) {
    open var value: T = initialValue
        set(value) {
            disposable(value)
            field = value
        }

    private val _observable: Observable<T>
    private val disposable: (T) -> Unit

    init {
        val pair = create<T>()
        _observable = pair.first

        disposable = pair.second
    }

    fun observable(noInitialValue: Boolean = false): Observable<T> {
        if(value==null || noInitialValue)
            return _observable
        else
            return _observable.startWith(value)
    }

    companion object {
        private fun <T> create(): Pair<ConnectableObservable<T>, (T) -> Unit> {
            class CustomDisposable {
                var disposable: ((T) -> Unit)? = null
            }

            val custom = CustomDisposable()
            val observable = Observable.create<T> { emitter ->
                custom.disposable = {
                    emitter.onNext(it)
                }
            }
            val connectable = observable.publish()
            connectable.connect()
            connectable.subscribe()
            return Pair(connectable, custom.disposable!!)
        }
    }

}


