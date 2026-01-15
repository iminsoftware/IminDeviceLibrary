package com.device.manager.sdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoDisplayDetail implements Parcelable {
  private int type;
  private String deviceName="";
  private int width;
  private int height;
  private float density;
  private int fps;
  private String productID="";
  private int[] Ids;
  private float brightnessDefault;
  private float brightness;
  private int rotation;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public float getDensity() {
    return density;
  }

  public void setDensity(float density) {
    this.density = density;
  }

  public int getFps() {
    return fps;
  }

  public void setFps(int fps) {
    this.fps = fps;
  }

  public String getProductID() {
    return productID;
  }

  public void setProductID(String productID) {
    this.productID = productID;
  }

  public int[] getIds() {
    return Ids;
  }

  public void setIds(int[] ids) {
    Ids = ids;
  }

  public float getBrightnessDefault() {
    return brightnessDefault;
  }

  public void setBrightnessDefault(float brightnessDefault) {
    this.brightnessDefault = brightnessDefault;
  }

  public float getBrightness() {
    return brightness;
  }

  public void setBrightness(float brightness) {
    this.brightness = brightness;
  }

  public int getRotation() {
    return rotation;
  }

  public void setRotation(int rotation) {
    this.rotation = rotation;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.type);
    dest.writeString(this.deviceName);
    dest.writeInt(this.width);
    dest.writeInt(this.height);
    dest.writeFloat(this.density);
    dest.writeInt(this.fps);
    dest.writeString(this.productID);
    dest.writeIntArray(this.Ids);
    dest.writeFloat(this.brightnessDefault);
    dest.writeFloat(this.brightness);
    dest.writeInt(this.rotation);
  }

  public VideoDisplayDetail() {
  }

  protected VideoDisplayDetail(Parcel in) {
    this.type = in.readInt();
    this.deviceName = in.readString();
    this.width = in.readInt();
    this.height = in.readInt();
    this.density = in.readFloat();
    this.fps = in.readInt();
    this.productID = in.readString();
    this.Ids = in.createIntArray();
    this.brightnessDefault = in.readFloat();
    this.brightness = in.readFloat();
    this.rotation = in.readInt();
  }

  public static final Parcelable.Creator<VideoDisplayDetail> CREATOR = new Parcelable.Creator<VideoDisplayDetail>() {
    @Override
    public VideoDisplayDetail createFromParcel(Parcel source) {
      return new VideoDisplayDetail(source);
    }

    @Override
    public VideoDisplayDetail[] newArray(int size) {
      return new VideoDisplayDetail[size];
    }
  };
}
