package com.device.manager.sdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ：Chenjk
 * @version 1.0
 * @createTime ：2025/2/13 18:29
 **/
public class BarcodeScannerDetail  implements Parcelable {
    private int type;  //HID:1 SSI:2 mipi:3
    private String model="";
    private String serialNumber=""; //mipi type: unavailable usb type: available
    private String firmwareVersion=""; //mipi-无 usb才有
    private String manufacturerName=""; //
    private String vendorID=""; //mipi-无 usb才有
    private String productID=""; //mipi-无 usb才有
    private String deviceName=""; //
    private int status; //1：已连接 0：未连接
    private String outputEncode=""; //outputEndcode : 输出编码格式

    public BarcodeScannerDetail(){}

    protected BarcodeScannerDetail(Parcel in) {
        type = in.readInt();
        model = in.readString();
        serialNumber = in.readString();
        firmwareVersion = in.readString();
        manufacturerName = in.readString();
        vendorID = in.readString();
        productID = in.readString();
        deviceName = in.readString();
        status = in.readInt();
        outputEncode = in.readString();
    }

    public static final Creator<BarcodeScannerDetail> CREATOR = new Creator<BarcodeScannerDetail>() {
        @Override
        public BarcodeScannerDetail createFromParcel(Parcel in) {
            return new BarcodeScannerDetail(in);
        }

        @Override
        public BarcodeScannerDetail[] newArray(int size) {
            return new BarcodeScannerDetail[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOutputEncode() {
        return outputEncode;
    }

    public void setOutputEncode(String outputEncode) {
        this.outputEncode = outputEncode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(model);
        dest.writeString(serialNumber);
        dest.writeString(firmwareVersion);
        dest.writeString(manufacturerName);
        dest.writeString(vendorID);
        dest.writeString(productID);
        dest.writeString(deviceName);
        dest.writeInt(status);
        dest.writeString(outputEncode);
    }
}
