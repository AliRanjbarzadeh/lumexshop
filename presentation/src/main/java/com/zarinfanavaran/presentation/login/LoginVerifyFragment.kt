package com.zarinfanavaran.presentation.login

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.zarinfanavaran.domain.BuildConfig
import com.zarinfanavaran.domain.BuildConfig.SESSION_TOKEN
import com.zarinfanavaran.domain.BuildConfig.USER
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.models.LoginMobile
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.BaseObject
import com.zarinfanavaran.presentation.databinding.FragmentLoginVerifyBinding
import com.zarinfanavaran.presentation.util.getRandomString
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
@AndroidEntryPoint
class LoginVerifyFragment : BaseFragment<FragmentLoginVerifyBinding>(R.layout.fragment_login_verify) {

	private val viewModel: LoginVerifyViewModel by viewModels()

	private val args: LoginVerifyFragmentArgs by navArgs()

	private var isTimerFinished = false
	private val mTimer = object : CountDownTimer(120000, 1000) {
		override fun onTick(millisUntilFinished: Long) {
			val seconds = millisUntilFinished / 1000

			val minute = seconds / 60
			val minuteString = if (minute < 10) {
				"0$minute"
			} else {
				minute.toString()
			}

			val secondsString = if (seconds - (minute * 60) < 10) {
				"0${(seconds - (minute * 60))}"
			} else {
				(seconds - (minute * 60)).toString()
			}

			binding.btnSendAgain.text = String.format(getString(R.string.send_again_timer), "$minuteString:$secondsString")
		}

		override fun onFinish() {
			binding.btnSendAgain.text = getString(R.string.send_again)
			isTimerFinished = true
		}

	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun onDestroy() {
		mTimer.cancel()
		super.onDestroy()
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.etVerifyCode.clearFocus()
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getVerify(), ::initVerify)
			observe(getResend(), ::initResend)
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

		//set mobile text
		binding.txtMobile.text = String.format(getString(R.string.mobile_verify), args.mobile)

		//start timer
		mTimer.start()

		//send again
		binding.btnSendAgain.setOnClickListener {
			if (!isTimerFinished) {
				Toast.makeText(requireContext(), getString(R.string.wait_until_timer_finish), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val jsonObject = JSONObject()
			jsonObject.put("mobile_number", args.mobile)
			val resendBody = jsonObject.toString().toRequestBody(BaseObject.jsonMediaType)
			viewModel.resend(resendBody)
		}

		//edit mobile
		binding.btnEditMobile.setOnClickListener { back() }

		//verify click
		binding.btnNext.setOnClickListener {
			val verifyCode = binding.etVerifyCode.text.toString()
			if (verifyCode.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.login_to_shop), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			hideKeyboard()

			val cartToken = getRandomString(100)
			saveToSp(BuildConfig.SESSION_CART_TOKEN, cartToken)

			val jsonObject = JSONObject()
			jsonObject.put("mobile_number", args.mobile)
			jsonObject.put("otp", verifyCode)
			val verifyBody = jsonObject.toString().toRequestBody(BaseObject.jsonMediaType)
//			viewModel.verify(verifyBody)

			saveToSp(BuildConfig.SESSION_LOGIN, true)
			val action = LoginVerifyFragmentDirections.verifyToProfile()
			findNavController().navigate(action)
		}

		//set button progress lifecycle
		bindProgressButton(binding.btnNext)

		binding.etVerifyCode.post {
			showInputMethod(binding.etVerifyCode)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		if (isLoading) {
			binding.btnNext.showProgress {
				progressColorRes = R.color.white
				gravity = DrawableButton.GRAVITY_CENTER
			}
		} else {
			binding.btnNext.hideProgress(R.string.login_to_shop)
		}
	}

	private fun initVerify(result: NetworkResult<User>) {
		if (result is NetworkResult.Success) {
			//change requirements when user login
			baseFragmentCallback?.login()

			BaseObject.user = result.data
			BaseObject.user.notifyChange()
			saveToSp(USER, result.data)
			saveToSp(SESSION_TOKEN, result.data.accessToken)

			val action = LoginVerifyFragmentDirections.verifyToProfile()
			findNavController().navigate(action)
		} else if (result is NetworkResult.Error) {
			Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
		}
	}

	private fun initResend(result: NetworkResult<LoginMobile>) {
		if (result is NetworkResult.Success) {
			Toast.makeText(requireContext(), getString(R.string.resend_verify_code, result.data.mobileNumber), Toast.LENGTH_SHORT).show()
			isTimerFinished = false
			mTimer.start()
		} else if (result is NetworkResult.Error) {
			isTimerFinished = true
			Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
		}
	}
}