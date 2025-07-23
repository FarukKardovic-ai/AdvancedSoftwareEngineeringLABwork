package junitcases;
import devices.Device;
import devices.FailingPolicy;
import devices.StandardDevice;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DeviceFailingPolicyIntegrationTest {

    public Device device;
    public FailingPolicy failingPolicy;

    @BeforeEach
    void setUp() {
        failingPolicy = mock(FailingPolicy.class);
        device = new StandardDevice(failingPolicy);
    }

    @Test
    @DisplayName("Device switches on when policy allows it")
    void testDeviceTurnsOnWhenPolicyAllows() {
        when(failingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn(), "Device should be on when policy allows it");
    }

    @Test
    @DisplayName("Device remains off when policy denies it")
    void testDeviceRemainsOffWhenPolicyDenies() {
        when(failingPolicy.attemptOn()).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> device.on());
        assertFalse(device.isOn(), "Device should remain off when policy denies it");
    }

    @Test
    @DisplayName("Device turns on and off until failure")
    void testDeviceTurnsOnAndOffUntilFailure() {
        when(failingPolicy.attemptOn()).thenReturn(true, true, false);

        device.on();
        assertTrue(device.isOn(), "Device should be on after first successful attempt");
        device.off();
        assertFalse(device.isOn(), "Device should be off after switching off");

        device.on();
        assertTrue(device.isOn(), "Device should be on after second successful attempt");
        device.off();
        assertFalse(device.isOn(), "Device should be off after switching off again");

        assertThrows(IllegalStateException.class, () -> device.on(), "Device should fail on third attempt");
    }

    @Test
    @DisplayName("Verify attemptOn() is called correct number of times")
    void testPolicyAttemptOnCallCount() {
        when(failingPolicy.attemptOn()).thenReturn(true, false);

        device.on();
        device.off();
        assertThrows(IllegalStateException.class, () -> device.on());

        verify(failingPolicy, times(2)).attemptOn();
    }
}
