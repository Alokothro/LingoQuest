package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.*;

import lingoquestpackage.models.*;


import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VoiceListTest {

    @Mock
    private PollyClient mockPollyClient;

    @Before
    public void setUp() {
        // Setup commonly used mocks and other initialization
    }

    @Test
    public void testDisplayVoices_WithVoices() {
        // Mock the Polly response
        DescribeVoicesResponse response = DescribeVoicesResponse.builder()
            .voices(Voice.builder().name("Alice").gender("Female").languageName("English").build())
            .build();
        when(mockPollyClient.describeVoices(any(DescribeVoicesRequest.class))).thenReturn(response);

        VoiceList.displayVoices(mockPollyClient);

        // Verify the output, usually with an output capture or further analysis
    }

    @Test
    public void testDisplayVoices_NoVoices() {
        // Mock the Polly response with no voices
        DescribeVoicesResponse response = DescribeVoicesResponse.builder().build();
        when(mockPollyClient.describeVoices(any(DescribeVoicesRequest.class))).thenReturn(response);

        VoiceList.displayVoices(mockPollyClient);

        // Assert behavior or check outputs
    }

    @Test(expected = PollyException.class)
    public void testDisplayVoices_ThrowsException() {
        // Mock an exception being thrown
        when(mockPollyClient.describeVoices(any(DescribeVoicesRequest.class)))
            .thenThrow(PollyException.class);

        VoiceList.displayVoices(mockPollyClient);
    }

    @Test
    public void testShowVoices_ValidRegion() {
        // Assume this will use the mocked client
        VoiceList.showVoices(Region.EU_WEST_1);
        // Verify interactions
        verify(mockPollyClient).close();
    }
}