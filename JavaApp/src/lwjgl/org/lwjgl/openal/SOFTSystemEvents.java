/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 *
 * ALC_SOFT_system_events bindings, backported into the Pojav-style LWJGL
 * override so Minecraft 26.1 (LWJGL 3.4 API) finds the class it references
 * while initializing its audio device tracker at SoundEngine startup.
 *
 * The launcher's OpenAL (from the game's audio backend) does not expose the
 * SOFT_system_events extension, so alcEventIsSupportedSOFT reports
 * ALC_EVENT_NOT_SUPPORTED_SOFT. Minecraft then falls back to its
 * PollingDeviceTracker, which is exactly the pre-26.1 behaviour.
 */
package org.lwjgl.openal;

import java.nio.IntBuffer;

import org.lwjgl.system.*;

import static org.lwjgl.system.Checks.*;
import static org.lwjgl.system.MemoryUtil.*;

/** Binding to the ALC_SOFT_system_events extension. */
public final class SOFTSystemEvents {

    static final int ALC_PLAYBACK_DEVICE_SOFT                 = 0x19D4;
    static final int ALC_CAPTURE_DEVICE_SOFT                  = 0x19D5;
    static final int ALC_EVENT_TYPE_DEFAULT_DEVICE_CHANGED_SOFT = 0x19D6;
    static final int ALC_EVENT_TYPE_DEVICE_ADDED_SOFT          = 0x19D7;
    static final int ALC_EVENT_TYPE_DEVICE_REMOVED_SOFT        = 0x19D8;
    static final int ALC_EVENT_SUPPORTED_SOFT                  = 0x19D9;
    static final int ALC_EVENT_NOT_SUPPORTED_SOFT              = 0x19DA;

    private SOFTSystemEvents() {}

    /**
     * Returns whether the given event type and device type combination is
     * supported by the OpenAL implementation.
     *
     * <p>This port has no system-events extension, so it always reports
     * {@code ALC_EVENT_NOT_SUPPORTED_SOFT}, which makes Minecraft fall back
     * to its polling device tracker.</p>
     */
    public static int alcEventIsSupportedSOFT(int eventType, int deviceType) {
        return ALC_EVENT_NOT_SUPPORTED_SOFT;
    }

    /** Stub: system events are not supported here, control requests fail. */
    public static boolean alcEventControlSOFT(IntBuffer events, boolean enable) {
        return false;
    }

    /** Stub: system events are not supported here. */
    public static boolean alcEventControlSOFT(int[] events, boolean enable) {
        return false;
    }

    /** Stub: system events are not supported here. */
    public static void alcEventCallbackSOFT(SOFTSystemEventProcI callback, long userParam) { }

}
