package com.kingsleague.repository.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TournamentRepositoryExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryExtension.class);

    private TournamentRepositoryImpl baseRepository;

    public TournamentRepositoryExtension(TournamentRepositoryImpl baseRepository) {
        this.baseRepository = baseRepository;
    }
}
