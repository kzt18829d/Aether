package com.aether.core.types.interfaces;

import com.aether.core.types.enums.RecordingStatus;

public interface Recording {
    boolean startRecording();
    boolean stopRecording();
    RecordingStatus getRecordingStatus();
}
