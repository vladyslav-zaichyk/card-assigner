package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.domain.User;

import java.util.Collection;

public interface UserService {
    public User get(Long id);

    public void add(User user);

    public void addCard(User user, Card card);

    public Collection<Card> getCollectedCards(User user);

    public Collection<AlbumSet> getCollectedAlbumSets(User user);

    public void completeSet(User user, AlbumSet albumSet);

    public void completeAlbum(User user, Album album);

    public boolean isCompletedAlbumSet(User user, AlbumSet albumSet);

    public boolean isCompletedAlbum(User user, Album album);
}
