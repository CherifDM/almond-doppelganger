package info.dmerej;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SafeCalculatorTest {

  Authorizer mockAuthorizer = Mockito.mock(Authorizer.class);


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

  @Test
  void should_not_throw_when_authorized_v2() {
    when(mockAuthorizer.authorize()).thenReturn(true);
    SafeCalculator safeCalculator = new SafeCalculator(mockAuthorizer);

    assertDoesNotThrow(() -> {
      safeCalculator.add(1,2);
    });
  }

  @Test
  void should_throw_when_not_authorized_v2() {
    when(mockAuthorizer.authorize()).thenReturn(false);
    SafeCalculator safeCalculator = new SafeCalculator(mockAuthorizer);

    assertThrows(UnauthorizedAccessException.class, () -> {
      safeCalculator.add(1,2);
    });
  }
}
