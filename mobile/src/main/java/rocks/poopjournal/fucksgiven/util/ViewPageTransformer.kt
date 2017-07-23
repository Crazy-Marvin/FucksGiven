package rocks.poopjournal.fucksgiven.util

import android.annotation.SuppressLint
import android.support.v4.view.ViewPager
import android.view.View

internal class ViewPageTransformer(private val mTransformType: ViewPageTransformer.TransformType) : ViewPager.PageTransformer {

    @SuppressLint("NewApi")
    override fun transformPage(page: View, position: Float) {
        val alpha: Float
        val scale: Float
        val translationX: Float

        when (mTransformType) {
            ViewPageTransformer.TransformType.FLOW -> {
                page.rotationY = position * -30f
                return
            }

            ViewPageTransformer.TransformType.SLIDE_OVER -> if (position < 0 && position > -1) {
                // this is the page to the left
                scale = Math.abs(Math.abs(position) - 1) * (1.0f - SCALE_FACTOR_SLIDE) + SCALE_FACTOR_SLIDE
                alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position))
                val pageWidth = page.width
                val translateValue = position * -pageWidth
                if (translateValue > -pageWidth) {
                    translationX = translateValue
                } else {
                    translationX = 0f
                }
            } else {
                alpha = 1f
                scale = 1f
                translationX = 0f
            }

            ViewPageTransformer.TransformType.DEPTH -> if (position > 0 && position < 1) {
                // moving to the right
                alpha = 1 - position
                scale = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position))
                translationX = page.width * -position
            } else {
                // use default for all other cases
                alpha = 1f
                scale = 1f
                translationX = 0f
            }

            ViewPageTransformer.TransformType.ZOOM -> if (position >= -1 && position <= 1) {
                scale = Math.max(MIN_SCALE_ZOOM, 1 - Math.abs(position))
                alpha = MIN_ALPHA_ZOOM + (scale - MIN_SCALE_ZOOM) / (1 - MIN_SCALE_ZOOM) * (1 - MIN_ALPHA_ZOOM)
                val vMargin = page.height * (1 - scale) / 2
                val hMargin = page.width * (1 - scale) / 2
                if (position < 0) {
                    translationX = hMargin - vMargin / 2
                } else {
                    translationX = -hMargin + vMargin / 2
                }
            } else {
                alpha = 1f
                scale = 1f
                translationX = 0f
            }
            ViewPageTransformer.TransformType.FADE -> {
                if (position <= -1.0f || position >= 1.0f) {
                    page.alpha = 0.0f
                    page.isClickable = false
                } else if (position == 0.0f) {
                    page.alpha = 1.0f
                    page.isClickable = true
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    page.alpha = 1.0f - Math.abs(position)
                }
                return
            }

            else -> return
        }

        page.alpha = alpha
        page.translationX = translationX
        page.scaleX = scale
        page.scaleY = scale
    }

    internal enum class TransformType {
        FLOW,
        DEPTH,
        ZOOM,
        SLIDE_OVER,
        FADE
    }

    companion object {
        private val MIN_SCALE_DEPTH = 0.75f
        private val MIN_SCALE_ZOOM = 0.85f
        private val MIN_ALPHA_ZOOM = 0.5f
        private val SCALE_FACTOR_SLIDE = 0.85f
        private val MIN_ALPHA_SLIDE = 0.35f
    }
}
