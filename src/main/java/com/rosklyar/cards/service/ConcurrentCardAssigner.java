package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.rosklyar.cards.domain.Event.Type.ALBUM_FINISHED;
import static com.rosklyar.cards.domain.Event.Type.SET_FINISHED;

/**
 * Created by rostyslavs on 11/21/2015.
 * Implemented by vlad-zaichyk :)
 */

@RequiredArgsConstructor
public class ConcurrentCardAssigner implements CardAssigner {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentCardAssigner.class);

    private final UserService userService;
    private final CardService cardService;
    private final EventService eventService;

    @Override
    public void assignCard(long userId, long cardId) {
        User user = userService.get(userId);
        Card card = cardService.get(cardId);

        logger.info("Adding '{}' card to the user with id: {}", card.name, userId);

        userService.addCard(user, card);
        AlbumSet parentSet = cardService.getParentAlbumSet(card);
        Album parentAlbum = cardService.getParentAlbum(parentSet);

        synchronized (user) {
            if (isUserJustCollectedSet(user, parentSet)) {
                logger.info("User with id '{}' just collected set '{}'", userId, parentSet.name);
                userService.completeSet(user, parentSet);
                eventService.notify(new Event(userId, SET_FINISHED));
            }

            if (isUserJustCollectedAlbum(user, parentAlbum)) {
                logger.info("User with id '{}' just collected album '{}'", userId, parentAlbum.name);
                userService.completeAlbum(user, parentAlbum);
                eventService.notify(new Event(userId, ALBUM_FINISHED));
            }
        }
    }

    private boolean isUserJustCollectedSet(User user, AlbumSet albumSet) {
        boolean isSetComplete = cardService.isAlbumSetComplete(userService.getCollectedCards(user), albumSet);
        boolean hasUserCompletedSet = userService.isCompletedAlbumSet(user, albumSet);
        return isSetComplete && !hasUserCompletedSet;
    }

    private boolean isUserJustCollectedAlbum(User user, Album album) {
        boolean isAlbumComplete = cardService.isAlbumComplete(userService.getCollectedAlbumSets(user), album);
        boolean hasUserCompletedAlbum = userService.isCompletedAlbum(user, album);
        return isAlbumComplete && !hasUserCompletedAlbum;
    }
}
