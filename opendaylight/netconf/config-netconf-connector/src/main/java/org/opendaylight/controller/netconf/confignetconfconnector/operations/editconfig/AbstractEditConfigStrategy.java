/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.netconf.confignetconfconnector.operations.editconfig;

import java.util.Map;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;
import org.opendaylight.controller.config.util.ConfigTransactionClient;
import org.opendaylight.controller.netconf.confignetconfconnector.exception.NetconfConfigHandlingException;
import org.opendaylight.controller.netconf.confignetconfconnector.mapping.attributes.fromxml.AttributeConfigElement;
import org.opendaylight.controller.netconf.confignetconfconnector.mapping.config.ServiceRegistryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEditConfigStrategy implements EditConfigStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEditConfigStrategy.class);

    @Override
    public void executeConfiguration(String module, String instance, Map<String, AttributeConfigElement> configuration,
                                     ConfigTransactionClient ta, ServiceRegistryWrapper services) throws NetconfConfigHandlingException {

        try {
            ObjectName on = ta.lookupConfigBean(module, instance);
            LOG.debug("ServiceInstance for {} {} located successfully under {}", module, instance, on);
            executeStrategy(configuration, ta, on, services);
        } catch (InstanceNotFoundException e) {
            handleMissingInstance(configuration, ta, module, instance, services);
        }

    }

    // TODO split missing instances handling strategies from edit config strategies in this hierarchy = REFACTOR
    // edit configs should not handle missing

    abstract void handleMissingInstance(Map<String, AttributeConfigElement> configuration, ConfigTransactionClient ta,
                                        String module, String instance, ServiceRegistryWrapper services) throws NetconfConfigHandlingException;

    abstract void executeStrategy(Map<String, AttributeConfigElement> configuration, ConfigTransactionClient ta,
                                  ObjectName objectName, ServiceRegistryWrapper services) throws NetconfConfigHandlingException;

}
