/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    /*
     * Estrategia de test para geTimespan()
     * tweets.size() == 0 , 1 , >1
     * Tweets timestamp: todos distintos, algunos iguales, todos iguales
     * 
     * Estrategia de test getMentionedUser()
     * tweets.size = 0 , >0
     * Mencion: 0, 1 , >1
     * Case-insensitive: @ApolO es igual @apOlo, por ejemplo ["@Apolo","@apOlo"] -> ["@Aplolo"]    
     * Mentioned User esta: principio, medio o final del texto.
     * Un email no es un usuario valido: jertrudis@gmail.com, @gmail no es un usuario valido
     *   
    */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T14:00:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T15:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "tom", "rivest talk in 30 @Mike minutes #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "Mike", "rivest talk in 30 @dora minutes #hype", d4);
    private static final Tweet tweet5 = new Tweet(5, "Dora", "rivest talk in 30 @alyssa minutes #hype", d5);
    private static final Tweet tweet6 = new Tweet(6, "victor", "rivest talk in 30 @bbitdiddle minutes #hype", d6);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // getTimespan()
    
    // covers
    // tweets.size() = 0
    @Test(expected=AssertionError.class)
    public void testGetTimespanEmpty() {
        Timespan timespan = Extract.getTimespan(new ArrayList<Tweet>());
    }
    
    // covers
    // tweets.size() = 1
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
        
    }
    
    // covers
    // tweets.size > 1
    // Tweets timestamp: todos distintos
    @Test
    public void testGetTimespanMasUnTweetTodosDistintos() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2,tweet3));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
        
    }
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // covers
    // tweets.size > 1
    // Tweets timestamp: todos iguales
    @Test
    public void testGetTimespanMasUnTweetTodosIguales() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet1,tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
        
    }
    
    // covers
    // tweets.size > 1
    // Tweets timestamp: algunos iguales
    @Test
    public void testGetTimespanMasUnTweetAlgunosIguales() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2,tweet2));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
        
    }
    
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
