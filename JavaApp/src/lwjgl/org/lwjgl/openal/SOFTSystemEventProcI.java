/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 *
 * ALC_SOFT_system_events callback interface, backported so Minecraft 26.1
 * (LWJGL 3.4 API) can link CallbackDeviceTracker.createAndInstall. The
 * interface is never invoked on this port (the extension reports
 * unsupported), so the callback body only needs the right shape
 * (mirrors the CallbackI contract in this port).
 */
package org.lwjgl.openal;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;

import static org.lwjgl.system.APIUtil.apiCreateCIF;
import static org.lwjgl.system.libffi.LibFFI.FFI_DEFAULT_ABI;
import static org.lwjgl.system.libffi.LibFFI.ffi_type_pointer;
import static org.lwjgl.system.libffi.LibFFI.ffi_type_uint32;
import static org.lwjgl.system.libffi.LibFFI.ffi_type_void;

/** Instances of this interface may be passed to the {@link SOFTSystemEvents#alcEventCallbackSOFT alcEventCallbackSOFT} method. */
@FunctionalInterface
@NativeType("ALCEVENTPROCTYPESOFT")
public interface SOFTSystemEventProcI extends CallbackI {

    FFICIF CIF = apiCreateCIF(
        FFI_DEFAULT_ABI,
        ffi_type_void,
        ffi_type_uint32,   // ALCenum eventType
        ffi_type_uint32,   // ALCenum deviceType
        ffi_type_pointer, // ALCdevice *device
        ffi_type_uint32,   // ALCsizei length
        ffi_type_pointer, // const ALCchar *message
        ffi_type_pointer  // void *userParam
    );

    @Override
    default FFICIF getCallInterface() { return CIF; }

    @Override
    default void callback(long args, long unused) {
        // Never invoked on this port: alcEventIsSupportedSOFT always
        // reports ALC_EVENT_NOT_SUPPORTED_SOFT, so Minecraft never enables
        // the events that would call this.
        throw new UnsupportedOperationException("ALC_SOFT_system_events is not supported on this port");
    }

    /**
     * Will be called when an audio device event occurs.
     *
     * @param eventType  the event type
     * @param deviceType the device type
     * @param device     the affected device
     * @param length     the length of {@code message} in bytes
     * @param message    the event message
     * @param userParam  the user-specified parameter
     */
    void invoke(
        @NativeType("ALCenum") int eventType,
        @NativeType("ALCenum") int deviceType,
        @NativeType("ALCdevice *") long device,
        @NativeType("ALCsizei") int length,
        @NativeType("ALCchar const *") long message,
        @NativeType("void *") long userParam);

}
