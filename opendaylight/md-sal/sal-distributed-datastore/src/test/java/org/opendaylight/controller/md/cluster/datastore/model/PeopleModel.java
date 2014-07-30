/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.md.cluster.datastore.model;

import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.data.api.InstanceIdentifier;
import org.opendaylight.yangtools.yang.data.api.schema.MapEntryNode;
import org.opendaylight.yangtools.yang.data.api.schema.MapNode;
import org.opendaylight.yangtools.yang.data.api.schema.NormalizedNode;
import org.opendaylight.yangtools.yang.data.impl.schema.ImmutableNodes;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.api.CollectionNodeBuilder;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.impl.ImmutableContainerNodeBuilder;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.impl.ImmutableMapNodeBuilder;

public class PeopleModel {
    public static final QName BASE_QNAME = QName.create("urn:opendaylight:params:xml:ns:yang:controller:md:sal:dom:store:test:people", "2014-03-13",
        "people");

    public static final InstanceIdentifier BASE_PATH = InstanceIdentifier.of(BASE_QNAME);
    public static final QName PEOPLE_QNAME = QName.create(BASE_QNAME, "people");
    public static final QName PERSON_QNAME = QName.create(PEOPLE_QNAME, "person");
    public static final QName PERSON_NAME_QNAME = QName.create(PERSON_QNAME, "name");
    public static final QName PERSON_AGE_QNAME = QName.create(PERSON_QNAME, "age");



    public static NormalizedNode create(){

        // Create a list builder
        CollectionNodeBuilder<MapEntryNode, MapNode> cars =
            ImmutableMapNodeBuilder.create().withNodeIdentifier(
                new InstanceIdentifier.NodeIdentifier(
                    QName.create(PEOPLE_QNAME, "person")));

        // Create an entry for the person jack
        MapEntryNode jack =
            ImmutableNodes.mapEntryBuilder(PEOPLE_QNAME, PERSON_NAME_QNAME, "jack")
                .withChild(ImmutableNodes.leafNode(PERSON_NAME_QNAME, "jack"))
                .withChild(ImmutableNodes.leafNode(PERSON_AGE_QNAME, 100))
                .build();

        // Create an entry for the person jill
        MapEntryNode jill =
            ImmutableNodes.mapEntryBuilder(PEOPLE_QNAME, PERSON_NAME_QNAME, "jill")
                .withChild(ImmutableNodes.leafNode(PERSON_NAME_QNAME, "jill"))
                .withChild(ImmutableNodes.leafNode(PERSON_AGE_QNAME, 200))
                .build();

        cars.withChild(jack);
        cars.withChild(jill);

        return ImmutableContainerNodeBuilder.create()
            .withNodeIdentifier(new InstanceIdentifier.NodeIdentifier(BASE_QNAME))
            .withChild(cars.build())
            .build();

    }

    public static NormalizedNode emptyContainer(){
        return ImmutableContainerNodeBuilder.create()
            .withNodeIdentifier(
                new InstanceIdentifier.NodeIdentifier(BASE_QNAME))
            .build();
    }

}