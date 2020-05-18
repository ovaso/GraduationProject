package top.irises.pbox3;

import android.graphics.PathEffect;
import android.text.method.SingleLineTransformationMethod;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import top.irises.pbox3.utils.DateConverter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() throws ParseException {
        String ns = "-6";
        System.out.println(Integer.parseInt(ns));
    }
}