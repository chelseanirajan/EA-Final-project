package edu.miu.badge.services;

import edu.miu.badge.dto.RequestBadgeDTO;
import edu.miu.badge.dto.ResponseBadgeDTO;

import java.util.List;

public interface BadgeService {
    ResponseBadgeDTO getBadge(int id);
    ResponseBadgeDTO createBadge(RequestBadgeDTO badge);
    ResponseBadgeDTO updateBadge(int id, RequestBadgeDTO badge);
    String inactiveBadge(int id);
    List<ResponseBadgeDTO> getAllBadges();
}
