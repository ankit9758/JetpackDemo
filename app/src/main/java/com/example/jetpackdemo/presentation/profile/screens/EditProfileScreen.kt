package com.example.jetpackdemo.presentation.profile.screens

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.jetpackdemo.R
import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.presentation.profile.ProfileUiState
import com.example.jetpackdemo.presentation.profile.viewmodels.ProfileViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.BackToolbar
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.ImagePickerBottomSheet
import com.example.jetpackdemo.utils.LoadingOverlay
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.UserPreferences
import com.example.jetpackdemo.utils.Utility
import java.io.File
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val ctx = LocalContext.current
    val profileFlow = remember { UserPreferences(ctx).getProfile() }
    // collect → Compose State
    val profile by profileFlow.collectAsState(initial = null)

    var imageUriPath by rememberSaveable { mutableStateOf<String?>(null) } // To persist across config changes if desired for URI
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isEditable by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var showSheet by remember { mutableStateOf(false) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Coil painter so we can inspect the state
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUriPath)                 // <-- File on disk
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .crossfade(true)        // 250 ms by default
            .build()
    )

    val state = painter.state

    /* ---------- Cropper launcher ---------- */
    val cropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // The URI from the cropper
            val croppedUri = result.uriContent
            imageUriPath = croppedUri.toString()
            Log.d("EditProfileScreen", "Cropped Image Path: $imageUriPath")
        }
    }

    fun launchCrop(input: Uri) {
        val opts = CropImageOptions().apply {
            cropShape = CropImageView.CropShape.OVAL
            aspectRatioX = 1; aspectRatioY = 1
            fixAspectRatio = true
            outputCompressQuality = 70
        }
        cropLauncher.launch(CropImageContractOptions(uri = input, cropImageOptions = opts))
    }

    /* ---- Gallery ---- */
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { launchCrop(it) } }

    /* ---- Camera ---- */

    // Camera Launcher (Basic example, needs permission handling)

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            tempCameraUri?.let { launchCrop(it) }
        }
    }
    /* ---- Permission launcher ---- */
    val camPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val img = File(ctx.cacheDir, "cam_${UUID.randomUUID()}.jpg")
            tempCameraUri = FileProvider.getUriForFile(
                ctx,
                "${ctx.packageName}.provider",
                img
            )
            cameraLauncher.launch(tempCameraUri!!)
        } else {
            // TODO show rationale/snackbar
            Log.d("EditProfileScreen", "CAMERA permission denied")
        }
    }


    LaunchedEffect(Unit) {
        profile?.let {
            imageUriPath =it.imageUrl
            name = it.username
            email = it.email
            mobileNumber = it.phoneNumber
            password = it.password
        }
        profileViewModel.uiStateProfile.collect { state ->
            when (state) {

                is ProfileUiState.Error -> {
                    isLoading = false
                    Utility.showToast(ctx, (state).message)


                }

                is ProfileUiState.ErrorWithId -> {
                    isLoading = false
                    Utility.showToast(ctx, ctx.getString(state.id))
                }

               is  ProfileUiState.Loading -> {
                    isLoading = true
                }
                is ProfileUiState.Result<*> -> {
                    isLoading = false
                    val user = state.data as? User
                    user?.let {
                        profileViewModel.saveUserData(it)
                    }
                    Utility.showToast(ctx, "Your Profile Updated Successfully.")
                    onBackButtonClick()
                }

            }
        }
    }


    JetpackDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2196F3),
                            // Blue
                            Color(0xFF21CBF3)
                        ),
                    )
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BackToolbar(
                    title = stringResource(id = R.string.edit_profile),
                    onBackClick = { onBackButtonClick() }
                )

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Card(
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.size(120.dp),
                    )
                    {
                        /*    AsyncImage(
                                model = ImageRequest.Builder(ctx)
                                    .data(imageUriPath ?: R.drawable.avatar)
                                    .placeholder(R.drawable.avatar)
                                    .error(R.drawable.ic_camera) // Represents an error or fallback state
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )*/
                        // ------------ Image ------------
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    // ------------ Optional loading animation ------------
                    if (state is AsyncImagePainter.State.Loading) {
                        // Simple infinite progress; swap for a shimmer if you like
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            showSheet = true
                            isEditable =true
                        },
                        modifier = Modifier
                            .offset(x = (-2).dp, y = (-2).dp)
                            .size(24.dp)
                            .background(color = Color(0xFF4285F4), shape = CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .imePadding() // important!
                            .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 40.dp)
                    ) {

                        OutLineEditText(
                            value = name,
                            onValueChange = { it -> name = it.filter { it.isLetter() } },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.name),
                            placeHolder = stringResource(R.string.enter_name),
                            isEditable = isEditable,
                            keyboardType = KeyboardType.Ascii,
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Name Icon",
                                    tint = Color.Gray
                                )
                            }

                        )

                        OutLineEditText(
                            value = email,
                            onValueChange = {
                                email = it.filter { char ->
                                    char.isLetterOrDigit() || char in listOf('@', '.', '_', '-')
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.email),
                            placeHolder = stringResource(R.string.enter_email),
                            isEditable = false,
                            keyboardType = KeyboardType.Email,
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email Icon",
                                    tint = Color.Gray
                                )
                            }

                        )
                        OutLineEditText(
                            value = mobileNumber,
                            onValueChange = { it -> mobileNumber = it.filter { it.isDigit() } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.mobile_no),
                            placeHolder = stringResource(R.string.enter_phone),
                            keyboardType = KeyboardType.Number,
                            maxLength = 10,
                            isEditable = isEditable,
                            imeAction = ImeAction.Done,
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "Mobile Icon",
                                    tint = Color.Gray
                                )
                            }

                        )

                        OutLineEditText(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            label = stringResource(R.string.password),
                            placeHolder = stringResource(R.string.enter_password),
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password,
                            isEditable = false,
                            isPassword = !passwordVisible,
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "Info Icon"
                                    )

                                }
                            },
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Setting Icon",
                                    tint = Color.Gray
                                )
                            }
                        )

                        CustomButton(
                            text = stringResource(R.string.update_profile),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                profileViewModel.updateUserProfile(userName = name, phoneNumber = mobileNumber, imageUrl = imageUriPath.toString(),email=email)
                            })
                    }
                }
            }

            LoadingOverlay(isLoading)
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                ) {
                    ImagePickerBottomSheet(
                        onDismiss = { showSheet = false },
                        onCameraClick = {
                            camPermissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                        onGalleryClick = {
                            galleryLauncher.launch("image/*")
                        }
                    )
                }
            }
        }
    }

}