package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.domain.User;

import java.util.Collection;

public interface UserService {
    User get(Long id);

    void addCard(User user, Card card);

    Collection<Card> getCollectedCards(User user);

    Collection<AlbumSet> getCollectedAlbumSets(User user);

    void completeSet(User user, AlbumSet albumSet);

    void completeAlbum(User user, Album album);

    boolean isCompletedAlbumSet(User user, AlbumSet albumSet);

    boolean isCompletedAlbum(User user, Album album);
}
