/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 *
 * GLFW 3.4 preedit callback, backported into the Pojav-style GLFW override
 * so Minecraft 26.1 (LWJGL 3.4 API) finds the class it references during
 * InputConstants.setupKeyboardCallbacks. Follows the exact structure of
 * GLFWCharCallback in this port.
 */
package org.lwjgl.glfw;

import javax.annotation.*;

import org.lwjgl.system.*;

import java.nio.IntBuffer;

import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Instances of this class may be passed to the {@link GLFW#glfwSetPreeditCallback SetPreeditCallback} method.
 */
public abstract class GLFWPreeditCallback extends Callback implements GLFWPreeditCallbackI {

    /**
     * Creates a {@code GLFWPreeditCallback} instance from the specified function pointer.
     */
    public static GLFWPreeditCallback create(long functionPointer) {
        GLFWPreeditCallbackI instance = Callback.get(functionPointer);
        return instance instanceof GLFWPreeditCallback
            ? (GLFWPreeditCallback)instance
            : new Container(functionPointer, instance);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code functionPointer} is {@code NULL}. */
    @Nullable
    public static GLFWPreeditCallback createSafe(long functionPointer) {
        return functionPointer == NULL ? null : create(functionPointer);
    }

    /** Creates a {@code GLFWPreeditCallback} instance that delegates to the specified {@code GLFWPreeditCallbackI} instance. */
    public static GLFWPreeditCallback create(GLFWPreeditCallbackI instance) {
        return instance instanceof GLFWPreeditCallback
            ? (GLFWPreeditCallback)instance
            : new Container(instance.address(), instance);
    }

    protected GLFWPreeditCallback() {
        super(GLFWPreeditCallbackI.CIF);
    }

    GLFWPreeditCallback(long functionPointer) {
        super(functionPointer);
    }

    /** See {@link GLFW#glfwSetPreeditCallback SetPreeditCallback}. */
    public GLFWPreeditCallback set(long window) {
        glfwSetPreeditCallback(window, this);
        return this;
    }

    private static final class Container extends GLFWPreeditCallback {

        private final GLFWPreeditCallbackI delegate;

        Container(long functionPointer, GLFWPreeditCallbackI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public void invoke(long window, int preeditCount, IntBuffer preeditString, int blockCount, IntBuffer blockSizes, int focusedBlock, int caret) {
            delegate.invoke(window, preeditCount, preeditString, blockCount, blockSizes, focusedBlock, caret);
        }

    }

}
