package com.kingsleague.controller;

import com.kingsleague.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    private TeamService teamService;

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }
}
