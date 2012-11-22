/*
 * Copyright (c) 2008-2012, Hazel Bilisim Ltd. All Rights Reserved.
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

package com.hazelcast.map;

import com.hazelcast.spi.impl.Response;

import java.util.concurrent.BlockingQueue;

public class AsyncBackupResponse extends Response {

    @Override
    public void run() {
        try {
            long callId = getCallId();
            MapService mapService = (MapService) getService();
            final BlockingQueue backupCallQueue = mapService.getBackupCallQueue(callId);
            if (backupCallQueue != null) {
                backupCallQueue.offer(Boolean.TRUE);
            } else {
                System.err.println("NO BACKUP QUEUE !!! " + callId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}