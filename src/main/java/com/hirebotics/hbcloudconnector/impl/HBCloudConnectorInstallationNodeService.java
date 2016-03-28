package com.hirebotics.hbcloudconnector.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;

import java.io.InputStream;

/**
 * Created by matthewbush1 on 3/27/16.
 */
public class HBCloudConnectorInstallationNodeService implements InstallationNodeService {

    public HBCloudConnectorInstallationNodeService(){

    }

    @Override
    public InstallationNodeContribution createInstallationNode(URCapAPI api, DataModel model){
        return new HBCloudConnectorInstallationNodeContribution(api, model);
    }

    @Override
    public String getTitle() {
        return "Hirebotics Cloud Connector Installation Variables";
    }

    @Override
    public InputStream getHTML(){
        InputStream is = this.getClass().getResourceAsStream("/com/hirebotics/hbcloudconnector/installationnode.html");
        return is;
    }
}
