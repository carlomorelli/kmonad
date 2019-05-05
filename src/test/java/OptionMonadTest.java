import com.csoft.kmonad.Monad;
import com.csoft.kmonad.OptionMonad;
import org.junit.Test;

import java.util.Base64;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OptionMonadTest {

    private Function<String, Monad<String>> toUpperCase = str -> {
        System.out.println("passing Function toUpperCase...");
        return OptionMonad.of(str.toUpperCase());
    };

    private Function<String, Monad<String>> toBase64 = str -> {
        System.out.println("passing Function toBase64...");
        return OptionMonad.of(Base64.getEncoder().encodeToString(str.getBytes()));
    };


    @Test
    public void testIdentityRule() {

        OptionMonad<String> x = OptionMonad.of("la palingenetica obliterazione");

        OptionMonad<String> resultX = (OptionMonad<String>) x.bind(x::wrap);

        assertEquals("la palingenetica obliterazione", resultX.get());

    }


    @Test
    public void testCompositions() {

        OptionMonad<String> x = OptionMonad.of("la palingenetica obliterazione");

        OptionMonad<String> result = (OptionMonad<String>) x
                .bind(toUpperCase)
                .bind(toBase64);

        assertEquals("TEEgUEFMSU5HRU5FVElDQSBPQkxJVEVSQVpJT05F", result.get());

    }


    @Test
    public void testReverseCompositions() {

        OptionMonad<String> x = OptionMonad.of("la palingenetica obliterazione");

        OptionMonad<String> result = (OptionMonad<String>) x
                .bind(toBase64)
                .bind(toUpperCase);

        assertEquals("BGEGCGFSAW5NZW5LDGLJYSBVYMXPDGVYYXPPB25L", result.get());

    }


    @Test
    public void testEmptyMonadSkippedCompositions() {

        OptionMonad<String> x = OptionMonad.empty();

        OptionMonad<String> result = (OptionMonad<String>) x
                .bind(toUpperCase)
                .bind(toBase64);

        assertFalse(result.isPresent());
    }

}
