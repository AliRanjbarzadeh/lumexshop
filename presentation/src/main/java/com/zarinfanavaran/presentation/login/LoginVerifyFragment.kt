package com.zarinfanavaran.presentation.login

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zarinfanavaran.domain.BuildConfig.USER
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentLoginVerifyBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class LoginVerifyFragment : BaseFragment<FragmentLoginVerifyBinding>(R.layout.fragment_login_verify) {

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

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

			isTimerFinished = false
			mTimer.start()
		}

		//edit mobile
		binding.btnEditMobile.setOnClickListener { back() }

		//verify click
		binding.btnNext.setOnClickListener {
			val verifyCode = binding.etVerifyCode.text.toString()
			if (verifyCode.isEmpty()) {
				Toast.makeText(requireContext(), getString(R.string.please_enter_verify_code), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (verifyCode.length < 5) {
				Toast.makeText(requireContext(), getString(R.string.wrong_verify_code), Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val user = User(
				_mobile = args.mobile
			)
			saveToSp(USER, user)
			hideKeyboard()

			//change requirements when user login
			baseFragmentCallback?.login()

			val action = LoginVerifyFragmentDirections.verifyToProfile()
			findNavController().navigate(action)
		}

		binding.etVerifyCode.doAfterTextChanged {
			it?.also {
				if (it.toString().length == 5) {
					binding.btnNext.callOnClick()
				}
			}
		}

		binding.etVerifyCode.post {
			showInputMethod(binding.etVerifyCode)
		}
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
}