package com.device.manager.sdk.bean;

/**
 * @Author: Mark
 * @date: 2024/4/25 Time：15:24
 * @description:
 */

public class ScannerSetting {

    private ScannerType scannerType;
    private int scanMode;
    private int characterSet;
    private OutputViaBroadcast outputViaBroadcast;
    private PrefixAndSuffix prefixAndSuffix;
    private String promptMode;
    private OutputMode outputMode;
    private RemoveCharacters removeCharacters;
    private Integer scanInterval;
    private Integer scanTimeout;
    private Integer longScanInterval;


    public void setScannerType(ScannerType scannerType) {
        this.scannerType = scannerType;
    }
    public ScannerType getScannerType() {
        return scannerType;
    }

    public void setScanMode(int scanMode) {
        this.scanMode = scanMode;
    }
    public int getScanMode() {
        return scanMode;
    }

    public void setCharacterSet(int characterSet) {
        this.characterSet = characterSet;
    }
    public int getCharacterSet() {
        return characterSet;
    }

    public void setOutputViaBroadcast(OutputViaBroadcast outputViaBroadcast) {
        this.outputViaBroadcast = outputViaBroadcast;
    }
    public OutputViaBroadcast getOutputViaBroadcast() {
        return outputViaBroadcast;
    }

    public void setPrefixAndSuffix(PrefixAndSuffix prefixAndSuffix) {
        this.prefixAndSuffix = prefixAndSuffix;
    }
    public PrefixAndSuffix getPrefixAndSuffix() {
        return prefixAndSuffix;
    }

    public void setPromptMode(String promptMode) {
        this.promptMode = promptMode;
    }
    public String getPromptMode() {
        return promptMode;
    }

    public void setOutputMode(OutputMode outputMode) {
        this.outputMode = outputMode;
    }
    public OutputMode getOutputMode() {
        return outputMode;
    }

    public void setRemoveCharacters(RemoveCharacters removeCharacters) {
        this.removeCharacters = removeCharacters;
    }
    public RemoveCharacters getRemoveCharacters() {
        return removeCharacters;
    }

    public Integer getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(Integer scanInterval) {
        this.scanInterval = scanInterval;
    }

    public Integer getScanTimeout() {
        return scanTimeout;
    }

    public void setScanTimeout(Integer scanTimeout) {
        this.scanTimeout = scanTimeout;
    }

    public Integer getLongScanInterval() {
        return longScanInterval;
    }

    public void setLongScanInterval(Integer longScanInterval) {
        this.longScanInterval = longScanInterval;
    }

    @Override
    public String toString() {
        return "ScannerSetting{" +
                "scannerType=" + scannerType +
                ", scanMode=" + scanMode +
                ", characterSet=" + characterSet +
                ", outputViaBroadcast=" + outputViaBroadcast +
                ", prefixAndSuffix=" + prefixAndSuffix +
                ", promptMode='" + promptMode + '\'' +
                ", outputMode=" + outputMode +
                ", removeCharacters=" + removeCharacters +
                ", scanInterval=" + scanInterval +
                ", scanTimeout=" + scanTimeout +
                ", longScanInterval=" + longScanInterval +
                '}';
    }
}
