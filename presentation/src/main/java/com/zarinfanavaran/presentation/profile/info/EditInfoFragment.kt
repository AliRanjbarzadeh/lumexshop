package com.zarinfanavaran.presentation.profile.info

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.button.MaterialButton
import com.yalantis.ucrop.UCrop
import com.zarinfanavaran.domain.BuildConfig.USER
import com.zarinfanavaran.domain.extensions.saveToSp
import com.zarinfanavaran.domain.extensions.spannableString
import com.zarinfanavaran.domain.extensions.toEnglish
import com.zarinfanavaran.domain.models.BankAccount
import com.zarinfanavaran.domain.util.RecyclerViewTools
import com.zarinfanavaran.presentation.R
import com.zarinfanavaran.presentation.ShareViewModel
import com.zarinfanavaran.presentation.base.BaseFragment
import com.zarinfanavaran.presentation.base.BaseObject
import com.zarinfanavaran.presentation.databinding.*
import com.zarinfanavaran.presentation.util.DispatchersProvider
import com.zarinfanavaran.presentation.util.persiandatepicker.date.DatePickerDialog
import com.zarinfanavaran.presentation.util.persiandatepicker.utils.PersianCalendar
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.util.*
import javax.inject.Inject


/**
 * Created by Ali Ranjbarzadeh on 9/30/2022 AD.
 */

@AndroidEntryPoint
class EditInfoFragment : BaseFragment<FragmentEditInfoBinding>(R.layout.fragment_edit_info) {

	private val shareViewModel: ShareViewModel by viewModels()

	@Inject
	lateinit var dispatchers: DispatchersProvider

	private val bankAccountAdapter = ProfileBankAccountAdapter()

	private var mDialog: MaterialDialog? = null
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
		override fun <T> onItemClick(position: Int, view: View, item: T) {
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
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		baseFragmentCallback?.bottomNavigationVisibility(false)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//init toolbar
		binding.imgToolbarIcon.setOnClickListener { back() }

		//bind user to view
		binding.user = BaseObject.user


		//mobile text
		binding.txtMobile.text = spannableString(
			mContext = requireContext(),

			firstString = getString(R.string.mobile) + ": ",

			secondString = BaseObject.user.mobile,
			secondFont = getString(R.string.font_semi_bold),
		)

		//set current profile image
		if (currentProfileImageFile.exists())
			binding.imgProfile.setImageURI(getFileUri(currentProfileImageFile))

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
		binding.rvBankAccounts.setHasFixedSize(false)
		binding.rvBankAccounts.layoutManager = LinearLayoutManager(requireContext())
		bankAccountAdapter.recyclerViewTools = bankTools
		setBankAdapter()
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

					if (shabaNumber.length != 24) {
						Toast.makeText(requireContext(), "شماره شبا وارد شده معتبر نمی باشد", Toast.LENGTH_SHORT).show()
						return@OnClickListener
					}

					dismiss()

					if (position >= 0) {
						bankAccountAdapter.mItems[position].shabaNumber = shabaNumber
						bankAccountAdapter.notifyItemChanged(position)
					} else {
						bankAccountAdapter.mItems.add(
							0, BankAccount(
								title = "شماره شبا",
								_shabaNumber = shabaNumber,
								_isConfirmed = false
							)
						)
						bankAccountAdapter.notifyItemInserted(0)
					}
				})

				bankAccountBinding!!.btnClose.setOnClickListener { dismiss() }
			}
		}
	}

	private fun getShabaNumber(): String {
		return bankAccountBinding?.let {
			(it.etFirstPart.text.toString().trim()
					+ it.etSecondPart.text.toString().trim()
					+ it.etThirdPart.text.toString().trim()
					+ it.etFourthPart.text.toString().trim()
					+ it.etFifthPart.text.toString().trim()
					+ it.etSixthPart.text.toString().trim()
					+ it.etSeventhPart.text.toString().trim())
		} ?: kotlin.run { "" }
	}

	private fun changeBirthDate() {
		val persianCalendar = PersianCalendar()
		try {
			if (BaseObject.user.birthDate.isNotEmpty()) {
				val birthDate = BaseObject.user.birthDate.split("/")
				persianCalendar.setPersianDate(birthDate[0].toInt(), birthDate[1].toInt() - 1, birthDate[2].toInt())
			}
		} catch (_: Exception) {
			persianCalendar.setPersianDate(1370, 6, 7)
		}
		val datePickerDialog = DatePickerDialog.newInstance({ mView, mYear, mMonth, mDay ->
			BaseObject.user.birthDate = "$mYear/${mMonth + 1}/$mDay"
			saveToSp(USER, BaseObject.user)
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
						return@OnClickListener
					}
					if (BaseObject.user.email != email.trim()) {
						BaseObject.user.emailConfirmed = false
					}
					BaseObject.user.email = email.trim()
					saveToSp(USER, BaseObject.user)

					dismiss()
					showSimpleDialog(getString(R.string.email_activation_title), getString(R.string.email_activation_description))
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
					BaseObject.user.firstName = userInfoBinding.etFirstName.text.toString()
					BaseObject.user.lastName = userInfoBinding.etLastName.text.toString()
					BaseObject.user.personalCode = userInfoBinding.etPersonalCode.text.toString()

					//save user info in session
					saveToSp(USER, BaseObject.user)

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

	private fun setBankAdapter() {
		if (bankAccountAdapter.mItems.isEmpty()) {
			bankAccountAdapter.mItems.add(
				BankAccount(
					title = getString(R.string.shaba_number),
					_isConfirmed = false,
					_shabaNumber = "250120020000009281913129"
				)
			)
			bankAccountAdapter.mItems.add(
				BankAccount(
					title = getString(R.string.shaba_number),
					_isConfirmed = true,
					_shabaNumber = "250120020000009281913129"
				)
			)
		}
		binding.rvBankAccounts.adapter = bankAccountAdapter
	}

	private fun cropImage(fromUri: Uri) {
		UCrop.of(fromUri, imageUri)
			.start(requireContext(), this)
	}

	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
			data?.also {
				UCrop.getOutput(data)?.also {
					binding.imgProfile.setImageURI(null)

					currentProfileImageFile = File(userDir, "user.JPEG")
					if (currentProfileImageFile.exists())
						currentProfileImageFile.delete()

					val tempProfile = File(userDir, "profile.JPEG")
					tempProfile.renameTo(currentProfileImageFile)

					binding.imgProfile.setImageURI(getFileUri(currentProfileImageFile))
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