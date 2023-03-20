package com.example.mvvmretrofit.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import coil.bitmap.BitmapPool

class CustomWatermarkTransformation(private val waterMarkContent: String, @ColorInt private val waterMarkColor: Int, private val waterMarkSize: Float) : coil.transform.Transformation {

    private val waterMark: String = "waterMark"
    private val textColor: String = "textColor"
    private val textSize: String = "textSize"

    override fun key(): String = "${CustomWatermarkTransformation::class.java.name}-${waterMark}-${textColor}-${textSize}"

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: coil.size.Size): Bitmap {
        val width = input.width
        val height = input.height
        val config = input.config
        val output = pool.get(width, height, config)
        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.textSize = waterMarkSize
        paint.color = waterMarkColor

        //先绘制目标bitmap
        canvas.drawBitmap(input, 0f, 0f, paint)

        //绘制水印
        canvas.rotate(40f, width / 2f, height / 2f)
        val textWidth = paint.measureText(waterMarkContent)
        canvas.drawText(waterMarkContent, (width - textWidth) / 2f, height / 2f, paint)
        return output
    }

}