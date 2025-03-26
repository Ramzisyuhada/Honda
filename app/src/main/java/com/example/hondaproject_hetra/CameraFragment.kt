package com.example.hondaproject_hetra

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.hondaproject_hetra.Model.Image
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.common.util.concurrent.ListenableFuture
import com.surendramaran.yolov8tflite.Adapter.AdapterImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CameraFragment : Fragment() {

    private val CAMERA_PERMISSION_CODE = 1001
    private var previewView: PreviewView? = null
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    private var imageCapture: ImageCapture? = null
    private var DaftarGambar = ArrayList<Bitmap>()
    private var   i = 0
    val DisplayImageFragment = DisplayImageFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        previewView = view.findViewById(R.id.viewFinder)
        val btnCapture: Button = view.findViewById(R.id.btnCapture)

        btnCapture.setOnClickListener {
            takePhoto()
        }
        return view
    }

    fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val bitmap = imageProxyToBitmap(image) // Konversi ke Bitmap
                    DisplayImageFragment.DaftarGambar.add(bitmap)
                    i++
                    Log.i("CameraX", i.toString() )
                    if(i > 2){

                        parentFragmentManager.beginTransaction().replace(R.id.Frame,DisplayImageFragment)
                            .addToBackStack(null)
                            .commit()

                    }
                    image.close()

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "Gagal menangkap gambar", exception)
                }
            }
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.NavButton)
        bottomNav.visibility = View.GONE

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            setupCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCamera()
            } else {
                Toast.makeText(requireContext(), "Izin Kamera Diperlukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture?.addListener({
            val cameraProvider = cameraProviderFuture?.get()
            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    startCamera(cameraProvider)
                }
            })
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCamera(cameraProvider: ProcessCameraProvider?) {
        val view = previewView ?: return

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(view.surfaceProvider)
        }
        imageCapture = ImageCapture.Builder().build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK) // Kamera belakang
            .build()

        try {
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                viewLifecycleOwner, cameraSelector, preview,imageCapture
            )
        } catch (exc: Exception) {
            Log.e("CameraFragment", "Gagal memulai kamera", exc)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.NavButton)
//            bottomNav.visibility = View.VISIBLE

        previewView = null // 🔥 Hindari memory leak dengan menghapus referensi previewView
    }
}
