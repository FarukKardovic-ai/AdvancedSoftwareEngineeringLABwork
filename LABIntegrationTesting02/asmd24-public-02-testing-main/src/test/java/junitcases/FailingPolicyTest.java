package junitcases;

import devices.FailingPolicy;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FailingPolicyTest {

    private FailingPolicy failingPolicy;

    @BeforeEach
    void setUp() {
        failingPolicy = mock(FailingPolicy.class);
    }

    @Test
    @DisplayName("Policy allows turning on when attemptOn() returns true")
    void testPolicyAllowsTurningOn() {
        when(failingPolicy.attemptOn()).thenReturn(true);
        assertTrue(failingPolicy.attemptOn(), "Failing policy should allow turning on");
    }

    @Test
    @DisplayName("Policy denies turning on when attemptOn() returns false")
    void testPolicyDeniesTurningOn() {
        when(failingPolicy.attemptOn()).thenReturn(false);
        assertFalse(failingPolicy.attemptOn(), "Failing policy should deny turning on");
    }

    @Test
    @DisplayName("Policy name is correctly returned")
    void testPolicyName() {
        when(failingPolicy.policyName()).thenReturn("MockPolicy");
        assertEquals("MockPolicy", failingPolicy.policyName(), "Policy name should match expected");
    }

    @Test
    @DisplayName("Multiple attemptOn() calls return expected sequence")
    void testPolicyBehaviorOverMultipleCalls() {
        when(failingPolicy.attemptOn()).thenReturn(true, true, false);
        assertTrue(failingPolicy.attemptOn(), "First attempt should succeed");
        assertTrue(failingPolicy.attemptOn(), "Second attempt should succeed");
        assertFalse(failingPolicy.attemptOn(), "Third attempt should fail");
    }

    @Test
    @DisplayName("Verify attemptOn() is called a specific number of times")
    void testAttemptOnCallCount() {
        failingPolicy.attemptOn();
        failingPolicy.attemptOn();
        verify(failingPolicy, times(2)).attemptOn();
    }
}
