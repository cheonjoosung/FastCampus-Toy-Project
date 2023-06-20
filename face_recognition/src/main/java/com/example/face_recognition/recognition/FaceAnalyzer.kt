package com.example.face_recognition.recognition

import android.graphics.PointF
import android.graphics.RectF
import android.media.Image
import android.util.SizeF
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import androidx.lifecycle.Lifecycle
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlin.math.abs

internal class FaceAnalyzer(
    lifeCycle: Lifecycle,
    private val preview: PreviewView,
    private val listener: FaceAnalyzerListener?
) : ImageAnalysis.Analyzer {

    private var widthScaleFactor = 1F
    private var heightScaleFactor = 1F

    private var preCenterX = 0F
    private var preCenterY = 0F
    private var preWidth = 0F
    private var preHeight = 0F

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setMinFaceSize(0.4F)
        .build()

    private val detector = FaceDetection.getClient(options)
    private var detectStatus = FaceAnalyzerStatus.UnDetect

    private val successListener = OnSuccessListener<List<Face>> { faces ->

        faces.firstOrNull()?.let { face ->

            when (detectStatus) {
                FaceAnalyzerStatus.UnDetect -> {
                    detectStatus = FaceAnalyzerStatus.Detect
                    listener?.detect()
                    listener?.detectProgress(25f, "얼굴인식 완료 \n 왼쪽 눈 깜박")
                }

                FaceAnalyzerStatus.Detect -> {
                    if ((face.leftEyeOpenProbability
                            ?: 0F) > EYE_SUCCESS_VALUE && (face.rightEyeOpenProbability
                            ?: 0F) < EYE_SUCCESS_VALUE
                    ) {
                        detectStatus = FaceAnalyzerStatus.LeftWink
                        listener?.detectProgress(50F, "오른쪽 눈 깜빡")
                    } else {

                    }
                }

                FaceAnalyzerStatus.LeftWink -> {
                    if ((face.leftEyeOpenProbability
                            ?: 0F) < EYE_SUCCESS_VALUE && (face.rightEyeOpenProbability
                            ?: 0F) > EYE_SUCCESS_VALUE
                    ) {
                        detectStatus = FaceAnalyzerStatus.LeftWink
                        listener?.detectProgress(75F, "활짝 웃어보세요")
                    } else {

                    }
                }

                FaceAnalyzerStatus.RightWink -> {
                    if ((face.smilingProbability ?: 0F) > SMILE_SUCCESS_VALUE) {
                        detectStatus = FaceAnalyzerStatus.Smile
                        listener?.detectProgress(100F, "얼굴인식 완료됨")
                        listener?.stopDetect()
                        detector.close()
                    } else {

                    }
                }

                else -> {

                }
            }

            callDetectSize(face)
        } ?: run {
            if (detectStatus != FaceAnalyzerStatus.UnDetect && detectStatus != FaceAnalyzerStatus.Smile) {
                detectStatus = FaceAnalyzerStatus.UnDetect
                listener?.notDetect()
                listener?.detectProgress(0F, "처음으로 돌아갑니다")
            } else {

            }
        }

    }

    private val failureListener = OnFailureListener { e ->
        detectStatus = FaceAnalyzerStatus.UnDetect
    }

    init {
        lifeCycle.addObserver(detector)
    }

    override fun analyze(image: ImageProxy) {
        widthScaleFactor = preview.width.toFloat() / image.height
        heightScaleFactor = preview.height.toFloat() / image.width

        detectFaces(image)
    }

    private fun detectFaces(imageProxy: ImageProxy) {
        val image = InputImage.fromMediaImage(
            imageProxy.image as Image,
            imageProxy.imageInfo.rotationDegrees
        )

        detector.process(image)
            .addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)
            .addOnCompleteListener { imageProxy.close() }
    }

    private fun callDetectSize(face: Face) {
        val rect = face.boundingBox
        val boxWidth = rect.right - rect.left
        val boxHeight = rect.bottom - rect.top

        val left = rect.right.translateX() - (boxWidth / 2)
        val top = rect.top.translateY() - (boxHeight / 2)
        val right = rect.left.translateX() + (boxWidth / 2)
        val bottom = rect.bottom.translateY()

        val width = right - left
        val height = bottom - top
        val centerX = left + width / 2
        val centerY = top + height / 2

        if (abs(preCenterX - centerX) > PIVOT_OFFSET
            || abs(preCenterY - centerY) > PIVOT_OFFSET
            || abs(preWidth - width) > SIZE_OFFSET
            || abs(preHeight - height) > SIZE_OFFSET
        ) {
            listener?.faceSize(
                RectF(left, top, right, bottom),
                SizeF(width, height),
                PointF(centerX, centerY)
            )

            preCenterX = centerX
            preCenterY = centerY
            preWidth = width
            preHeight = height
        }
    }


    private fun Int.translateX() = preview.width - (toFloat() * widthScaleFactor)
    private fun Int.translateY() = toFloat() * heightScaleFactor

    companion object {
        private const val EYE_SUCCESS_VALUE = 0.1F
        private const val SMILE_SUCCESS_VALUE = 0.8F

        private const val PIVOT_OFFSET = 15
        private const val SIZE_OFFSET = 30
    }
}