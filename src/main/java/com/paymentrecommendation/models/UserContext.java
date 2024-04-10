package com.paymentrecommendation.models;

public class UserContext {
    // There can be more attributes within the UserContext. We are only keeping the
    // DeviceContext as of now.
    private DeviceContext deviceContext;

    public UserContext(DeviceContext deviceContext) {
        this.deviceContext = deviceContext;
    }

    public DeviceContext getDeviceContext() {
        return deviceContext;
    }

    public void setDeviceContext(DeviceContext deviceContext) {
        this.deviceContext = deviceContext;
    }
}
