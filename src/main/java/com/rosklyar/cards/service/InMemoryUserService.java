package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
public class InMemoryUserService implements UserService {
    private final Collection<User> users;

    @Override
    public User get(Long id) {
        return users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Could not found user with id '%s'", id)));
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public void addCard(User user, Card card) {
        user.getCards().add(card);
    }

    @Override
    public Collection<Card> getCollectedCards(User user) {
        return user.getCards();
    }

    @Override
    public Collection<AlbumSet> getCollectedAlbumSets(User user) {
        return user.getCompletedAlbumSets();
    }

    @Override
    public void completeSet(User user, AlbumSet albumSet) {
        user.getCompletedAlbumSets().add(albumSet);
    }

    @Override
    public void completeAlbum(User user, Album album) {
        user.getCompletedAlbums().add(album);
    }

    @Override
    public boolean isCompletedAlbumSet(User user, AlbumSet albumSet) {
        return user.getCompletedAlbumSets().contains(albumSet);
    }

    @Override
    public boolean isCompletedAlbum(User user, Album album) {
        return user.getCompletedAlbums().contains(album);
    }
}
