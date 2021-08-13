/*
 * Copyright 2012 Sebastian Annies, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.*;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Changes the timescale of a track by wrapping the track.
 */
public class DivideTimeScaleTrack implements Track {
    Track source;
    private int timeScaleDivisor;

    public DivideTimeScaleTrack(Track source, int timeScaleDivisor) {
        this.source = source;
        this.timeScaleDivisor = timeScaleDivisor;
    }

    @Override
    public SampleDescriptionBox getSampleDescriptionBox() {
        return source.getSampleDescriptionBox();
    }

    @Override
    public List<TimeToSampleBox.Entry> getDecodingTimeEntries() {
        return adjustTts();
    }

    @Override
    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return adjustCtts();
    }

    @Override
    public long[] getSyncSamples() {
        return source.getSyncSamples();
    }

    @Override
    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return source.getSampleDependencies();
    }

    @Override
    public TrackMetaData getTrackMetaData() {
        TrackMetaData trackMetaData = (TrackMetaData) source.getTrackMetaData().clone();
        trackMetaData.setTimescale(source.getTrackMetaData().getTimescale() / this.timeScaleDivisor);
        return trackMetaData;
    }

    @Override
    public String getHandler() {
        return source.getHandler();
    }

    @Override
    public boolean isEnabled() {
        return source.isEnabled();
    }

    @Override
    public boolean isInMovie() {
        return source.isInMovie();
    }

    @Override
    public boolean isInPreview() {
        return source.isInPreview();
    }

    @Override
    public boolean isInPoster() {
        return source.isInPoster();
    }

    @Override
    public List<ByteBuffer> getSamples() {
        return source.getSamples();
    }


    List<CompositionTimeToSample.Entry> adjustCtts() {
        List<CompositionTimeToSample.Entry> origCtts = this.source.getCompositionTimeEntries();
        if (origCtts != null) {
            List<CompositionTimeToSample.Entry> entries2 = new ArrayList<CompositionTimeToSample.Entry>(origCtts.size());
            for (CompositionTimeToSample.Entry entry : origCtts) {
                entries2.add(new CompositionTimeToSample.Entry(entry.getCount(), entry.getOffset() / timeScaleDivisor));
            }
            return entries2;
        } else {
            return null;
        }
    }

    List<TimeToSampleBox.Entry> adjustTts() {
        List<TimeToSampleBox.Entry> origTts = source.getDecodingTimeEntries();
        LinkedList<TimeToSampleBox.Entry> entries2 = new LinkedList<TimeToSampleBox.Entry>();
        for (TimeToSampleBox.Entry e : origTts) {
            entries2.add(new TimeToSampleBox.Entry(e.getCount(), e.getDelta() / timeScaleDivisor));
        }
        return entries2;
    }

    @Override
    public Box getMediaHeaderBox() {
        return source.getMediaHeaderBox();
    }

    @Override
    public SubSampleInformationBox getSubsampleInformationBox() {
        return source.getSubsampleInformationBox();
    }

    @Override
    public String toString() {
        return "MultiplyTimeScaleTrack{" +
                "source=" + source +
                '}';
    }
}
