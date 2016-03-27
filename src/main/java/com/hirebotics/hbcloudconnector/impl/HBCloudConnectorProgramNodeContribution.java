package com.hirebotics.hbcloudconnector.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.annotation.Label;
import com.ur.urcap.api.ui.component.*;

public class HBCloudConnectorProgramNodeContribution implements ProgramNodeContribution {
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";
    private static final String SCRIPT = "script";

    private final DataModel model;
    private final ProgramTreeNode programTreeNode;
    private final URCapAPI api;

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

    @Input(id = "startprogram")
    private InputRadioButton apiStartProgram;

    @Input(id = "sendmessage")
    private InputRadioButton apiSendMessage;

    @Input(id = "senderrormessage")
    private InputRadioButton apiSendErrorMessage;

    @Label(id = "logmessage")
    private LabelComponent logmessage;

    @Input(id = "sendmessagestring")
    private InputTextField sendMessageString;

    private void setLogMessage(String msg){
        logmessage.setText(msg);
    }



    public HBCloudConnectorProgramNodeContribution(URCapAPI api, DataModel model, ProgramTreeNode programTreeNode){
        this.api = api;
        this.model = model;
        this.programTreeNode = programTreeNode;
       // sendMessageString.setVisible(false);
        setProgramTreeNodeTitle();

    }

    @Input(id = "startprogram")
    public void onInput1(InputEvent event){
        if(event.getEventType() == InputEvent.EventType.ON_CHANGE) {
            setTitle("Send Start Program");
            setProgramTreeNodeTitle();
            setLogMessage("Component was: " + event.getComponent());
            setScriptCode("startProgram()");
        }
    }

    @Input(id = "sendmessage")
    public void onInput2(InputEvent event){
        if(event.getEventType() == InputEvent.EventType.ON_CHANGE) {
            setTitle("Send Message");
            setProgramTreeNodeTitle();
            //sendMessageString.setVisible(true);
            setLogMessage("Component was: sendmessage radio button");
            setScriptCode("sendMessage()");
        }
    }


    @Input(id = "senderrormessage")
    public void onInput3(InputEvent event){
        if(event.getEventType() == InputEvent.EventType.ON_CHANGE) {
            setTitle("Send Error Message");
            setProgramTreeNodeTitle();
            setLogMessage("Component was: senderror message radio button");
            setScriptCode("sendErrorMessage()");
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
        programTreeNode.setTitle(model.isSet(TITLE) ? getTitle() : "Waiting for Input");
    }

    @Override
    public boolean isDefined() {
        return !getTitle().isEmpty();
    }

    @Override
    public void openView(){

    }

    @Override
    public void closeView() {

    }

    @Override
    public void generateScript(ScriptWriter writer) {
        String script = getScriptCode();
        setLogMessage(script);
        writer.appendLine(script);
//        writer.appendLine("popup(hello_world_message, \"Info\", False, False, blocking=True)");
//        writer.writeChildren();
    }


}
