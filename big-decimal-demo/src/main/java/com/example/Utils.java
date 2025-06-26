package com.example;

import java.math.MathContext;
import java.math.RoundingMode;

public class Utils {

    // reusable rounding and precision (significant decimal digits)
    public static MathContext getContext() {
        return new MathContext(2, RoundingMode.HALF_UP);
    }
}
