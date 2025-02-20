/**
 * Copyright (c) 2019 Cotta & Cush Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cottacush.android.hiddencam

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import android.os.Handler
import android.os.Looper

/**
 * A [LifecycleOwner] to manage the Lifecycle of the camera engine.
 */
internal class HiddenCamLifeCycleOwner : LifecycleOwner {

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private val mainHandler = Handler(Looper.getMainLooper()) // Handler for main thread

    init {
        // Ensure markState is called on the main thread during initialization
        runOnMainThread {
            lifecycleRegistry.markState(Lifecycle.State.CREATED)
        }
    }

    fun start() = runOnMainThread {
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    fun stop() = runOnMainThread {
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    fun tearDown() = runOnMainThread {
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    // Helper function to ensure code runs on the main thread
    private fun runOnMainThread(action: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // Already on the main thread, execute directly
            action()
        } else {
            // Post to the main thread
            mainHandler.post(action)
        }
    }
}
