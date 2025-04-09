package com.jg.composeplayground.photoframe.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.jg.composeplayground.photoframe.viewmodel.PhotoFrameViewModel


@Composable
fun PhotoFrameRoute(
  viewModel: PhotoFrameViewModel = hiltViewModel(),
  onBackPress: () -> Unit
) {
    PhotoFrameScreen(
      viewModel = viewModel,
      onImageSelected = viewModel::handleImageSelected,
      onBackPress = onBackPress
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoFrameScreen(
    viewModel: PhotoFrameViewModel,
    onImageSelected: (Uri) -> Unit = {},
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    var showPermissionDeniedDialog by remember { mutableStateOf(false) }
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }
    
    // ViewModel에서 UI 상태 가져오기
    val uiState by viewModel.uiState.collectAsState()
    
    // 갤러리에서 이미지 선택 결과 처리
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }
    
    // 외부 저장소 읽기 권한 상태
    val storagePermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    ) { isGranted ->
        if (isGranted) {
            // 권한이 승인되면 갤러리 열기
            galleryLauncher.launch("image/*")
        } else {
            // 권한이 거부되면 다이얼로그 표시
            showPermissionDeniedDialog = true
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("전자 액자") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                ) {
                    // 선택된 이미지 표시
                    if (uiState.hasPhoto && uiState.selectedPhotoUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(uiState.selectedPhotoUri),
                            contentDescription = "선택된 사진",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // 빈 상태 표시
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .aspectRatio(1f)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "사진을 추가해주세요",
                                color = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { 
                            if (storagePermissionState.status.isGranted) {
                                // 이미 권한이 있으면 갤러리 바로 열기
                                galleryLauncher.launch("image/*")
                            } else if (storagePermissionState.status.shouldShowRationale) {
                                // 권한 요청 이유 설명 필요
                                showPermissionRationaleDialog = true
                            } else {
                                // 처음 권한 요청
                                storagePermissionState.launchPermissionRequest()
                            }
                        },
                    ) {
                        Text(
                            text = "사진 추가하기",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {},
                        enabled = uiState.hasPhoto // 사진이 있을 때만 활성화
                    ) {
                        Text(
                            text = "전자 액자 실행하기",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
    
    // 권한 필요성 설명 다이얼로그
    if (showPermissionRationaleDialog) {
        PermissionRationaleDialog(
            onDismiss = { showPermissionRationaleDialog = false },
            onConfirm = {
                showPermissionRationaleDialog = false
                storagePermissionState.launchPermissionRequest()
            }
        )
    }
    
    // 권한 거부 안내 다이얼로그
    if (showPermissionDeniedDialog) {
        PermissionDeniedDialog(
            onDismiss = { showPermissionDeniedDialog = false },
            onConfirm = {
                showPermissionDeniedDialog = false
                openAppSettings(context)
            }
        )
    }
}

@Composable
fun PermissionRationaleDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("권한 필요") },
        text = { Text("사진을 선택하기 위해 저장소 접근 권한이 필요합니다.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun PermissionDeniedDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("권한 거부됨") },
        text = { Text("저장소 접근 권한이 거부되었습니다. 앱 설정에서 권한을 허용해주세요.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("설정으로 이동")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

// 앱 설정 화면으로 이동하는 함수
private fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}