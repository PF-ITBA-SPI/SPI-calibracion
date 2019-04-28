package ar.edu.itba.spi.calibracion.Activities.map

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ar.edu.itba.spi.calibracion.api.models.Floor

/**
 * ViewModel shared between map view and floor selector fragments, used to communicate between them.
 * Shared data includes floor numbers of current building and currently selected floor number.
 */
class MapViewModel : ViewModel() {
    var floors = MutableLiveData<List<Floor>>()
    var selectedFloorNumber = MutableLiveData<Int>()
    var isOffline = MutableLiveData<Boolean>().apply { value = false }
    var isLocationUnknown = MutableLiveData<Boolean>().apply { value = false }
    var isChangingOverlay = MutableLiveData<Boolean>().apply { value = false }
}
