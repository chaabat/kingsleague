package com.kingsleague.service;

import com.kingsleague.repository.interfaces.TeamRepository;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TournamentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentService.class);

    private TournamentRepository tournamentRepository;
    private TeamRepository teamRepository;

    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}
