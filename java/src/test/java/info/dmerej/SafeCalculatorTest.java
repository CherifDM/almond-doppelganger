package info.dmerej;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SafeCalculatorTest {

  SafeCalculator unauthorizedSafeCalculator = new SafeCalculator(new Authorizer() {
    @Override
    public boolean authorize() {
      return false;
    }
  });

  SafeCalculator authorizedSafeCalculator = new SafeCalculator(new Authorizer() {
    @Override
    public boolean authorize() {
      return true;
    }
  });
  @Test
  void should_not_throw_when_authorized() {
    assertDoesNotThrow(() -> {
      authorizedSafeCalculator.add(1,2);
    });
  }

  @Test
  void should_throw_when_not_authorized() {
    assertThrows(UnauthorizedAccessException.class, () -> {
      unauthorizedSafeCalculator.add(1,2);
    });
  }
}
