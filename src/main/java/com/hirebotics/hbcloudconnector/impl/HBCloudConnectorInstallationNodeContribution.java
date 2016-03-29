package com.hirebotics.hbcloudconnector.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.component.InputEvent;
import com.ur.urcap.api.ui.component.InputTextField;

import java.lang.ref.SoftReference;
import java.util.Timer;

/**
 * Created by matthewbush1 on 3/27/16.
 */
public class HBCloudConnectorInstallationNodeContribution implements InstallationNodeContribution {

    private static final String IP_ADDR = "ipaddr";
    private static final String ROBOT_GUID = "robotguid";
    private static final String STD_MSG_LEVEL = "stdmessagelevel";
    private static final String ERR_MSG_LEVEL = "errmessagelevel";

    private DataModel model;
    private Timer uiTimer;
    private URCapAPI api;

    public void setStdMessageLevel(String level){
        model.set(STD_MSG_LEVEL, level);
    }

    public String getStdMessageLevel() {
        return model.get(STD_MSG_LEVEL, "");
    }

    public void setErrMessageLevel(String level){
        model.set(ERR_MSG_LEVEL, level);
    }

    public String getErrMessageLevel() {
        return model.get(ERR_MSG_LEVEL, "");
    }

    private void setIpAddr(String ipAdrr) {
        model.set(IP_ADDR, ipAdrr);
    }

    private String getIpAddr() {
        return model.get(IP_ADDR, "");
    }

    private void setRobotGuid(String guid){
        model.set(ROBOT_GUID, guid);
    }

    private String getRobotGuid(){
        return model.get(ROBOT_GUID, "");
    }

    public HBCloudConnectorInstallationNodeContribution(URCapAPI api, DataModel model){
        this.api = api;
        this.model = model;
    }

    @Input(id = "ipaddr")
    private InputTextField ipAddrField;

    @Input(id = "robotguid")
    private InputTextField robotGuidField;

    @Input(id = "stdmessagelevel")
    public InputTextField stdMessageLevel;

    @Input(id = "errmessagelevel")
    public InputTextField errMessageLevel;

    @Input(id = "ipaddr")
    public void onIpAddrChange(InputEvent event){
        if (event.getEventType() == InputEvent.EventType.ON_CHANGE){
            setIpAddr(ipAddrField.getText());
        }
    }

    @Input(id = "robotguid")
    public void onRobotGuidChange(InputEvent event){
        if (event.getEventType() == InputEvent.EventType.ON_CHANGE){
            setRobotGuid(robotGuidField.getText());
        }
    }

    @Input(id = "stdmessagelevel")
    public void onMessageLevelChange(InputEvent event){
        if (event.getEventType() == InputEvent.EventType.ON_CHANGE){
            setStdMessageLevel(stdMessageLevel.getText());
        }
    }

    @Input(id = "errmessagelevel")
    public void onErrorMessageLevelChange(InputEvent event){
        if (event.getEventType() == InputEvent.EventType.ON_CHANGE){
            setErrMessageLevel(errMessageLevel.getText());
        }
    }

    @Override
    public void openView(){
        ipAddrField.setText(getIpAddr());
        robotGuidField.setText(getRobotGuid());
        stdMessageLevel.setText(getStdMessageLevel());
        errMessageLevel.setText(getErrMessageLevel());
    }

    @Override
    public void closeView(){

    }

    @Override
    public void generateScript(ScriptWriter script){
        script.assign("robot_uid", "\"" + getRobotGuid() + "\"");
        script.assign("ip_address", "\"" + getIpAddr() + "\"");
    }





}
