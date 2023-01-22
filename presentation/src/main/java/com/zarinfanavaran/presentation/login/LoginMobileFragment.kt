package com.zarinfanavaran.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zarinfanavaran.domain.BuildConfig.SESSION_LOGIN
import com.zarinfanavaran.domain.extensions.isMobile
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentLoginMobileBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class LoginMobileFragment : BaseFragment<FragmentLoginMobileBinding>(R.layout.fragment_login_mobile) {

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

			val action = LoginMobileFragmentDirections.loginToVerify(mobile)
			findNavController().navigate(action)
		}

		if (!loadFromSp(SESSION_LOGIN, false)) {
			binding.etMobile.post {
				showInputMethod(binding.etMobile)
			}
		}
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.etMobile.clearFocus()
		}
	}
}