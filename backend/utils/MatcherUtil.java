package com.hitrsutpay.sa.backend.utils;

import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.*;

public class MatcherUtil {

	public static Matcher<String> isValidTimeFormat() {
        return matchesRegex("\\d{4}-\\d{2}-\\d{2} \\d{2}.\\d{2}.\\d{2}");
    }
}
