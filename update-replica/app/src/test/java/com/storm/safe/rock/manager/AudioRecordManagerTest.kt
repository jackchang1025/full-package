package com.storm.safe.rock.manager

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class AudioRecordManagerTest {

    @Test
    fun `not recording initially`() {
        val mgr = AudioRecordManager(RuntimeEnvironment.getApplication())
        assertFalse(mgr.isRecording)
    }

    @Test
    fun `startRecording sets flag`() {
        val mgr = AudioRecordManager(RuntimeEnvironment.getApplication())
        mgr.startRecording()
        assertTrue(mgr.isRecording)
    }

    @Test
    fun `stopRecording clears flag`() {
        val mgr = AudioRecordManager(RuntimeEnvironment.getApplication())
        mgr.startRecording()
        mgr.stopRecording()
        assertFalse(mgr.isRecording)
    }

    @Test
    fun `double start is safe`() {
        val mgr = AudioRecordManager(RuntimeEnvironment.getApplication())
        mgr.startRecording()
        mgr.startRecording()
        assertTrue(mgr.isRecording)
    }

    @Test
    fun `double stop is safe`() {
        val mgr = AudioRecordManager(RuntimeEnvironment.getApplication())
        mgr.startRecording()
        mgr.stopRecording()
        mgr.stopRecording()
        assertFalse(mgr.isRecording)
    }

    @Test
    fun `stop when never started is safe`() {
        val mgr = AudioRecordManager(RuntimeEnvironment.getApplication())
        mgr.stopRecording()
        assertFalse(mgr.isRecording)
    }
}
