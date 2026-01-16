package com.example.imindevicelibrary.bean;

import java.io.Serializable;
import java.util.List;

public class ApplicationPolicy implements Serializable {
    public String packageName;
    public String apkUrl;
    public Boolean kioskMode;
    public List<PermissionGrant> permissionGrants;
    public Boolean disabled;
    public Boolean hidden;
    public String[] delegatedScopes;
    public Boolean uninstallAppDisable;
    public String connectedWorkAndPersonalApp;
    public String userControlSettings;
    public String displayName;
}
