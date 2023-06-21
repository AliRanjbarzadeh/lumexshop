package com.zarinfanavaran.presentation.util


/*=============variables==============*/
val SESSION_LOGOUT_KEY = "logout"

/*=============methods==============*/
fun getRandomString(length: Int): String {
	val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
	return (1..length)
			.map { allowedChars.random() }
			.joinToString("")
}