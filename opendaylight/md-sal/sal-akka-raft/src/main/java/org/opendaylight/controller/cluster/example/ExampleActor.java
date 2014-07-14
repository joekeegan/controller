/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.cluster.example;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;
import org.opendaylight.controller.cluster.example.messages.KeyValue;
import org.opendaylight.controller.cluster.example.messages.KeyValueSaved;
import org.opendaylight.controller.cluster.raft.RaftActor;

import java.util.HashMap;
import java.util.Map;

/**
 * A sample actor showing how the RaftActor is to be extended
 */
public class ExampleActor extends RaftActor {

    private final Map<String, String> state = new HashMap();

    private long persistIdentifier = 1;


    public ExampleActor(String id, Map<String, String> peerAddresses) {
        super(id, peerAddresses);
    }

    public static Props props(final String id, final Map<String, String> peerAddresses){
        return Props.create(new Creator<ExampleActor>(){

            @Override public ExampleActor create() throws Exception {
                return new ExampleActor(id, peerAddresses);
            }
        });
    }

    @Override public void onReceiveCommand(Object message){
        if(message instanceof KeyValue){

            if(isLeader()) {
                String persistId = Long.toString(persistIdentifier++);
                persistData(getSender(), persistId, message);
            } else {
                getLeader().forward(message, getContext());
            }
        }
        super.onReceiveCommand(message);
    }

    @Override protected void applyState(ActorRef clientActor, String identifier,
        Object data) {
        if(data instanceof KeyValue){
            KeyValue kv = (KeyValue) data;
            state.put(kv.getKey(), kv.getValue());
            if(clientActor != null) {
                clientActor.tell(new KeyValueSaved(), getSelf());
            }
        }
    }

    @Override public void onReceiveRecover(Object message) {
        super.onReceiveRecover(message);
    }

    @Override public String persistenceId() {
        return getId();
    }
}