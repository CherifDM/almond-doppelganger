package info.dmerej;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscountApplierTest {

  List<User> calledUsers;
  List<User> allUsers;
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

}
