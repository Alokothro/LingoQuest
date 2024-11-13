package lingo_quest;

// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import javazoom.jl.decoder.JavaLayerException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.Voice;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.PollyException;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import java.io.IOException;
import java.io.InputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * Utility class that leverages Amazon Polly to synthesize speech from text and play it aloud.
 */
public class Narriator {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Narriator() {
    };
    
    /**
     * Plays the given text as synthesized speech using Amazon Polly and the default region.
     *
     * @param text The text to be synthesized and played.
     */
    public static void playSound(String text) {
        PollyClient polly = PollyClient.builder().region(Region.EU_WEST_3).build();
        talkPolly(polly, text);
        polly.close();
    }
    /**
     * Internal method that handles the interaction with Amazon Polly to fetch and play the speech.
     *
     * @param polly The PollyClient instance configured for requests.
     * @param text The text to synthesize into speech.
     */
    private static void talkPolly(PollyClient polly, String text) {
        try {
            DescribeVoicesRequest describeVoiceRequest = DescribeVoicesRequest.builder()
                    .engine("standard")
                    .build();

            DescribeVoicesResponse describeVoicesResult = polly.describeVoices(describeVoiceRequest);
            Voice voice = describeVoicesResult.voices().stream()
                    .filter(v -> v.name().equals("Miguel"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Voice not found"));

            InputStream stream = synthesize(polly, text, voice, OutputFormat.MP3);
            AdvancedPlayer player = new AdvancedPlayer(stream,
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

            player.setPlayBackListener(new PlaybackListener() {
            });

            player.play();

        } catch (PollyException | JavaLayerException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    /**
     * Synthesizes speech from the given text using a specified voice and output format, returning the audio stream.
     *
     * @param polly The PollyClient used to synthesize speech.
     * @param text The text to be synthesized.
     * @param voice The Voice to be used for synthesis.
     * @param format The output format of the synthesized speech.
     * @return InputStream of the synthesized audio data.
     * @throws IOException If an input/output exception occurs during speech synthesis.
     */

    public static InputStream synthesize(PollyClient polly, String text, Voice voice, OutputFormat format)
            throws IOException {
        SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder()
                .text(text)
                .voiceId(voice.id())
                .outputFormat(format)
                .build();

        ResponseInputStream<SynthesizeSpeechResponse> synthRes = polly.synthesizeSpeech(synthReq);
        return synthRes;
    }
}