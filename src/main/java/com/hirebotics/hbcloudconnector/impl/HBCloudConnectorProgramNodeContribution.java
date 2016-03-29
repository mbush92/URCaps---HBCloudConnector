package com.hirebotics.hbcloudconnector.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.*;
import com.ur.urcap.api.ui.component.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HBCloudConnectorProgramNodeContribution implements ProgramNodeContribution {
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";
    private static final String SCRIPT = "script";
    private static final String SELECT_LIST_INDEX = "selectListIndex";

    private final DataModel model;
    private final ProgramTreeNode programTreeNode;
    private final URCapAPI api;

    private boolean nodeComplete;
    private BufferedImage img;
    private int messageType;

    @Input(id = "startprogram")
    private InputRadioButton apiStartProgram;

    @Input(id = "sendmessage")
    private InputRadioButton apiSendMessage;

    @Label(id = "logmessage")
    private LabelComponent logmessage;

    @Input(id = "messagestring")
    private InputTextField sendMessageString;

    @Label(id = "messagestringlabel")
    private LabelComponent messageStringLabel;

    @Select(id = "selectmessagetype")
    private SelectDropDownList selectMessageType;

    @Img(id = "logo" )
    private ImgComponent logoImage;

    private void setMessageSelected(int msgTypeIndex) {
        messageType = msgTypeIndex;
        model.set(SELECT_LIST_INDEX, msgTypeIndex);
    }

    private int getMessageSelected() {
        return model.get(SELECT_LIST_INDEX, 0);
    }

    private int getMessageType(){
        return selectMessageType.getSelectedIndex();
    }

    private void setMessage(String msg) {
        if ("".equals(msg)) {
            model.remove(MESSAGE);
        } else {
            model.set(MESSAGE, msg);
        }
    }

    private void setTitle(String title){
        if ("".equals(title)) {
            model.remove(TITLE);
        } else {
            model.set(TITLE, title);
        }
    }

    private String getTitle() {
        return model.get(TITLE, "");
    }

    private void setProgramNodeComplete(boolean status){
        nodeComplete = status;
    }

    private boolean getProgramNodeComplete(){
        return nodeComplete;
    }

    private void setLogMessage(String msg){
        logmessage.setText(msg);
    }

    private List selectMessageTypeList = new ArrayList();

    public List getSelectMessageTypeList() {
        if (selectMessageTypeList.size() == 0) {
            selectMessageTypeList.add("Select Message Type");
            selectMessageTypeList.add("Regular");
            selectMessageTypeList.add("Error");
        }
        return selectMessageTypeList;
    }

    public void setSelectMessageTypeList(){
        selectMessageType.setItems(selectMessageTypeList);
    }

    public HBCloudConnectorProgramNodeContribution(URCapAPI api, DataModel model, ProgramTreeNode programTreeNode) {
        this.api = api;
        this.model = model;
        this.programTreeNode = programTreeNode;
        setProgramTreeNodeTitle();
        setProgramNodeComplete(false);
        //setLogMessage("\"" + sendErrorMessageStringBox.isEnabled() + "\"");

        try{
            InputStream is = getClass().getResourceAsStream("/com/hirebotics/hbcloudconnector/Hirebotics.jpg");
            img = ImageIO.read(is);
        } catch (IOException e) {
        }
    }

    @Select(id = "selectmessagetype")
    public void onSelectMessageTypeChange(SelectEvent event){
        setLogMessage("select options " + event.getEvent());
        if(event.getEvent() == SelectEvent.EventType.ON_SELECT){
           setLogMessage("select option " + selectMessageType.getSelectedItem());
           sendMessageString.setEnabled(true);
            messageStringLabel.setEnabled(true);
            setMessageSelected(selectMessageType.getSelectedIndex());
       }
    }
    @Input(id = "startprogram")
    public void onStartProgramRadioCheck(InputEvent event){
        setProgramNodeComplete(false);
        if(event.getEventType() == InputEvent.EventType.ON_CHANGE) {
            setTitle("Send Start Program");
            setProgramTreeNodeTitle();
            setLogMessage("Component was: " + event.getComponent());
            setScriptCode("startProgram()");
            setProgramNodeComplete(true);
        }
    }

    @Input(id = "sendmessage")
    public void onSendMessageRadioCheck(InputEvent event){
        setProgramNodeComplete(false);
        InputEvent.EventType eventType = event.getEventType();
        setLogMessage("Component was send message, event:" + "\"" + eventType + "\"");
        if(event.getEventType() == InputEvent.EventType.ON_CHANGE) {
            setTitle("Send Message");
            setProgramTreeNodeTitle();
            selectMessageType.setEnabled(true);
            if(selectMessageType.getSelectedIndex()>0) {
                sendMessageString.setEnabled(true);
            }

        }
    }

    @Input(id = "messagestring")
    public void onSendMessageTextInput(InputEvent event){
        setProgramNodeComplete(false);
        HBCloudConnectorInstallationNodeContribution c = api.getInstallationNode(HBCloudConnectorInstallationNodeContribution.class);
        String stdMessageLevel = c.getStdMessageLevel();
        String errMessageLevel = c.getErrMessageLevel();
        if(event.getEventType() == InputEvent.EventType.ON_CHANGE) {
            String msg = sendMessageString.getText();
            if ("".equals(msg)) {
                return ;
            }
            String msgHeader;
            switch (getMessageType()) {
                case 1:
                    msgHeader = "sendMessage(" + "\"" + "[" + stdMessageLevel + "] ";
                    break;
                case 2:
                    msgHeader = "sendErrorMessage(" + "\"" + "[" +  errMessageLevel +"] ";
                    break;
                default:
                    return;
            }
            String msgString = msgHeader + msg + "\")";
            setScriptCode(msgString);
            setProgramNodeComplete(true);
            setLogMessage(msgString);

        }

    }


    private void setScriptCode(String script){
        if ("".equals(script)) {
            model.remove(SCRIPT);
        } else {
            model.set(SCRIPT, script);
        }

    }

    private String getScriptCode(){
        setLogMessage("Script code: "+ model.get(SCRIPT,""));
        return (model.get(SCRIPT, ""));

    }

    private void setProgramTreeNodeTitle(){
        programTreeNode.setTitle(model.isSet(TITLE) ? getTitle() : "Need Input");
    }

    @Override
    public boolean isDefined() {
        return getProgramNodeComplete();
    }

    @Override
    public void openView(){
        getSelectMessageTypeList();
        setLogMessage("select list length " + selectMessageType.getItemCount() + ", list length " + selectMessageTypeList.size());
        if (selectMessageType.getItemCount() < selectMessageTypeList.size()) {
            setSelectMessageTypeList(); //populate the list if it is not populated
        }
        logoImage.setImage(img);
        if (!apiSendMessage.isSelected()) {
            selectMessageType.setEnabled(false);
            sendMessageString.setEnabled(false);
            messageStringLabel.setEnabled(false);
        }
        selectMessageType.selectItemAtIndex(getMessageSelected());
        setLogMessage("select list length " + selectMessageType.getItemCount() + ", list length " + selectMessageTypeList.size());

    }

    @Override
    public void closeView() {

    }

    @Override
    public void generateScript(ScriptWriter writer) {
        String script = getScriptCode();
        setLogMessage(script);
        writer.appendLine(script);
    }


}
