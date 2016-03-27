package com.hirebotics.hbcloudconnector.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.ProgramNodeService;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.ui.component.ProgramTreeNode;

import java.io.InputStream;

/**
 * Created by matthewbush1 on 3/26/16.
 */
public class HBCloudConnectorProgramNodeService implements ProgramNodeService {

    public HBCloudConnectorProgramNodeService(){

    }

    @Override
    public String getId() {

        return "HB Cloud Connector Node";
    }

    @Override
    public String getTitle() {
        return "HB Cloud Connector";
    }

    @Override
    public InputStream getHTML() {
        InputStream is = this.getClass().getResourceAsStream("/com/hirebotics/hbcloudconnector/programnode.html");
        return is;
    }

    @Override
    public boolean isDeprecated() {
        return false;
    }


    @Override
    public boolean isChildrenAllowed(){
        return false;
    }

    @Override
    public ProgramNodeContribution createNode(URCapAPI api, DataModel model, ProgramTreeNode programTreeNode) {
        return new HBCloudConnectorProgramNodeContribution(api, model, programTreeNode);
    }

}
