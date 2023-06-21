package com.zarinfanavaran.presentation.profile.info

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.RequestManager
import com.google.android.material.button.MaterialButton
import com.google.gson.JsonElement
import com.yalantis.ucrop.UCrop
import com.zarinfanavaran.domain.BuildConfig.USER
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.extensions.toEnglish
import com.zarinfanavaran.domain.models.CreditCard
import com.zarinfanavaran.domain.models.Media
import com.zarinfanavaran.domain.util.HttpErrors
import com.zarinfanavaran.domain.util.NetworkResult
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.ShareViewModel
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.BaseObject
import com.zarinfanavaran.presentation.base.RetryDialog
import com.zarinfanavaran.presentation.databinding.*
import com.zarinfanavaran.presentation.util.observe
import com.zarinfanavaran.presentation.util.persiandatepicker.date.DatePickerDialog
import com.zarinfanavaran.presentation.util.persiandatepicker.utils.PersianCalendar
import com.zarinfanavaran.presentation.util.toRequestBody
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.*
import java.util.*
import javax.inject.Inject


/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */

@AndroidEntryPoint
class EditInfoFragment : BaseFragment<FragmentEditInfoBinding>(R.layout.fragment_edit_info) {

	private val viewModel: EditInfoViewModel by viewModels()
	private val shareViewModel: ShareViewModel by activityViewModels()

	@Inject
	lateinit var glide: RequestManager

	private val bankAccountAdapter = ProfileBankAccountAdapter()

	private var bankAccountBinding: TemplateDialogBankAccountBinding? = null

	private lateinit var imageUri: Uri
	private lateinit var userDir: File
	private lateinit var currentProfileImageFile: File

	private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
		if (success) {
			cropImage(imageUri)
		}
	}

	private val takeFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
		uri?.also { pickedImageUri ->
			val tempProfileImageFile = File(userDir, "profile.JPEG")
			if (tempProfileImageFile.exists())
				tempProfileImageFile.delete()
			imageUri = getFileUri(tempProfileImageFile)
			cropImage(uri)
		} ?: kotlin.run {
			Toast.makeText(requireContext(), "Something went wrong with pick image", Toast.LENGTH_SHORT).show()
		}
	}

	private val bankTools = object : RecyclerViewTools {
		override fun <T> onItemClick(position: Int, view: View, item: T, parentPosition: Int) {
			bankAccountDialog(position)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		userDir = File(requireContext().filesDir, "User")
		if (!userDir.exists())
			userDir.mkdirs()

		val tempProfileImageFile = File(userDir, "profile.JPEG")
		if (tempProfileImageFile.exists())
			tempProfileImageFile.delete()
		imageUri = getFileUri(tempProfileImageFile)

		currentProfileImageFile = File(userDir, "user.JPEG")

		setupObservers()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		baseFragmentCallback?.bottomNavigationVisibility(false)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun onPause() {
		super.onPause()
		mDialog?.dismiss()
		mDialog = null
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			bankAccountBinding?.also {
				it.etFirstPart.clearFocus()
				it.etSecondPart.clearFocus()
				it.etThirdPart.clearFocus()
				it.etFourthPart.clearFocus()
				it.etFifthPart.clearFocus()
				it.etSixthPart.clearFocus()
				it.etSeventhPart.clearFocus()
			}
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(getUploadFile(), ::uploadFile)
			observe(getAvatar(), ::saveAvatar)
			observe(getInfo(), ::saveInfo)
			observe(getBornAt(), ::saveBornAt)
			observe(getEmail(), ::saveEmail)
			observe(getCreditCards(), ::initCreditCards)
			observe(creditCardAdd(), ::addCreditCard)
			observe(creditCardDelete(), ::deleteCreditCard)
			observe(creditCardBankInfo(), ::bankInfoCreditCard)
		}
	}

	private fun setupUI() {
		//init toolbar
		binding.imgToolbarIcon.setOnClickListener { back() }

		//bind user to view
		binding.user = BaseObject.user


		//mobile text
		binding.txtMobile.text = spannableString(
			mContext = requireContext(),

			firstString = getString(R.string.mobile) + ": ",

			secondString = BaseObject.user.mobileNumber,
			secondFont = getString(R.string.font_semi_bold),
		)

		//profile image
		BaseObject.user.media?.avatar?.also {
			glide.load(it.file).into(binding.imgProfile)
		}

		//edit image
		binding.btnEditImage.setOnClickListener { changeImageDialog() }

		//edit info
		binding.btnEditInfo.setOnClickListener { editInfoDialog() }

		//change birthdate
		binding.btnEditBirthDay.setOnClickListener { changeBirthDate() }

		//change email
		binding.btnEditEmail.setOnClickListener { changeEmailDialog() }

		//add bank account
		binding.btnAddBankAccount.setOnClickListener { bankAccountDialog() }

		//bank accounts
		binding.rvCreditCards.setHasFixedSize(false)
		binding.rvCreditCards.layoutManager = LinearLayoutManager(requireContext())
		bankAccountAdapter.recyclerViewTools = bankTools


		//TODO: request to get bank accounts
		setCreditCardsAdapter()
	}

	private fun initLoading(isLoading: Boolean) {
		setProgressView(binding.flMain, isLoading)
	}

	private fun uploadFile(result: NetworkResult<Media>) {
		if (result is NetworkResult.Success) {
			result.data.avatar?.also { avatar ->
				BaseObject.user.media?.avatar = avatar

				//save avatar after upload file
				val jsonObject = JSONObject()
				jsonObject.put("avatar_id", avatar.id)
				val avatarBody = jsonObject.toRequestBody()
				viewModel.saveAvatar(avatarBody)
			}
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, false).show()
			}
		}
	}

	private fun saveAvatar(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			saveToSp(USER, BaseObject.user)
			BaseObject.user.media?.avatar?.also {
				glide.load(it.file).into(binding.imgProfile)
			}
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, false).show()
			}
		}
	}

	private fun saveInfo(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			//TODO: save info to user after success server response
			saveToSp(USER, BaseObject.user)
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, false).show()
			}
		}
	}

	private fun saveBornAt(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			//TODO: save bornAt to user after success server response
			saveToSp(USER, BaseObject.user)
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, false).show()
			}
		}
	}

	private fun saveEmail(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			//TODO: save email to user after success server response
			saveToSp(USER, BaseObject.user)
			showSimpleDialog(getString(R.string.email_activation_title), getString(R.string.email_activation_description))
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, true).show()
			}
		}
	}

	private fun initCreditCards(result: NetworkResult<List<CreditCard>>) {
		if (result is NetworkResult.Success) {
			//TODO: set items to credit card adapter
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, false).show()
			}
		}
	}

	private fun addCreditCard(result: NetworkResult<CreditCard>) {
		if (result is NetworkResult.Success) {
			//TODO: add item to adapter
			bankAccountAdapter.mItems.add(
				0, CreditCard(
					id = 0
				)
			)
			bankAccountAdapter.notifyItemInserted(0)
		} else if (result is NetworkResult.Error) {
			Log.d(TAG, "addCreditCard: ${result.error.message}")
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, true).show()
			}
		}
	}

	private fun deleteCreditCard(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			//TODO: delete credit card from list with position
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			} else {
				RetryDialog(requireContext(), this, true).show()
			}
		}
	}

	private fun bankInfoCreditCard(result: NetworkResult<JsonElement>) {
		if (result is NetworkResult.Success) {
			//TODO: set image for bank
		} else if (result is NetworkResult.Error) {
			if (result.error.status == HttpErrors.Unauthorized) {
				back()
			}
		}
	}

	private fun bankAccountDialog(position: Int = -1) {
		mDialog?.dismiss()

		bankAccountBinding = TemplateDialogBankAccountBinding.inflate(layoutInflater)
		if (position >= 0) {
			bankAccountBinding?.item = bankAccountAdapter.mItems[position]
			bankAccountBinding?.imgBankLogo?.alpha = 1f
		}
		mDialog = MaterialDialog(requireContext()).show {
			customView(view = bankAccountBinding!!.root)

			onShow {
				bankAccountBinding!!.root.setPadding(0, 0, 0, 0)

				bankAccountBinding!!.txtAlert.text = spannableString(
					mContext = requireContext(),

					firstString = getString(R.string.very_important),
					firstColor = R.color.colorFF,

					secondString = getString(R.string.shaba_number_alert),
					secondFont = getString(R.string.font_medium)
				)

				bankAccountBinding!!.etFirstPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 2) {
							bankAccountBinding!!.etSecondPart.requestFocus()
						}
						if (getShabaNumber().length == 0) {
							bankAccountBinding!!.imgBankLogo.alpha = 0.1f
						} else {
							bankAccountBinding!!.imgBankLogo.alpha = 1f
						}
					}
				}

				bankAccountBinding!!.etSecondPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 4)
							bankAccountBinding!!.etThirdPart.requestFocus()
						else if (it == 0)
							bankAccountBinding!!.etFirstPart.requestFocus()
					}
				}

				bankAccountBinding!!.etThirdPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 4)
							bankAccountBinding!!.etFourthPart.requestFocus()
						else if (it == 0)
							bankAccountBinding!!.etSecondPart.requestFocus()
					}
				}

				bankAccountBinding!!.etFourthPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 4)
							bankAccountBinding!!.etFifthPart.requestFocus()
						else if (it == 0)
							bankAccountBinding!!.etThirdPart.requestFocus()
					}
				}

				bankAccountBinding!!.etFifthPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 4)
							bankAccountBinding!!.etSixthPart.requestFocus()
						else if (it == 0)
							bankAccountBinding!!.etFourthPart.requestFocus()
					}
				}

				bankAccountBinding!!.etSixthPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 4)
							bankAccountBinding!!.etSeventhPart.requestFocus()
						else if (it == 0)
							bankAccountBinding!!.etFifthPart.requestFocus()
					}
				}

				bankAccountBinding!!.etSeventhPart.doAfterTextChanged {
					it?.toString()?.length?.also {
						if (it == 0) {
							bankAccountBinding!!.etSixthPart.requestFocus()
						}
					}
				}

				bankAccountBinding!!.btnAction.setOnClickListener(View.OnClickListener {
					val shabaNumber = getShabaNumber().toEnglish()

					if (shabaNumber.length != 30) {
						Toast.makeText(requireContext(), "شماره شبا وارد شده معتبر نمی باشد", Toast.LENGTH_SHORT).show()
						return@OnClickListener
					}


					dismiss()

					if (position >= 0) {
						//TODO: update credit card info
//						bankAccountAdapter.mItems[position].shabaNumber = shabaNumber
//						bankAccountAdapter.notifyItemChanged(position)
					} else {
						//save credit card
						val jsonObject = JSONObject()
						jsonObject.put("iban_number", shabaNumber)
						viewModel.saveCreditCard(jsonObject.toRequestBody())
					}
				})

				bankAccountBinding!!.btnClose.setOnClickListener { dismiss() }
			}
		}
	}

	private fun getShabaNumber(): String {
		return bankAccountBinding?.let {
			(it.etFirstPart.text.toString().trim() + "-"
					+ it.etSecondPart.text.toString().trim() + "-"
					+ it.etThirdPart.text.toString().trim() + "-"
					+ it.etFourthPart.text.toString().trim() + "-"
					+ it.etFifthPart.text.toString().trim() + "-"
					+ it.etSixthPart.text.toString().trim() + "-"
					+ it.etSeventhPart.text.toString().trim())
		} ?: kotlin.run { "" }
	}

	private fun changeBirthDate() {
		val persianCalendar = PersianCalendar()
		try {
			if (BaseObject.user.jalaliBornAt.isNotEmpty()) {
				val birthDate = BaseObject.user.jalaliBornAt.split("/")
				persianCalendar.setPersianDate(birthDate[0].toInt(), birthDate[1].toInt() - 1, birthDate[2].toInt())
			}
		} catch (_: Exception) {
			persianCalendar.setPersianDate(1370, 6, 7)
		}
		val datePickerDialog = DatePickerDialog.newInstance({ mView, mYear, mMonth, mDay ->
			val jalaliBornAt = "$mYear/${mMonth + 1}/$mDay"

			//send data to server
			val jsonObject = JSONObject()
			jsonObject.put("born_at", jalaliBornAt)
			viewModel.saveBornAt(jsonObject.toRequestBody())

		}, persianCalendar.persianYear, persianCalendar.persianMonth, persianCalendar.persianDay)
		datePickerDialog.typeface = getString(R.string.font_regular)

		datePickerDialog.show(childFragmentManager, "BirthdatePicker")
	}

	private fun changeImageDialog() {
		mDialog?.dismiss()
		mDialog = MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
			customView(R.layout.template_select_picture)

			onShow {
				this.getCustomView().setPadding(0, 0, 0, 0)

				getCustomView().findViewById<MaterialButton>(R.id.btnGallery).setOnClickListener {
					dismiss()
					takeFromGallery.launch("image/*")
				}
				getCustomView().findViewById<MaterialButton>(R.id.btnCamera).setOnClickListener {
					dismiss()
					takePicture.launch(imageUri)
				}
			}
		}
	}

	private fun changeEmailDialog() {
		mDialog?.dismiss()
		val dialogEmailBinding = TemplateDialogEmailBinding.inflate(layoutInflater)
		dialogEmailBinding.user = BaseObject.user
		mDialog = MaterialDialog(requireContext()).show {
			customView(view = dialogEmailBinding.root)

			onShow {
				dialogEmailBinding.root.setPadding(0, 0, 0, 0)

				dialogEmailBinding.btnAction.setOnClickListener(View.OnClickListener {
					val email = dialogEmailBinding.etEmail.text.toString()
					if (email.trim().isEmpty()) {
						Toast.makeText(requireContext(), getString(R.string.enter_email), Toast.LENGTH_SHORT).show()
						return@OnClickListener
					}
					if (BaseObject.user.email != email.trim()) {
						BaseObject.user.emailVerifiedAt = ""
					}
					//send data to server
					val jsonObject = JSONObject()
					jsonObject.put("email", email)
					viewModel.saveEmail(jsonObject.toRequestBody())

					dismiss()
				})

				dialogEmailBinding.btnClose.setOnClickListener { dismiss() }
			}
		}
	}

	private fun editInfoDialog() {
		mDialog?.dismiss()

		val userInfoBinding = TemplateDialogUserInfoBinding.inflate(LayoutInflater.from(requireContext()))
		userInfoBinding.user = BaseObject.user

		mDialog = MaterialDialog(requireContext()).show {
			customView(view = userInfoBinding.root)
			onShow {
				userInfoBinding.root.setPadding(0, 0, 0, 0)

				userInfoBinding.btnClose.setOnClickListener { dismiss() }
				userInfoBinding.btnAction.setOnClickListener {
					val firstName = userInfoBinding.etFirstName.text.toString()
					val lastName = userInfoBinding.etLastName.text.toString()
					val nationalCode = userInfoBinding.etPersonalCode.text.toString()

					if (firstName.trim().isEmpty()) {
						Toast.makeText(requireContext(), getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show()
						return@setOnClickListener
					}

					if (lastName.trim().isEmpty()) {
						Toast.makeText(requireContext(), getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show()
						return@setOnClickListener
					}

					if (nationalCode.trim().isEmpty()) {
						Toast.makeText(requireContext(), getString(R.string.enter_national_code), Toast.LENGTH_SHORT).show()
						return@setOnClickListener
					}

					//send data to server
					val jsonObject = JSONObject()
					jsonObject.put("first_name", firstName.trim())
					jsonObject.put("last_name", lastName.trim())
					jsonObject.put("national_code", nationalCode.trim().toLong())
					viewModel.saveInfo(jsonObject.toRequestBody())

					//close dialog
					dismiss()
				}
			}
		}
	}

	private fun showSimpleDialog(title: String, description: String) {
		mDialog?.dismiss()

		val dialogAlertBinding = TemplateDialogAlertBinding.inflate(layoutInflater)
		mDialog = MaterialDialog(requireContext()).show {
			customView(view = dialogAlertBinding.root)
			onShow {
				dialogAlertBinding.root.setPadding(0, 0, 0, 0)

				dialogAlertBinding.txtTitle.text = title
				dialogAlertBinding.txtDescription.text = description

				dialogAlertBinding.btnClose.setOnClickListener { dismiss() }
			}
		}
	}

	private fun setCreditCardsAdapter() {
		if (bankAccountAdapter.mItems.isEmpty()) {
			bankAccountAdapter.mItems.add(
				CreditCard(
					id = 0
				)
			)
			bankAccountAdapter.mItems.add(
				CreditCard(
					id = 0,
					isConfirmed = true
				)
			)
		}
		binding.rvCreditCards.adapter = bankAccountAdapter
	}

	private fun cropImage(fromUri: Uri) {
		val options = UCrop.Options()
		options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
		options.setCompressionQuality(90)
		UCrop.of(fromUri, imageUri)
				.withOptions(options)
				.withMaxResultSize(400, 400)
				.start(requireContext(), this)
	}

	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
			data?.also {
				UCrop.getOutput(data)?.also {
					try {
						binding.imgProfile.setImageURI(null)

						currentProfileImageFile = File(userDir, "user.JPEG")
						if (currentProfileImageFile.exists())
							currentProfileImageFile.delete()

						val tempProfile = File(userDir, "profile.JPEG")
						tempProfile.renameTo(currentProfileImageFile)
						val requestBody = currentProfileImageFile.asRequestBody("image/*".toMediaTypeOrNull())
						val multiPartBody = MultipartBody.Part.createFormData("file", currentProfileImageFile.name, requestBody)
						viewModel.uploadFile(multiPartBody)
					} catch (e: Exception) {
						e.printStackTrace()
						Log.e(TAG, "onActivityResult: ${e.message}")
					}
				}
			}
		} else if (resultCode == UCrop.RESULT_ERROR) {
			data?.also {
				val cropError = UCrop.getError(data)
				Log.e(TAG, "onActivityResult: ${cropError?.message}")
			}
		}
	}
}