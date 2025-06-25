package com.shoppinginc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {

    @Test
    public void mainRunsWithoutException() {
      assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
