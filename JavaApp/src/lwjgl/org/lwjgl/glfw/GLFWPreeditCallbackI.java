/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 *
 * GLFW 3.4 preedit callback interface, backported into the Pojav-style
 * GLFW override so Minecraft 26.1 (LWJGL 3.4 API) finds the interface it
 * implements in InputConstants.setupKeyboardCallbacks.
 *
 * The iOS input bridge never emits preedit (IME composition) events, so
 * the callback is never invoked at runtime; it only needs to exist with
 * the right shape (mirrors GLFWCharCallbackI in this port).
 */
package org.lwjgl.glfw;

import org.lwjgl.system.*;
import org.lwjgl.system.libffi.*;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.APIUtil.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.libffi.LibFFI.*;

/** Instances of this interface may be passed to the {@link GLFW#glfwSetPreeditCallback SetPreeditCallback} method. */
@FunctionalInterface
@NativeType("GLFWpreeditfun")
public interface GLFWPreeditCallbackI extends CallbackI {

    FFICIF CIF = apiCreateCIF(
        FFI_DEFAULT_ABI,
        ffi_type_void,
        ffi_type_pointer, // GLFWwindow *window
        ffi_type_uint32,  // int preedit_count
        ffi_type_pointer, // const unsigned int *preedit_string
        ffi_type_uint32,  // int block_count
        ffi_type_pointer,  // int *block_sizes
        ffi_type_uint32,  // int focused_block
        ffi_type_uint32   // int caret
    );

    @Override
    default FFICIF getCallInterface() { return CIF; }

    @Override
    default void callback(long args, long unused) {
        long window = memGetAddress(args);
        args += POINTER_SIZE;
        int preeditCount = (int)memGetInt(args);
        args += 4;
        long preeditString = memGetAddress(args);
        args += POINTER_SIZE;
        int blockCount = (int)memGetInt(args);
        args += 4;
        long blockSizes = memGetAddress(args);
        args += POINTER_SIZE;
        int focusedBlock = (int)memGetInt(args);
        args += 4;
        int caret = (int)memGetInt(args);

        invoke(
            window,
            preeditCount,
            preeditString == 0L ? null : memIntBuffer(preeditString, preeditCount),
            blockCount,
            blockSizes == 0L ? null : memIntBuffer(blockSizes, blockCount),
            focusedBlock,
            caret
        );
    }

    /**
     * Will be called when the preedit (IME composition) text of a window changes.
     *
     * @param window        the window that received the event
     * @param preeditCount  the number of code points in {@code preeditString}
     * @param preeditString the preedit text code points, or {@code null}
     * @param blockCount    the number of blocks in {@code blockSizes}
     * @param blockSizes    the preedit block sizes, or {@code null}
     * @param focusedBlock  the currently focused block, or -1 if none
     * @param caret         the caret position within the focused block, or -1 if none
     */
    void invoke(
        @NativeType("GLFWwindow *") long window,
        @NativeType("int") int preeditCount,
        @NativeType("unsigned const int *") IntBuffer preeditString,
        @NativeType("int") int blockCount,
        @NativeType("int *") IntBuffer blockSizes,
        @NativeType("int") int focusedBlock,
        @NativeType("int") int caret);

}
