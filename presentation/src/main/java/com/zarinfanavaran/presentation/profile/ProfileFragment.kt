package com.zarinfanavaran.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinfanavaran.domain.extensions.priceFormat
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.models.OrderType
import com.zarinfanavaran.domain.models.ProfileMenuItem
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.ShareViewModel
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.BaseObject
import com.zarinfanavaran.presentation.databinding.FragmentProfileBinding
import java.io.File

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

	private val shareViewModel: ShareViewModel by activityViewModels()

	private val orderTypesAdapter = ProfileOrderTypesAdapter()
	private val profileMenuAdapter = ProfileMenuAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		baseFragmentCallback?.bottomNavigationVisibility(true)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.user = BaseObject.user

		binding.txtWalletAmount.text = spannableString(
			mContext = requireContext(),

			firstString = 150000.priceFormat(" "),
			firstSize = com.intuit.ssp.R.dimen._9ssp,
			firstFont = getString(R.string.font_medium),

			secondString = getString(R.string.toman),
			secondSize = com.intuit.ssp.R.dimen._8ssp,
		)

		binding.txtPointAmount.text = spannableString(
			mContext = requireContext(),

			firstString = 40.priceFormat(" "),
			firstSize = com.intuit.ssp.R.dimen._9ssp,
			firstFont = getString(R.string.font_medium),

			secondString = getString(R.string.point),
			secondSize = com.intuit.ssp.R.dimen._8ssp,
		)

		//profile image
		val profileImage = File(requireContext().filesDir, "User/user.JPEG")
		if (profileImage.exists()) {
			binding.imgProfile.setImageURI(getFileUri(profileImage))
		}

		//order types
		binding.rvOrderTypes.setHasFixedSize(true)
		binding.rvOrderTypes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		orderTypesAdapter.recyclerViewTools = orderTypeClicks
		setOrderTypeAdapter()

		//menu
		binding.rvProfileMenu.setHasFixedSize(false)
		binding.rvProfileMenu.layoutManager = LinearLayoutManager(requireContext())
		profileMenuAdapter.recyclerViewTools = menuClicks
		setProfileMenuAdapter()
	}

	private val orderTypeClicks = object : RecyclerViewTools {
		override fun <T> onItemClick(position: Int, view: View, item: T) {
		}
	}

	private fun setOrderTypeAdapter() {
		if (orderTypesAdapter.mItems.isEmpty()) {
			val sending = OrderType(
				title = "در مسیر شما",
				image = R.drawable.temp_umbrella,
				_badgeCount = 2
			)

			val delivered = OrderType(
				title = "تحویلش گرفتی",
				image = R.drawable.temp_basket,
				_badgeCount = 1
			)

			val returnType = OrderType(
				title = "مرجوع کردی",
				image = R.drawable.temp_return,
				_badgeCount = 3
			)

			val wishList = OrderType(
				title = "منتظره بخریش",
				image = R.drawable.temp_battery,
				_badgeCount = 0
			)

			orderTypesAdapter.mItems.add(sending)
			orderTypesAdapter.mItems.add(delivered)
			orderTypesAdapter.mItems.add(returnType)
			orderTypesAdapter.mItems.add(wishList)
		}

		binding.rvOrderTypes.adapter = orderTypesAdapter
	}

	private val menuClicks = object : RecyclerViewTools {
		override fun <T> onItemClick(position: Int, view: View, item: T) {
			item as ProfileMenuItem

			when (item.type) {
				"info" -> findNavController().navigate(R.id.editInfoFragment)
			}
		}
	}

	private fun setProfileMenuAdapter() {
		if (profileMenuAdapter.mItems.isEmpty()) {

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "اطلاعات کاربری",
					icon = R.drawable.ic_contact_stroke,
					color = R.color.color18,
					type = "info",
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "آدرس ها",
					icon = R.drawable.ic_address,
					color = R.color.color18,
					type = "address",
					_badgeCount = 0
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "دیدگاه ها",
					icon = R.drawable.ic_comment,
					color = R.color.color18,
					type = "comment"
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "علاقه مندی ها",
					icon = R.drawable.ic_bookmark_stroke,
					color = R.color.color18,
					type = "bookmark",
					_badgeCount = 0
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "بازدیدهای اخیر",
					icon = R.drawable.ic_history_stroke,
					color = R.color.color18,
					type = "recent",
					_badgeCount = 0
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "پشتیبانی",
					icon = R.drawable.ic_support,
					color = R.color.color18,
					type = "support"
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "آدرس ها",
					icon = R.drawable.ic_transaction_history,
					color = R.color.color18,
					type = "transaction",
					_badgeCount = 0
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "خروج از حساب کاربری",
					icon = R.drawable.ic_logout_storke,
					color = R.color.colorD6,
					type = "logout",
					_badgeCount = 0
				)
			)
		}

		binding.rvProfileMenu.adapter = profileMenuAdapter
	}
}