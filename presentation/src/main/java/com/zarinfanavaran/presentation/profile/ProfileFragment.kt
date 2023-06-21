package com.zarinfanavaran.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.RequestManager
import com.google.gson.JsonElement
import com.zarinfanavaran.domain.extensions.loadFromSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.models.OrderType
import com.zarinfanavaran.domain.models.ProfileMenuItem
import com.zarinfanavaran.domain.models.User
import com.zarinfanavaran.domain.util.HttpErrors
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.ShareViewModel
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.BaseObject
import com.zarinfanavaran.presentation.base.RetryCallback
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.FragmentProfileBinding
import com.zarinfanavaran.presentation.util.SESSION_LOGOUT_KEY
import com.zarinfanavaran.presentation.util.observe
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
	private val viewModel: ProfileViewModel by viewModels()
	private val shareViewModel: ShareViewModel by activityViewModels()

	private val orderTypesAdapter = ProfileOrderTypesAdapter()
	private val profileMenuAdapter = ProfileMenuAdapter()

	@Inject
	lateinit var glide: RequestManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		baseFragmentCallback?.bottomNavigationVisibility(true)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onResume() {
		super.onResume()

		if (loadFromSp(SESSION_LOGOUT_KEY, false)) {
			performLogout()
		}
	}

	override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
		when (item) {
			is ProfileMenuItem -> {
				when (item.type) {
					"info" -> {
						val action = ProfileFragmentDirections.profileToEditInfo()
						findNavController().navigate(action)
					}

					"address" -> {
						val action = ProfileFragmentDirections.profileToAddresses()
						findNavController().navigate(action)
					}

					"comment" -> {}

					"bookmark" -> {
						val action = ProfileFragmentDirections.actionProfileFragmentToWishListsFragment()
						findNavController().navigate(action)
					}

					"recent" -> {}

					"support" -> {}

					"transaction" -> {}

					"logout" -> {
						MaterialDialog(requireContext()).show {
							title(R.string.logout_title)
							message(R.string.logout_message)
							positiveButton(R.string._yes) {
								dismiss()
								viewModel.logout(JSONObject().toString().toRequestBody())
							}
							negativeButton(R.string.cancel)
						}
					}
				}
			}
		}
	}

	override fun onRetry() {
		viewModel.profile()
	}

	override fun onCancel() {
		baseFragmentCallback?.myOnBackPressed()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getProfile(), ::initProfile)
			observe(getLogout(), ::initLogout)
		}
	}

	private fun setupUI() {
		binding.user = BaseObject.user

		//order types
		binding.rvOrderTypes.setHasFixedSize(true)
		binding.rvOrderTypes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		orderTypesAdapter.recyclerViewTools = this
		binding.rvOrderTypes.adapter = orderTypesAdapter

		//menu
		binding.rvProfileMenu.setHasFixedSize(false)
		binding.rvProfileMenu.layoutManager = LinearLayoutManager(requireContext())
		profileMenuAdapter.recyclerViewTools = this
		binding.rvProfileMenu.adapter = profileMenuAdapter

		setProfileMenuAdapter()
		setOrderTypeAdapter()
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.flMain, isLoading)
	}

	private fun initProfile(result: NetworkResult<User>) {
		if (result is NetworkResult.Success) {
			BaseObject.user = result.data
			BaseObject.user.notifyChange()

			setProfielInfo()
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				performLogout()
			} else {
				RetryDialog(requireContext(), this, false).show()
			}
		}
	}

	private fun initLogout(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			performLogout()
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				performLogout()
			} else {
				RetryDialog(requireContext(), object : RetryCallback {
					override fun onRetry() {
						viewModel.logout(JSONObject().toString().toRequestBody())
					}
				}, true).show()
			}
		}
	}

	private fun setProfielInfo() {
		binding.txtWalletAmount.text = spannableString(
			mContext = requireContext(),

			firstString = BaseObject.user.walletAmountPrettified,
			firstSize = com.intuit.ssp.R.dimen._9ssp,
			firstFont = getString(R.string.font_medium),

			secondString = getString(R.string.toman),
			secondSize = com.intuit.ssp.R.dimen._8ssp,
		)

		binding.txtPointAmount.text = spannableString(
			mContext = requireContext(),

			firstString = BaseObject.user.pointAmountPrettified,
			firstSize = com.intuit.ssp.R.dimen._9ssp,
			firstFont = getString(R.string.font_medium),

			secondString = getString(R.string.point),
			secondSize = com.intuit.ssp.R.dimen._8ssp,
		)

		//profile image
		BaseObject.user.media?.avatar?.also {
			glide.load(it.file).into(binding.imgProfile)
		}

		setOrderTypeAdapter()
		setProfileMenuAdapter()
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun setOrderTypeAdapter() {
		if (orderTypesAdapter.mItems.isEmpty()) {
			val sending = OrderType(
				title = "در مسیر شما",
				image = R.drawable.temp_umbrella,
				_badgeCount = BaseObject.user.sentProgressOrdersCount
			)

			val delivered = OrderType(
				title = "تحویلش گرفتی",
				image = R.drawable.temp_basket,
				_badgeCount = BaseObject.user.expiredOrdersCount
			)

			val returnType = OrderType(
				title = "مرجوع کردی",
				image = R.drawable.temp_return,
				_badgeCount = BaseObject.user.returnedOrdersCount
			)

			val wishList = OrderType(
				title = "منتظره بخریش",
				image = R.drawable.temp_battery,
				_badgeCount = BaseObject.user.inProgressOrdersCount
			)

			orderTypesAdapter.mItems.add(sending)
			orderTypesAdapter.mItems.add(delivered)
			orderTypesAdapter.mItems.add(returnType)
			orderTypesAdapter.mItems.add(wishList)

			orderTypesAdapter.notifyDataSetChanged()
		}
	}

	@SuppressLint("NotifyDataSetChanged")
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
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "دیدگاه ها",
					icon = R.drawable.ic_comment,
					color = R.color.color18,
					type = "comment",
					_badgeCount = BaseObject.user.productsWaitingToCommentCount
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "علاقه مندی ها",
					icon = R.drawable.ic_bookmark_stroke,
					color = R.color.color18,
					type = "bookmark",
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "بازدیدهای اخیر",
					icon = R.drawable.ic_history_stroke,
					color = R.color.color18,
					type = "recent",
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "پشتیبانی",
					icon = R.drawable.ic_support,
					color = R.color.color18,
					type = "support",
					_badgeCount = BaseObject.user.questionsWaitingToAnswerCount
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "آخرین تراکنش ها",
					icon = R.drawable.ic_transaction_history,
					color = R.color.color18,
					type = "transaction",
				)
			)

			profileMenuAdapter.mItems.add(
				ProfileMenuItem(
					title = "خروج از حساب کاربری",
					icon = R.drawable.ic_logout_storke,
					color = R.color.colorD6,
					type = "logout",
				)
			)

			profileMenuAdapter.notifyDataSetChanged()
		}
	}

	private fun performLogout() {
//		val action = ProfileFragmentDirections.actionProfileFragmentToLoginMobileFragment()
//		findNavController().navigate(action)
//		baseFragmentCallback?.logout()
	}
}