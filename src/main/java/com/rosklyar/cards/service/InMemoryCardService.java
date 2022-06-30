package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public class InMemoryCardService implements CardService {
    private final Set<Album> albums;

    @Override
    public Card get(Long id) {
        return albums.stream()
                .flatMap(album -> album.sets.stream())
                .flatMap(albumSet -> albumSet.cards.stream())
                .filter(card -> card.id == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Could not found card with id %s", id)));
    }

    @Override
    public AlbumSet getParentAlbumSet(Card card) {
        return albums.stream()
                .flatMap(album -> album.sets.stream())
                .filter(albumSet -> albumSet.cards.contains(card))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Could not found parent of a card '%s'", card.name)));
    }

    @Override
    public Album getParentAlbum(AlbumSet albumSet) {
        return albums.stream()
                .filter(album -> album.sets.contains(albumSet))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Could not found parent of a set '%s'", albumSet.name)));
    }

    @Override
    public boolean isAlbumSetComplete(Collection<Card> cards, AlbumSet albumSet) {
        return cards.containsAll(albumSet.cards);
    }

    @Override
    public boolean isAlbumComplete(Collection<AlbumSet> sets, Album album) {
        return sets.containsAll(album.sets);
    }
}
