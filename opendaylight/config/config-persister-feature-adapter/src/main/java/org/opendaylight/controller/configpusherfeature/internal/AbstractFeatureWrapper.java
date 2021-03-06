/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.configpusherfeature.internal;

import com.google.common.base.Preconditions;
import java.util.LinkedHashSet;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.karaf.features.BundleInfo;
import org.apache.karaf.features.Conditional;
import org.apache.karaf.features.ConfigFileInfo;
import org.apache.karaf.features.ConfigInfo;
import org.apache.karaf.features.Dependency;
import org.apache.karaf.features.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Wrap a Feature for the purposes of extracting the FeatureConfigSnapshotHolders from
 * its underlying ConfigFileInfo's
 *
 * Delegates the the contained feature and provides additional methods.
 */
public class AbstractFeatureWrapper implements Feature {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFeatureWrapper.class);
    protected Feature feature = null;

    protected AbstractFeatureWrapper() {
        // prevent instantiation without Feature
    }

    /*
     * @param f Feature to wrap
     */
    public AbstractFeatureWrapper(final Feature f) {
        Preconditions.checkNotNull(f,"FeatureWrapper requires non-null Feature in constructor");
        this.feature = f;
    }

    /*
     * Get FeatureConfigSnapshotHolders appropriate to feed to the config subsystem
     * from the underlying Feature Config files
     */
    public LinkedHashSet<FeatureConfigSnapshotHolder> getFeatureConfigSnapshotHolders() throws Exception {
        final LinkedHashSet <FeatureConfigSnapshotHolder> snapShotHolders = new LinkedHashSet<FeatureConfigSnapshotHolder>();
        for(final ConfigFileInfo c: getConfigurationFiles()) {
            try {
                snapShotHolders.add(new FeatureConfigSnapshotHolder(c,this));
            } catch (final JAXBException e) {
                LOG.debug("{} is not a config subsystem config file",c.getFinalname());
            }
        }
        return snapShotHolders;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((feature == null) ? 0 : feature.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractFeatureWrapper other = (AbstractFeatureWrapper) obj;
        if (feature == null) {
            if (other.feature != null) {
                return false;
            }
        } else if (!feature.equals(other.feature)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return feature.getName();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getId()
     */
    @Override
    public String getId() {
        return feature.getId();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getName()
     */
    @Override
    public String getName() {
        return feature.getName();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getDescription()
     */
    @Override
    public String getDescription() {
        return feature.getDescription();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getDetails()
     */
    @Override
    public String getDetails() {
        return feature.getDetails();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getVersion()
     */
    @Override
    public String getVersion() {
        return feature.getVersion();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#hasVersion()
     */
    @Override
    public boolean hasVersion() {
        return feature.hasVersion();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getResolver()
     */
    @Override
    public String getResolver() {
        return feature.getResolver();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getInstall()
     */
    @Override
    public String getInstall() {
        return feature.getInstall();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getDependencies()
     */
    @Override
    public List<Dependency> getDependencies() {
        return feature.getDependencies();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getBundles()
     */
    @Override
    public List<BundleInfo> getBundles() {
        return feature.getBundles();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getConfigurations()
     */
    @Override
    public List<ConfigInfo> getConfigurations() {
        return feature.getConfigurations();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getConfigurationFiles()
     */
    @Override
    public List<ConfigFileInfo> getConfigurationFiles() {
        return feature.getConfigurationFiles();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getConditional()
     */
    @Override
    public List<? extends Conditional> getConditional() {
        return feature.getConditional();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getStartLevel()
     */
    @Override
    public int getStartLevel() {
        return feature.getStartLevel();
    }

    /**
     * @return
     * @see org.apache.karaf.features.Feature#getRegion()
     */
    @Override
    public String getRegion() {
        return feature.getRegion();
    }

}