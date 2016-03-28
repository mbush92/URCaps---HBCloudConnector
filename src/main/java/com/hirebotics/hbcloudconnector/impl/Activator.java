package com.hirebotics.hbcloudconnector.impl;

import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.contribution.ProgramNodeService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Activator says Hello World!");
		HBCloudConnectorProgramNodeService hbCloudConnectorProgramNodeService = new HBCloudConnectorProgramNodeService();
		HBCloudConnectorInstallationNodeService hbCloudConnectorInstallationNodeService = new HBCloudConnectorInstallationNodeService();

		bundleContext.registerService(ProgramNodeService.class, hbCloudConnectorProgramNodeService, null);
		bundleContext.registerService(InstallationNodeService.class, hbCloudConnectorInstallationNodeService, null);


	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Activator says Goodbye World!");
	}
}

