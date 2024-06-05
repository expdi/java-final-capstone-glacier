package org.glacier.pricingapplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Test class added ONLY to cover main() invocation not covered by application tests.
@SpringBootTest
class PricingApplicationTests {

    @Test
    public void testMain() {
        PricingApplication.main(new String[] {});
    }
}
