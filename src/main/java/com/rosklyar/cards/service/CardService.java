package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;

import java.util.Collection;

public interface CardService {
    public Card get(Long id);

    public AlbumSet getParentAlbumSet(Card card);

    public Album getParentAlbum(AlbumSet albumSet);

    public boolean isAlbumSetComplete(Collection<Card> cards, AlbumSet albumSet);

    public boolean isAlbumComplete(Collection<AlbumSet> cards, Album album);
}
