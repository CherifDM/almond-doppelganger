package info.dmerej;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscountApplierTest {

  List<User> calledUsers;
  List<User> allUsers;
  Notifier mockNotifier = Mockito.mock(Notifier.class);
  DiscountApplier discountApplier = new DiscountApplier(new Notifier() {
    @Override
    public void notify(User user, String message) {
      calledUsers.add(user);
    }
  });

  @BeforeEach
  void set_users() {
    calledUsers = new ArrayList<>();
    allUsers = Arrays.asList(
            new User("Adam", "adam@gmail.com"),
            new User("Clement", "clement@gmail.com"),
            new User("Auno", "auno@gmail.com"),
            new User("Talla", "talla@gmail.com"),
            new User("Cherif", "CHERIF@gmail.com"));
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v1() {
    discountApplier.applyV1(30, allUsers);
    assertIterableEquals(allUsers, calledUsers);
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v2() {
    discountApplier.applyV2(30, allUsers);
    assertIterableEquals(allUsers, calledUsers);
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v1_mockito() {
    DiscountApplier applier = new DiscountApplier(mockNotifier);
    applier.applyV1(30, allUsers);
    verify(mockNotifier, times(5)).notify(any(User.class), eq("You've got a new discount of 30%"));
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v2_mockito() {
    discountApplier.applyV2(30, allUsers);
    assertIterableEquals(allUsers, calledUsers);

    DiscountApplier applier = new DiscountApplier(mockNotifier);
    applier.applyV1(30, allUsers);
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    verify(mockNotifier, times(5)).notify(userCaptor.capture(), messageCaptor.capture());

    assertTrue(userCaptor.getAllValues().get(0).equals(allUsers.get(0)));
    assertEquals(messageCaptor.getAllValues().get(0), "You've got a new discount of 30%");

    assertTrue(userCaptor.getAllValues().get(1).equals(allUsers.get(1)));
    assertEquals(messageCaptor.getAllValues().get(1), "You've got a new discount of 30%");

    assertTrue(userCaptor.getAllValues().get(2).equals(allUsers.get(2)));
    assertEquals(messageCaptor.getAllValues().get(2), "You've got a new discount of 30%");

    assertTrue(userCaptor.getAllValues().get(3).equals(allUsers.get(3)));
    assertEquals(messageCaptor.getAllValues().get(3), "You've got a new discount of 30%");

    assertTrue(userCaptor.getAllValues().get(4).equals(allUsers.get(4)));
    assertEquals(messageCaptor.getAllValues().get(4), "You've got a new discount of 30%");
  }
}
