package com.rosklyar.cards.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private final Long id;
    private Set<Card> cards = new HashSet<>();
    private Set<Album> completedAlbums = new HashSet<>();
    private Set<AlbumSet> completedAlbumSets = new HashSet<>();
}
