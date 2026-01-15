// IAsyncCallback.aidl
package com.device.manager.server.aidl;

// Declare any non-default types here with import statements

interface IAsyncCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onResult(String result);
}