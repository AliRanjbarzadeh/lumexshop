package com.zarinfanavaran.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.zarinfanavaran.domain.BuildConfig.SESSION_LOGIN
import com.zarinfanavaran.domain.extensions.isMobile
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentLoginMobileBinding
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
@AndroidEntryPoint
class LoginMobileFragment : BaseFragment<FragmentLoginMobileBinding>(R.layout.fragment_login_mobile) {

	private val viewModel: LoginMobileViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getLogin(), ::initLogin)
		}
	}

	private fun setupUI() {
		binding.txtLaw.text = spannableString(
			mContext = requireContext(),

			firstString = "با ورود یا ثبت نام به شاپ شما ",

			secondString = "شرایط و قوانین",
			secondColor = R.color.color42,

			thirdString = " استفاده از شاپ و حقوق ",

			fourthString = "قوانین حریم شخصی",
			fourthColor = R.color.color42,

			fifthString = " آن میپذیرید"
		)

		binding.btnNext.setOnClickListener {
			val mobile = binding.etMobile.text.toString()
			if (mobile.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.please_enter_mobile), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (!mobile.isMobile()) {
				Toast.makeText(requireContext(), getString(R.string.wrong_mobile), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			hideKeyboard()

			val jsonObject = JSONObject()
			jsonObject.put("mobile_number", mobile)
			val loginBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
//			viewModel.login(loginBody)

			val action = LoginMobileFragmentDirections.loginToVerify(mobile)
			findNavController().navigate(action)
		}

		//set button progress lifecycle
		bindProgressButton(binding.btnNext)

		if (!loadFromSp(SESSION_LOGIN, false)) {
			binding.etMobile.post {
				showInputMethod(binding.etMobile)
			}
		}
	}

	private fun initLoading(isLoading: Boolean) {
		if (isLoading) {
			binding.btnNext.showProgress {
				progressColorRes = R.color.white
				gravity = DrawableButton.GRAVITY_CENTER
			}
		} else {
			binding.btnNext.hideProgress(R.string.send_login_code)
		}
	}

	private fun initLogin(result: NetworkResult<LoginMobile>) {
		if (result is NetworkResult.Success) {
			val action = LoginMobileFragmentDirections.loginToVerify(result.data.mobileNumber)
			findNavController().navigate(action)
		} else if (result is NetworkResult.Error) {
			Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
		}
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.etMobile.clearFocus()
		}
	}
}