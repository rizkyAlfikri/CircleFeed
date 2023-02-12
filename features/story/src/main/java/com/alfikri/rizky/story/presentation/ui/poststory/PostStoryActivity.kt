package com.alfikri.rizky.story.presentation.ui.poststory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.alfikri.rizky.core.presentation.UiState
import com.alfikri.rizky.share.ui.compose.LoadingCircle
import com.alfikri.rizky.share.ui.compose.PrimaryButton
import com.alfikri.rizky.share.ui.compose.PrimaryButtonSmall
import com.alfikri.rizky.share.ui.theme.CircleFeedTheme
import com.alfikri.rizky.share.ui.theme.Placeholder
import com.alfikri.rizky.share.ui.theme.PrimaryBlue
import com.alfikri.rizky.share.ui.theme.Typography
import com.alfikri.rizky.story.R
import com.alfikri.rizky.story.presentation.utils.reduceFileImage
import com.alfikri.rizky.story.presentation.utils.rotateBitmap
import com.alfikri.rizky.story.presentation.utils.uriToFile
import com.alfikri.rizky.story.presentation.viewmodel.PostStoryEventState
import com.alfikri.rizky.story.presentation.viewmodel.PostStoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class PostStoryActivity : ComponentActivity() {

    private val viewModel: PostStoryViewModel by viewModels()

    private var getFile: File? = null

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@PostStoryActivity)
            getFile = myFile
            viewModel.setImage(imageUri = selectedImg)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            getFile = myFile
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            viewModel.setImage(imageBitmap = result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircleFeedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PostStoryScreen(viewModel = viewModel, onNavigateBack = { finish() })
                }
            }
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        viewModel.postStoryEventState.observe(this) {
            when (it) {
                is PostStoryEventState.StartCameraX -> {
                    startCameraX()
                }

                is PostStoryEventState.StartGallery -> {
                    startGallery()
                }

                is PostStoryEventState.StartUploadImage -> {
                    startUploadImage(it.description)
                }

                is PostStoryEventState.SuccessUploadImage -> {
                    Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startUploadImage(description: String) {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
            val imageRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                imageRequestBody
            )

            viewModel.postStory(imageMultipart, descriptionRequestBody)
        } else {
            Toast.makeText(
                this,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    companion object {

        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}

@Composable
fun PostStoryScreen(
    modifier: Modifier = Modifier,
    viewModel: PostStoryViewModel,
    onNavigateBack: () -> Unit,
) {

    var isLoading by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    val image = viewModel.imageState.collectAsState().value

    viewModel.postStoryUiState.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> isLoading = true

            is UiState.Error -> {
                isLoading = false
            }

            is UiState.Success -> {
                val context = LocalContext.current
                Toast.makeText(
                    context,
                    "Success adding your story ",
                    Toast.LENGTH_SHORT
                ).show()
                isLoading = false
            }

            is UiState.Empty -> {
                // no implementation
            }
        }
    }

    Scaffold {
        Column(
            modifier = modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            HeaderComponent(onNavigateBack = onNavigateBack)

            val stroke = Stroke(
                width = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            when {
                image.imageUri != null -> {
                    AsyncImage(
                        model = image.imageUri,
                        modifier = Modifier
                            .height(224.dp)
                            .width(224.dp),
                        contentDescription = "Selected image",
                    )
                }

                image.imageBitmap != null -> {
                    AsyncImage(
                        model = image.imageBitmap,
                        modifier = Modifier
                            .height(224.dp)
                            .width(224.dp),
                        contentDescription = "Selected image",
                    )
                }

                else -> {
                    Box(Modifier.size(250.dp, 250.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawRoundRect(color = PrimaryBlue, style = stroke)
                        }
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_image_24),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(224.dp)
                                .width(224.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                PrimaryButtonSmall(
                    text = "Camera",
                    onClick = {
                        viewModel.setUiState(PostStoryEventState.StartCameraX)
                    },
                    modifier = Modifier.weight(0.5f)
                )
                PrimaryButtonSmall(
                    text = "Galery",
                    onClick = { viewModel.setUiState(PostStoryEventState.StartGallery) },
                    modifier = Modifier.weight(0.5f)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                placeholder = {
                    Text(
                        text = "Description",
                        style = Typography.caption.copy(color = Placeholder)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(180.dp)
                    .border(
                        width = 1.dp,
                        color = PrimaryBlue,
                        shape = RoundedCornerShape(8.dp),
                    ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = !isLoading) {
                PrimaryButton(
                    text = "Upload",
                    isEnable = description.isNotEmpty(),
                    onClick = {
                        viewModel.setUiState(
                            PostStoryEventState.StartUploadImage(
                                description
                            )
                        )
                    }
                )
            }

            AnimatedVisibility(visible = isLoading) {
                LoadingCircle()
            }
        }
    }
}

@Composable
private fun HeaderComponent(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Post Story",
                style = Typography.h1.copy(color = Color.Black),
                textAlign = TextAlign.Center
            )

        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onNavigateBack()
                },
                tint = Color.Black
            )
        },
        backgroundColor = Color.White,
        elevation = 0.dp,
        modifier = modifier
    )
}

@Preview
@Composable
fun PostStoryScreenPreview() {
    CircleFeedTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
//            PostStoryScreen()
        }
    }
}