package com.storm.safe.rock.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Custom View that renders floating particle animation effects.
 *
 * JADX reference: view/ParticleView.java (131 LOC)
 * Draws colored circles (particles) that float upward with varying speeds,
 * opacity, and drift. Used as a decorative background effect in the UI.
 *
 * Note: This is a UI-only component. Core rendering logic is replicated faithfully;
 * p000 dependencies (RunnableC0165ca, RunnableC0941o6, km0, etc.) are inlined.
 */
class ParticleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    /**
     * Particle data holder.
     * JADX reference: p000.km0 — holds position, size, speed, opacity, drift, and color.
     */
    data class Particle(
        var x: Float = 0f,
        var y: Float = 0f,
        var radius: Float = 0f,
        var alpha: Float = 0f,
        var speed: Float = 0f,
        var drift: Float = 0f,
        var red: Int = 0,
        var green: Int = 0,
        var blue: Int = 0
    )

    companion object {
        private const val TAG = "ParticleView"
        private const val PARTICLE_COUNT = 30
        private const val ANIMATION_FRAME_MS = 100L

        /** Color palette: (R, G, B) triples for particles */
        // JADX: Triple(220, 235, 255), Triple(180, 210, 255), Triple(255, 255, 255), Triple(160, 200, 240)
        val COLOR_PALETTE: List<Triple<Int, Int, Int>> = listOf(
            Triple(220, 235, 255),
            Triple(180, 210, 255),
            Triple(255, 255, 255),
            Triple(160, 200, 240)
        )
    }

    val particles: MutableList<Particle> = mutableListOf()
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val handler: Handler = Handler(Looper.getMainLooper())
    var isAnimating: Boolean = false
        internal set

    private val animationRunnable = object : Runnable {
        override fun run() {
            if (!isAnimating) return
            // Update particle positions
            for (particle in particles) {
                particle.y -= particle.speed
                particle.x += particle.drift
                // Reset particles that go off-screen
                if (particle.y + particle.radius < 0) {
                    resetParticle(particle, width, height, fromBottom = true)
                }
            }
            invalidate()
            handler.postDelayed(this, ANIMATION_FRAME_MS)
        }
    }

    /**
     * Creates a new particle with randomized properties.
     *
     * JADX: m212472a0(int width, int height, boolean randomY)
     */
    fun createParticle(viewWidth: Int, viewHeight: Int, randomY: Boolean): Particle {
        val color = COLOR_PALETTE[Random.nextInt(COLOR_PALETTE.size)]
        return Particle(
            x = Random.nextFloat() * viewWidth,
            y = if (randomY) Random.nextFloat() * viewHeight else viewHeight + Random.nextFloat() * 50f,
            radius = Random.nextFloat() * 3.5f + 1.5f,
            alpha = Random.nextFloat() * 0.55f + 0.1f,
            speed = Random.nextFloat() * 1.2f + 0.4f,
            drift = (Random.nextFloat() - 0.5f) * 0.6f,
            red = color.first,
            green = color.second,
            blue = color.third
        )
    }

    private fun resetParticle(particle: Particle, viewWidth: Int, viewHeight: Int, fromBottom: Boolean) {
        val color = COLOR_PALETTE[Random.nextInt(COLOR_PALETTE.size)]
        particle.x = Random.nextFloat() * viewWidth
        particle.y = if (fromBottom) viewHeight + Random.nextFloat() * 50f else Random.nextFloat() * viewHeight
        particle.radius = Random.nextFloat() * 3.5f + 1.5f
        particle.alpha = Random.nextFloat() * 0.55f + 0.1f
        particle.speed = Random.nextFloat() * 1.2f + 0.4f
        particle.drift = (Random.nextFloat() - 0.5f) * 0.6f
        particle.red = color.first
        particle.green = color.second
        particle.blue = color.third
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAnimating = true
        handler.postDelayed(animationRunnable, ANIMATION_FRAME_MS)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAnimating = false
        handler.removeCallbacks(animationRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        for (particle in particles) {
            val argb = Color.argb(
                (particle.alpha * 255f).roundToInt().coerceIn(0, 255),
                particle.red,
                particle.green,
                particle.blue
            )
            paint.color = argb
            canvas.drawCircle(particle.x, particle.y, particle.radius, paint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        particles.clear()
        for (i in 0 until PARTICLE_COUNT) {
            particles.add(createParticle(w, h, randomY = true))
        }
    }
}
