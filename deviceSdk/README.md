1.0 对外接口文档
    说明：交互方式采用aild，交互数据格式为JSON，用户接入时导入deviceSdk
1.1 String getProp(String name)
    参数：name  需要获取的系统属性key
    返回：对应的系统信息值
1.2  String getProps()
    参数：无
    返回：系统属性所有值，格式为json
    例子：{"customerName":"","deviceName":"Swan 1 Pro","model":"Swan 1 Pro","romtype":""}
1.3  String getDeviceInfo();
    参数：无
    返回：设备信息所有值，格式为json
    例子：{"customerName":"","deviceName":"Swan 1 Pro","model":"Swan 1 Pro","romtype":""}
1.4 void getDeviceInfoAsyn(IAsyncCallback callback);
    参数：回调函数IAsyncCallback
    返回：无
    说明：实现IAsyncCallback类，并在onResult接收返回值格式为json
1.5 String sendDeviceAction(String action,String pamars);
    参数：action 需要系统执行的操作指令,pamars 应用包名
    返回：操作结果
1.6 void sendDeviceActionAsyn(String action,String pamars, IAsyncCallback callback);
    参数：action 需要系统执行的操作指令，callback 回调函数,pamars 应用包名
    返回：无
2.0 操作指令集合
    2.1：Operation_Cashbox_Open:参数应用包名，说明：打开钱箱
    2.2：Operation_Set_AutoStart：参数应用包名，说明：设置默认启动应用
    2.3：Operation_Set_AutoStart_Cance:参数应用包名，说明：取消默认应用启动
    2.4：Operation_Set_Kiosk：参数应用包名，说明：设置霸屏应用
    2.5：Operation_Set_Kiosk_Cance:参数应用包名，说明：取消霸屏应用
    2.6：Operation_Set_Kiosk_Pwd:参数密码串，说明：设置霸屏密码
    2.4：Operation_Allow_Usb_Permissions：参数应用包名，说明：设置应用免UBS权限
    2.5：Operation_Allow_Usb_Permissions_Cance:参数应用包名，说明：取消应用免UBS权限
