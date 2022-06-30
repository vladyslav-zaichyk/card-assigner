package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;

import java.util.Collection;

public interface CardService {
    Card get(Long id);

    AlbumSet getParentAlbumSet(Card card);

    Album getParentAlbum(AlbumSet albumSet);

    boolean isAlbumSetComplete(Collection<Card> cards, AlbumSet albumSet);

    boolean isAlbumComplete(Collection<AlbumSet> cards, Album album);
}
