package com.peanutproductionsdev.questdex.utils.reactive

import io.reactivex.Observable

class Property<T>(from: MutableProperty<T>) {
    private val property = from
    var value: T = property.value
        get() = property.value
        private set

    val observable: Observable<T>
        get() = property.observable()
}