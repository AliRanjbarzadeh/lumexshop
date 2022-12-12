package com.zarinfanavaran.presentation.base

import androidx.lifecycle.ViewModel
import com.zarinfanavaran.presentation.util.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
abstract class BaseViewModel(private val dispatchers: DispatchersProvider) : ViewModel(), CoroutineScope {
	override val coroutineContext: CoroutineContext
		get() = dispatchers.getMain() + SupervisorJob()

	fun execute(job: suspend () -> Unit) = launch {
		withContext(dispatchers.getIO()) { job.invoke() }
	}
}