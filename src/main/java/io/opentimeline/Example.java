package io.opentimeline;

import io.opentimeline.opentimelineio.Clip;
import io.opentimeline.opentimelineio.SerializableObject;
import io.opentimeline.opentimelineio.Timeline;
import io.opentimeline.opentimelineio.Track;

import java.util.stream.Collectors;

public class Example {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java -jar otio-java-example-1.0-SNAPSHOT.jar <file.otio>");
            System.exit(1);
        }

        String otioPath = args[0];

        OTIOFactory otioFactory = OTIOFactory.getInstance();

        Timeline timeline = (Timeline) SerializableObject.fromJSONFile(otioPath);

        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(String.format("Loaded OTIO file: %s\n", otioPath));
        outputBuilder.append(String.format("Timeline name: %s\n", timeline.getName()));
        outputBuilder.append(String.format("Timeline duration: %s\n", timeline.getDuration().toTimecode()));

        outputBuilder.append("Video Tracks:\n");
        if (timeline.getVideoTracks().size() == 0) {
            outputBuilder.append(" No video tracks\n");
        } else {
            for (Track videoTrack: timeline.getVideoTracks()) {
                outputBuilder.append(String.format(" Track: %s\n", videoTrack.getName()));
                outputBuilder.append(String.format("  Kind: %s\n", videoTrack.getKind()));
                outputBuilder.append(String.format("   Duration: %s\n", videoTrack.getDuration().toTimecode()));
            }
        }

        outputBuilder.append("Audio Tracks:\n");
        if (timeline.getAudioTracks().size() == 0) {
            outputBuilder.append(" No audio tracks\n");
        } else {
            for (Track audioTrack: timeline.getAudioTracks()) {
                outputBuilder.append(String.format(" Track: %s\n", audioTrack.getName()));
                outputBuilder.append(String.format("  Kind: %s\n", audioTrack.getKind()));
                outputBuilder.append(String.format("   Duration: %s\n", audioTrack.getDuration().toTimecode()));
            }
        }

        outputBuilder.append("All Clips:\n");
        for (Clip clip: timeline.eachClip().collect(Collectors.toList())) {
            outputBuilder.append(String.format(" Clip: %s\n", clip.getName()));
            outputBuilder.append(String.format("  Duration: %s\n", clip.getDuration().toTimecode()));
        }

        System.out.println(outputBuilder);

        // In a complete application (eg: a SpringBoot web service),
        // the cleanUp function should be called at regular intervals to trigger GC
        otioFactory.cleanUp();
    }

}
