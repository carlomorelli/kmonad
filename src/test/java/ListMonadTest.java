import com.csoft.kmonad.ListMonad;
import com.csoft.kmonad.Monad;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListMonadTest {

    private Function<Integer, Monad<Integer>> doubleIt = i -> {
        System.out.println("passing Function doubleIt...");
        return ListMonad.of(2 * i);
    };

    private Function<Integer, Monad<Integer>> increment = i -> {
        System.out.println("passing Function increment...");
        return ListMonad.of(i + 1);
    };


    private Function<String, Monad<String>> appendSmith = str -> {
        System.out.println("passing Function appendSmith...");
        return ListMonad.of(str.concat(" smith"));
    };

    private Function<String, Monad<String>> appendWilliamson = str -> {
        System.out.println("passing Function appendWilliamson...");
        return ListMonad.of(str.concat(" williamson"));
    };


    private static void assertCollection(List<?> expected, List<?> actual) {
        assertEquals(expected.size(), actual.size());
        actual.forEach( it ->
                assertTrue(expected.contains(it))
        );
    }


    @Test
    public void testIdentityRule() {

        var x = ListMonad.of(1, 7, 12);

        var resultX = (ListMonad<Integer>) x.bind(x::wrap);

        assertCollection(Arrays.asList(1, 7, 12), resultX.unwrap());

    }


    @Test
    public void testCompositions() {

        var x = ListMonad.of(1, 2, 3);
        var y = ListMonad.of("john", "hannah");

        var resultX = (ListMonad<Integer>) x
                .bind(doubleIt)
                .bind(increment);
        var resultY = (ListMonad<String>) y
                .bind(appendSmith)
                .bind(appendWilliamson);

        assertCollection(Arrays.asList(3, 5, 7), resultX.unwrap());
        assertCollection(Arrays.asList("john smith williamson", "hannah smith williamson"), resultY.unwrap());

    }


    @Test
    public void testReverseCompositions() {

        var x = ListMonad.of(1, 2, 3);
        var y = ListMonad.of("john", "hannah");

        var resultX = (ListMonad<Integer>) x
                .bind(increment)
                .bind(doubleIt);
        var resultY = (ListMonad<String>) y
                .bind(appendWilliamson)
                .bind(appendSmith);

        assertCollection(Arrays.asList(4, 6, 8), resultX.unwrap());
        assertCollection(Arrays.asList("john williamson smith", "hannah williamson smith"), resultY.unwrap());

    }


    @Test
    public void testEmptyMonadSkippedCompositions() {

        ListMonad<Integer> x = ListMonad.empty();

        var resultX = (ListMonad<Integer>) x
                .bind(doubleIt)
                .bind(increment);

        assertTrue(resultX.isEmpty());
    }

}
