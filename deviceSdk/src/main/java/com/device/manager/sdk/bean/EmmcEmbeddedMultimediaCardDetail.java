package com.device.manager.sdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EmmcEmbeddedMultimediaCardDetail implements Parcelable {

    private String type="";
    private String model="";
    private String serialNumber="";
    private String firmwareVersion="";
    private String manufacturerName="";

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.model);
        dest.writeString(this.serialNumber);
        dest.writeString(this.firmwareVersion);
        dest.writeString(this.manufacturerName);
    }

    public EmmcEmbeddedMultimediaCardDetail() {
    }

    protected EmmcEmbeddedMultimediaCardDetail(Parcel in) {
        this.type = in.readString();
        this.model = in.readString();
        this.serialNumber = in.readString();
        this.firmwareVersion = in.readString();
        this.manufacturerName = in.readString();
    }

    public static final Parcelable.Creator<EmmcEmbeddedMultimediaCardDetail> CREATOR = new Parcelable.Creator<EmmcEmbeddedMultimediaCardDetail>() {
        @Override
        public EmmcEmbeddedMultimediaCardDetail createFromParcel(Parcel source) {
            return new EmmcEmbeddedMultimediaCardDetail(source);
        }

        @Override
        public EmmcEmbeddedMultimediaCardDetail[] newArray(int size) {
            return new EmmcEmbeddedMultimediaCardDetail[size];
        }
    };
}
