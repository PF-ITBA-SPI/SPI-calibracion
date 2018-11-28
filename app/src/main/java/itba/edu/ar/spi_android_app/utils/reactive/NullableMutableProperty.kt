package com.peanutproductionsdev.questdex.utils.reactive

class Nullable<T>(val value: T?)

class NullableMutableProperty<T>(initialValue: T?): MutableProperty<Nullable<T>>(Nullable(initialValue)) {

    var maybeValue: T?
        set(value) {
            super.value = Nullable(value)
        }
        get() = super.value.value

    fun notNullObservable(noInitial: Boolean = false) = observable(noInitial)
            .filter { it.value != null}.map { it.value!! }

    fun <R> flatMap(mapper: (T?)-> R?): NullableMutableProperty<R> {
        val newProperty: NullableMutableProperty<R> = NullableMutableProperty(mapper(maybeValue))
        val disp = observable().subscribe { newProperty.value = Nullable(mapper(it.value)) }
        observable().doOnDispose { disp.dispose() }
        return newProperty
    }

}