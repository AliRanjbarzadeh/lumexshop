package com.zarinfanavaran.presentation.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
	liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T : ViewModel> Fragment.obtainViewModel(
	owner: ViewModelStoreOwner,
	viewModelClass: Class<T>,
	viewModelFactory: ViewModelProvider.Factory
) = ViewModelProvider(owner, viewModelFactory).get(viewModelClass)


fun JSONObject.toRequestBody(): RequestBody {
	return this.toString().toRequestBody("application/json".toMediaType())
}