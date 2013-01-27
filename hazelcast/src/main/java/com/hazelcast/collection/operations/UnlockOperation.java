/*
 * Copyright (c) 2008-2012, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.collection.operations;

import com.hazelcast.collection.CollectionContainer;
import com.hazelcast.collection.CollectionProxyType;
import com.hazelcast.collection.WaitKey;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.spi.Notifier;
import com.hazelcast.spi.Operation;
import com.hazelcast.spi.WaitNotifyKey;

/**
 * @ali 1/16/13
 */
public class UnlockOperation extends CollectionBackupAwareOperation implements Notifier {

    public UnlockOperation() {
    }

    public UnlockOperation(String name, CollectionProxyType proxyType, Data dataKey, int threadId) {
        super(name, proxyType, dataKey, threadId);
    }

    public void run() throws Exception {
        CollectionContainer container = getOrCreateContainer();
        response = container.unlock(dataKey, getCaller(), threadId);
    }

    public Operation getBackupOperation() {
        return new UnlockBackupOperation(name, proxyType, dataKey, threadId, getCaller());
    }

    public boolean shouldBackup() {
        return Boolean.TRUE.equals(response);
    }

    public boolean shouldNotify() {
        return Boolean.TRUE.equals(response);
    }

    public WaitNotifyKey getNotifiedKey() {
        return new WaitKey(name, dataKey, "lock");
    }

    public boolean shouldWait() {
        return false;
    }
}