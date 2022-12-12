package com.zarinfanavaran.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.zarinfanavaran.domain.models.Intro
import com.zarinfanavaran.presentation.MainActivity
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.databinding.FragmentIntroBinding

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class IntroFragment : BaseFragment<FragmentIntroBinding>(R.layout.fragment_intro) {

	private var introAdapter: IntroAdapter? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setAdapter()

		binding.ivDots.apply {
			setSliderWidth(resources.getDimension(com.intuit.sdp.R.dimen._10sdp))
			setSliderHeight(resources.getDimension(com.intuit.sdp.R.dimen._10sdp))
			setSliderGap(resources.getDimension(com.intuit.sdp.R.dimen._5sdp))
			setupWithViewPager(binding.introSlider)
			notifyDataChanged()
		}

		binding.introSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
			override fun onPageSelected(position: Int) {
				super.onPageSelected(position)
				if (position == 2) {
					binding.ivDots.visibility = View.GONE
					binding.btnNext.text = getString(R.string.enter_shop)
				} else {
					binding.ivDots.visibility = View.VISIBLE
					binding.btnNext.text = getString(R.string.turn_page)
				}
			}
		})

		binding.viewSkip.setOnClickListener {
			goToMain()
		}

		binding.btnNext.setOnClickListener {
			if (binding.introSlider.currentItem < 2) {
				binding.introSlider.currentItem += 1
			} else {
				goToMain()
			}
		}
	}

	private fun setAdapter() {
		if (introAdapter == null) {
			introAdapter = IntroAdapter()

			introAdapter?.mItems?.add(
				Intro(
					title = "ثبت لحظه ها",
					description = "از امروز میتونی نیاز های الکترونیکی خودت رو با قیمت ای خفن رفع کنی",
					image = R.drawable.intro1,
					btnText = "لومکس شاپ"
				)
			)

			introAdapter?.mItems?.add(
				Intro(
					title = "ثبت لحظه ها",
					description = "از امروز میتونی نیاز های الکترونیکی خودت رو با قیمت ای خفن رفع کنی",
					image = R.drawable.intro2,
					btnText = "لومکس شاپ"
				)
			)

			introAdapter?.mItems?.add(
				Intro(
					title = "ثبت لحظه ها",
					description = "از امروز میتونی نیاز های الکترونیکی خودت رو با قیمت ای خفن رفع کنی",
					image = R.drawable.intro3,
					btnText = "لومکس شاپ"
				)
			)
		}

		binding.introSlider.adapter = introAdapter
	}

	private fun goToMain() {
		Intent(requireContext(), MainActivity::class.java).apply {
			startActivity(this)
			requireActivity().finish()
		}
	}
}