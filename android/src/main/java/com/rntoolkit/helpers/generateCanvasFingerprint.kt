package com.rntoolkit.helpers

import android.graphics.*
import java.io.ByteArrayOutputStream
import android.util.Log
import java.lang.Exception


fun generateCanvasFingerprint(): String? {
  return try {
    // Параметры изображения
    val width = 200
    val height = 100
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
      setHasAlpha(true) // Включаем прозрачность
    }
    val canvas = Canvas(bitmap)

    // Градиентный фон
    val gradient = LinearGradient(
      0f, 0f, width.toFloat(), height.toFloat(),
      Color.parseColor("#4285F4"),
      Color.parseColor("#34A853"),
      Shader.TileMode.CLAMP
    )
    Paint().apply {
      shader = gradient
    }.let { canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), it) }

    val text = "A7b!Z#9pL@2q%Y4v^1s&*Kx6j"

    val paint = Paint().apply {
      isAntiAlias = true
      textSize = 14f
      typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
    }

    // Оранжевый прямоугольник
    paint.color = Color.parseColor("#FF6600")
    canvas.drawRect(125f, 1f, 187f, 21f, paint)

    // Текст с тенью (белый)
    paint.setShadowLayer(3f, 2f, 2f, Color.argb(128, 0, 0, 0)) // Полупрозрачная тень
    paint.color = Color.WHITE
    canvas.drawText(text, 2f, 15f, paint)
    paint.clearShadowLayer()

    // Повернутый текст с красной тенью
    canvas.save()
    canvas.rotate(30f, 4f, 32f)
    paint.setShadowLayer(3f, 2f, 2f, Color.RED)
    paint.color = Color.argb(179, 102, 204, 0) // 0.7 alpha
    canvas.drawText(text, 4f, 32f, paint)
    canvas.restore()
    paint.clearShadowLayer()

    // Режим смешивания
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)

    // Фигуры
    drawCustomShapes(canvas, width, height)

    // Конвертация в байты
    ByteArrayOutputStream().use { stream ->
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
      return generateSHA256(stream.toByteArray())
    }
  } catch (e: Exception) {
    null
  }
}

private fun drawCustomShapes(canvas: Canvas, width: Int, height: Int) {
  val paint = Paint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
  }

  // Маленький круг в левом нижнем углу
  paint.color = Color.MAGENTA
  canvas.drawCircle(30f, height - 30f, 30f, paint)

  // Сложная фигура справа по центру
  val centerY = height / 2f
  val rightEdge = width - 50f

  paint.color = Color.argb(179, 255, 0, 255) // Полупрозрачный magenta
  paint.setShadowLayer(8f, 3f, 3f, Color.argb(76, 0, 0, 0)) // Тень с alpha

  Path().apply {
    moveTo(rightEdge, centerY)
    lineTo(rightEdge - 40f, centerY - 60f)
    quadTo(rightEdge - 80f, centerY - 30f, rightEdge - 70f, centerY)
    cubicTo(
      rightEdge - 90f, centerY + 20f,
      rightEdge - 50f, centerY + 40f,
      rightEdge - 30f, centerY + 20f
    )
    lineTo(rightEdge - 10f, centerY + 10f)
    close()
  }.let { canvas.drawPath(it, paint) }

  paint.clearShadowLayer()
}
