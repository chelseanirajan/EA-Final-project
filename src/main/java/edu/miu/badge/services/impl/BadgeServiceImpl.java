package edu.miu.badge.services.impl;

import edu.miu.badge.domains.Badge;
import edu.miu.badge.domains.Member;
import edu.miu.badge.dto.RequestBadgeDTO;
import edu.miu.badge.dto.ResponseBadgeDTO;
import edu.miu.badge.enumeration.BadgeStatus;
import java.util.Random;

import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.repositories.BadgeRepository;
import edu.miu.badge.repositories.MemberRepository;
import edu.miu.badge.services.BadgeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MemberRepository memberRepository;



    @Override
    public ResponseBadgeDTO getBadge(int id)throws ResourceNotFoundException {
        Badge badge = badgeRepository.findById(id).orElse(null);
        if (badge == null)
            throw new ResourceNotFoundException("Badge with ID " + id + " not found");
        return modelMapper.map(badge, ResponseBadgeDTO.class);
    }

    @Override
    public ResponseBadgeDTO createBadge(RequestBadgeDTO badge) {                            //exception handling
        Optional<Member> optionalMember = memberRepository.findById(badge.getMemberId());
        if (optionalMember.isEmpty())
            throw new ResourceNotFoundException("Member with ID " + badge.getMemberId() + " not found");
        if(badge.getBadgeStatus().equals(BadgeStatus.INACTIVE))
            throw new ResourceNotFoundException("You can't create an inactive badge");
        Badge newBadge = new Badge();
        newBadge.setBadgeStatus(BadgeStatus.ACTIVE);
        newBadge.setMember(optionalMember.get());
        if(optionalMember.get().getBadges().size() == 0) {
            int number = getNewBadgeNumber();
            newBadge.setBadgeNumber(number);
        }
        else{
            Badge activeBadgeToInactive = badgeRepository.getActiveBadge(badge.getMemberId()).get();
            newBadge.setBadgeNumber(activeBadgeToInactive.getBadgeNumber());
            activeBadgeToInactive.setBadgeStatus(BadgeStatus.INACTIVE);
            badgeRepository.save(activeBadgeToInactive);
        }
        badgeRepository.save(newBadge);
        return modelMapper.map(newBadge, ResponseBadgeDTO.class);
    }

    @Override
    public ResponseBadgeDTO updateBadge(int id, RequestBadgeDTO badge) throws ResourceNotFoundException{
        Optional<Badge> badgeToUpdate = badgeRepository.findById(id);
        if (badgeToUpdate.isEmpty())
            throw new ResourceNotFoundException("Badge with ID " + id + " not found");
        if(badge.getMemberId() != badgeToUpdate.get().getMember().getId())
            throw new ResourceNotFoundException("Member with ID " + badge.getMemberId() + " not found");
        if(badge.getBadgeStatus().equals(BadgeStatus.ACTIVE)){
            badgeRepository.updateBadgeStatusOfMember(badge.getMemberId(), BadgeStatus.INACTIVE);
            Badge newStatus = badgeToUpdate.get();
            newStatus.setBadgeStatus(BadgeStatus.ACTIVE);
            badgeRepository.save(newStatus);
            return modelMapper.map(newStatus, ResponseBadgeDTO.class);
        }
        else{
            Badge newStatus = badgeToUpdate.get();
            newStatus.setBadgeStatus(BadgeStatus.INACTIVE);
            badgeRepository.save(newStatus);
            return modelMapper.map(newStatus, ResponseBadgeDTO.class);
        }
    }

    @Override
    public String inactiveBadge(int id)throws ResourceNotFoundException {
        Optional<Badge> toInactve = badgeRepository.findById(id);
        if (toInactve.isEmpty())
            throw new ResourceNotFoundException("Badge with ID " + id + " not found");
        if(toInactve.get().getBadgeStatus().equals(BadgeStatus.INACTIVE))
            throw new ResourceNotFoundException("Badge with ID " + id + " is already inactive");
        toInactve.get().setBadgeStatus(BadgeStatus.INACTIVE);
        badgeRepository.save(toInactve.get());
        return "Badge with ID " +  id+ " deactivated";
    }

    @Override
    public List<ResponseBadgeDTO> getAllBadges() {
        Optional<List<Badge>> badges = badgeRepository.getActiveBadges();
        if (badges.isEmpty())
            throw new ResourceNotFoundException("No active badges found");
        List<ResponseBadgeDTO> responseBadgeDTOS = new ArrayList<>();
        for (Badge badge : badges.get()) {
            responseBadgeDTOS.add(modelMapper.map(badge, ResponseBadgeDTO.class));
        }
        return responseBadgeDTOS;
    }
    int getNewBadgeNumber(){
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(90000) + 10000;
        }while(badgeRepository.getBadgeByBadgeNumber(number).isPresent());
        return number;
    }

}
