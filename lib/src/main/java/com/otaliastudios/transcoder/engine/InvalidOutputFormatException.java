/*
 * Copyright (C) 2015 Yuya Tanaka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.otaliastudios.transcoder.engine;

import androidx.annotation.NonNull;

import com.otaliastudios.transcoder.MediaTranscoder;
import com.otaliastudios.transcoder.MediaTranscoderOptions;

/**
 * One of the exceptions possibly thrown by
 * {@link MediaTranscoder#transcode(MediaTranscoderOptions)}, which means it can be
 * passed to {@link MediaTranscoder.Listener#onTranscodeFailed(Throwable)}.
 */
@SuppressWarnings("WeakerAccess")
public class InvalidOutputFormatException extends RuntimeException {
    InvalidOutputFormatException(@NonNull String detailMessage) {
        super(detailMessage);
    }
}
