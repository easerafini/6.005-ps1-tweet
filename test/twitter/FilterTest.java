/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here. See the
     * ic03-testing exercise for examples of what a testing strategy comment looks
     * like. Make sure you have partitions.
     */

    /*
     * Test strategy writtenBy; tweets.size = 1, >1; username: 0, singleton,
     * multiple; result same order that tweets order
     * 
     * Test strategy of inTimespan; tweets.size = 1, >1; Timespan: 0, singleton,
     * multiple; result same order that tweets order
     * 
     * Test strategy of containing; tweets.size = 1, >1; Containing: 0, singleton,
     * multiple; result same order that tweets order
     * 
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable, to talk. about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "is it reasonable to talk about rivest so much?", d3);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     * tweets.size = 1 username = 0
     */
    @Test
    public void testWrittenBySingleTweetsEmptyResult() {
        final List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "robert");
        assertTrue("expected empty list", writtenBy.isEmpty());
    }

    /*
     * tweets.size = 1 username = 1
     */
    @Test
    public void testWrittenBySingleTweetsSingleResult() {
        final List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    /*
     * tweets.size > 1 username = 0
     */
    @Test
    public void testWrittenByMultipleTweetsEmptyResult() {
        final List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "robert");
        assertTrue("expected empty list", writtenBy.isEmpty());
    }

    /*
     * tweets.size >1 username = 1
     */
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        final List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    /*
     * tweets.size >1 username > 1
     */
    @Test
    public void testWrittenByMultipleTweetsMultypleResult() {
        final List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");

        assertEquals("expected two tweets", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
    }

    /*
     * Result tweets are in same order that input tweets order
     */
    @Test
    public void testWrittenBySameOrderResult() {
        final List<Tweet> listOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> writtenBy = Filter.writtenBy(listOrigin, "alyssa");

        assertEquals("expected two tweets", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));

        for (int index = 0; index < writtenBy.size() - 1; index++) {
            final Tweet actual = writtenBy.get(index);
            final Tweet next = writtenBy.get(index + 1);
            assertTrue("expected true", listOrigin.indexOf(actual) < listOrigin.indexOf(next)
                    && writtenBy.indexOf(actual) < writtenBy.indexOf(next));
        }
    }

    /*
     * Test no modify list of tweets
     */
    @Test
    public void testWrittenByNoModifyListTweets() {
        final List<Tweet> listOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> copiaListOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> writtenBy = Filter.writtenBy(listOrigin, "alyssa");
        assertEquals("expected two tweets", 2, writtenBy.size());

        assertEquals("expected same lists", listOrigin, copiaListOrigin);
    }

    /*
     * inTimespan
     * 
     */
    /*
     * tweets.size = 1; Timespan = 0
     */
    @Test
    public void testInTimespanSingleTweetsEmptyResult() {
        final Instant testStart = Instant.parse("2016-02-17T06:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T07:00:00Z");
        final List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1), new Timespan(testStart, testEnd));
        assertTrue("expected empty list", inTimespan.isEmpty());
    }

    /*
     * tweets.size = 1; Timespan = 1
     */
    @Test
    public void testInTimespanSingleTweetsSingleResult() {
        final Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T10:00:00Z");
        final List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1), new Timespan(testStart, testEnd));
        assertEquals("expected singleton list", 1, inTimespan.size());
        assertTrue("expected list to contain tweet", inTimespan.contains(tweet1));
    }

    /*
     * tweets.size > 1; Timespan = 0
     */
    @Test
    public void testInTimespanMultipleTweetsEmptyResult() {
        final Instant testStart = Instant.parse("2016-02-17T06:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T07:00:00Z");
        final List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2),
                new Timespan(testStart, testEnd));
        assertTrue("expected empty list", inTimespan.isEmpty());
    }

    /*
     * tweets.size >1; Timespan = 1
     */
    @Test
    public void testInTimespanMultipleTweetsSingleResult() {
        final Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T10:00:00Z");
        final List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2),
                new Timespan(testStart, testEnd));

        assertEquals("expected singleton list", 1, inTimespan.size());
        assertTrue("expected list to contain tweet", inTimespan.contains(tweet1));
    }

    /*
     * tweets.size >1; Timespan > 1; same order
     */
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        final Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T11:00:00Z");
        final List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3),
                new Timespan(testStart, testEnd));
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    /*
     * Result tweets are in same order that input tweets order
     */
    @Test
    public void testInTimespanSameOrderResult() {
        final Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T11:00:00Z");
        final List<Tweet> listOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> inTimespan = Filter.inTimespan(listOrigin, new Timespan(testStart, testEnd));

        assertEquals("expected two tweets", 2, inTimespan.size());
        assertTrue("expected list to contain tweet", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));

        for (int index = 0; index < inTimespan.size() - 1; index++) {
            final Tweet actual = inTimespan.get(index);
            final Tweet next = inTimespan.get(index + 1);

            assertTrue("expected true", listOrigin.indexOf(actual) < listOrigin.indexOf(next)
                    && inTimespan.indexOf(actual) < inTimespan.indexOf(next));
        }
    }

    /*
     * Test no modify list of tweets
     */
    @Test
    public void testInTimespanNoModifyListTweets() {
        final Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        final Instant testEnd = Instant.parse("2016-02-17T11:00:00Z");
        final List<Tweet> listOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> copiaListOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> inTimespan = Filter.inTimespan(listOrigin, new Timespan(testStart, testEnd));

        assertEquals("expected two tweets", 2, inTimespan.size());
        assertEquals("expected same lists", listOrigin, copiaListOrigin);
    }

    /*
     * containing
     */
    /*
     * tweets.size = 1; Containing = 0
     */
    @Test
    public void testContainingSingleTweetsEmptyResult() {
        final List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("speak"));
        assertTrue("expected empty list", containing.isEmpty());
    }

    /*
     * tweets.size = 1; Containing = 1
     */
    @Test
    public void testContainingSingleTweetsSingleResult() {
        final List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("talk"));
        assertEquals("expected singleton list", 1, containing.size());
        assertTrue("expected list to contain tweet", containing.contains(tweet1));
    }

    /*
     * tweets.size > 1; Containing = 0
     */
    @Test
    public void testContainingMultipleTweetsEmptyResult() {
        final List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("speak"));
        assertTrue("expected empty list", containing.isEmpty());
    }

    /*
     * tweets.size >1; Containing = 1
     */
    @Test
    public void testContainingMultipleTweetsSingleResult() {
        final List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("about"));

        assertEquals("expected singleton list", 1, containing.size());
        assertTrue("expected list to contain tweet", containing.contains(tweet1));
    }

    /*
     * tweets.size >1; Containing > 1; same order
     */
    @Test
    public void testContainingMultipleTweetsMultipleResults() {
        final List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Result tweets are in same order that input tweets order
     */
    @Test
    public void testContainingSameOrderResult() {
        final List<Tweet> listOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> containing = Filter.containing(listOrigin, Arrays.asList("talk"));

        assertEquals("expected two tweets", 3, containing.size());
        assertTrue("expected list to contain tweet", containing.containsAll(Arrays.asList(tweet1, tweet2)));

        for (int index = 0; index < containing.size() - 1; index++) {
            final Tweet actual = containing.get(index);
            final Tweet next = containing.get(index + 1);

            assertTrue("expected true", listOrigin.indexOf(actual) < listOrigin.indexOf(next)
                    && containing.indexOf(actual) < containing.indexOf(next));
        }
    }

    /*
     * Test no modify list of tweets
     */
    @Test
    public void testContainingNoModifyListTweets() {
        final List<Tweet> listOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> copiaListOrigin = Arrays.asList(tweet1, tweet2, tweet3);
        final List<Tweet> containing = Filter.containing(listOrigin, Arrays.asList("talk"));

        assertEquals("expected two tweets", 3, containing.size());
        assertEquals("expected same lists", listOrigin, copiaListOrigin);
    }

    /*
     * tweets.size >1; Containing > 1; same order; Case-insensitive
     */
    @Test
    public void testContainingMultipleTweetsMultipleResultsCaseInsensitive() {
        final List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("TALK"));
    
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting (temporarily)
     * your version of Filter with the staff's version. DO NOT strengthen the spec
     * of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own that
     * you have put in Filter, because that means you're testing a stronger spec
     * than Filter says. If you need such helper methods, define them in a different
     * class. If you only need them in this test class, then keep them in this test
     * class.
     */

}
