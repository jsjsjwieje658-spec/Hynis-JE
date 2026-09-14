/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 *
 * GLFW 3.4 IME status callback interface, backported into the Pojav-style
 * GLFW override so Minecraft 26.1 (LWJGL 3.4 API) finds the interface it
 * implements in InputConstants.setupKeyboardCallbacks. Never invoked at
 * runtime on this port (mirrors GLFWCharCallbackI).
 */
package org.lwjgl.glfw;

import org.lwjgl.system.*;
import org.lwjgl.system.libffi.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.APIUtil.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.libffi.LibFFI.*;

/** Instances of this interface may be passed to the {@link GLFW#glfwSetIMEStatusCallback SetIMEStatusCallback} method. */
@FunctionalInterface
@NativeType("GLFWimestatusfun")
public interface GLFWIMEStatusCallbackI extends CallbackI {

    FFICIF CIF = apiCreateCIF(
        FFI_DEFAULT_ABI,
        ffi_type_void,
        ffi_type_pointer // GLFWwindow *window
    );

    @Override
    default FFICIF getCallInterface() { return CIF; }

    @Override
    default void callback(long args, long unused) {
        invoke(memGetAddress(args));
    }

    /**
     * Will be called when the IME status of a window changes.
     *
     * @param window the window that received the event
     */
    void invoke(@NativeType("GLFWwindow *") long window);

}
