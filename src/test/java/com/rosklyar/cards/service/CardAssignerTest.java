package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static com.rosklyar.cards.domain.Event.Type.ALBUM_FINISHED;
import static com.rosklyar.cards.domain.Event.Type.SET_FINISHED;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.range;
import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * Created by rostyslavs on 11/21/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CardAssignerTest {
    private final List<User> users = range(0L, 10L).mapToObj(User::new).collect(Collectors.toList());

    private final UserService userService = new InMemoryUserService(users);
    private final CardService cardService = new InMemoryCardService(Set.of(albumTestData()));
    private final EventService eventService = new ThreadSafeEventService();
    private final CardAssigner cardAssigner = new ThreadSafeCardAssigner(userService, cardService, eventService);


    @Test(timeout = 2000L)
    public void shouldEmmitAllEvents() {
        Album album = albumTestData();
        final List<Card> allCards = album.sets.stream().map(set -> set.cards).flatMap(Collection::stream).collect(toList());

        final List<Event> emmitedEvents = new CopyOnWriteArrayList<>();
        eventService.subscribe(emmitedEvents::add, SET_FINISHED, ALBUM_FINISHED);

        ExecutorService executorService = newFixedThreadPool(10);
        while (!allUsersCompletedCollection(emmitedEvents, album)) {
            executorService.submit(() ->
                    cardAssigner.assignCard(getRandomItem(users).getId(), getRandomItem(allCards).id));
        }

        assert emmitedEvents.stream().filter(event -> event.type == ALBUM_FINISHED).count() == users.size();
        assert emmitedEvents.stream().filter(event -> event.type == SET_FINISHED).count() == (long) users.size() * album.sets.size();
    }

    private <T> T getRandomItem(List<T> item) {
        return item.get(nextInt(0, item.size()));
    }

    private boolean allUsersCompletedCollection(List<Event> events, Album album) {
        return events.size() == users.size() + users.size() * album.sets.size();
    }

    private Album albumTestData() {
        return new Album(1L, "Animals", newHashSet(
                new AlbumSet(1L, "Birds", newHashSet(new Card(1L, "Eagle"), new Card(2L, "Cormorant"), new Card(3L, "Sparrow"), new Card(4L, "Raven"))),
                new AlbumSet(2L, "Fish", newHashSet(new Card(5L, "Salmon"), new Card(6L, "Mullet"), new Card(7L, "Bream"), new Card(8L, "Marline")))));
    }
}