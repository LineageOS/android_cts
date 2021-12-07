/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.os.cts.process.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.SystemClock;
import android.os.cts.process.common.Consts;
import android.os.cts.process.common.Message;
import android.util.Log;

import com.android.compatibility.common.util.BroadcastMessenger;

public class BaseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Consts.TAG, "onReceive: " + intent);
        switch (intent.getAction()) {
            case Consts.ACTION_SEND_BACK_START_TIME:
                sendBackStartTime(context);
                break;
        }
    }

    private static void sendBackStartTime(Context context) {
        Message m = new Message();

        m.processName = Process.myProcessName();

        m.nowElapsedRealtime = SystemClock.elapsedRealtime();
        m.nowUptimeMillis = SystemClock.uptimeMillis();

        m.startElapsedRealtime = Process.getStartElapsedRealtime();
        m.startUptimeMillis = Process.getStartUptimeMillis();
        m.startRequestedElapsedRealtime = Process.getStartRequestedElapsedRealtime();
        m.startRequestedUptimeMillis = Process.getStartRequestedUptimeMillis();

        BroadcastMessenger.send(context, Consts.TAG, m);
    }
}