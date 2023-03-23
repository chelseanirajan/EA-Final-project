package edu.miu.badge.services.impl;



import edu.miu.badge.exceptions.TransactionDeclinedException;
import edu.miu.badge.domains.*;
import edu.miu.badge.dto.RequestTransactionDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.enumeration.PlanTypeEnum;
import edu.miu.badge.enumeration.RoleType;
import edu.miu.badge.enumeration.TransactionType;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.repositories.BadgeRepository;
import edu.miu.badge.repositories.LocationRepository;
import edu.miu.badge.repositories.MembershipRepository;
import edu.miu.badge.repositories.TransactionRepository;
import edu.miu.badge.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Value("${studentLimit}")
    private int studentMealPlanLimit;
    @Value("${staffLimit}")
    private int staffFacultyMealPlanLimit;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseTransactionDTO createTransaction(RequestTransactionDTO requestTransactionDTO) throws TransactionDeclinedException
    {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        Badge badgeOptional = badgeRepository.getBadgeByBadgeNumber(requestTransactionDTO.getBadgeId())
                .orElseThrow(() -> {
                    transaction.setType(TransactionType.DENIED);
                    transactionRepository.save(transaction);
                    return new TransactionDeclinedException("Badge doesnt exist!");
                });
        List<Membership> memberships = membershipRepository.findMembershipsByMemberId(badgeOptional.getMember().getId());
        Member member = badgeOptional.getMember();
        Membership ms = memberships.stream().filter(membership ->
                Objects.equals(membership.getPlan().getId(), requestTransactionDTO.getPlanId())).collect(Collectors.toList()).get(0);
        transaction.setMember(member);
        transaction.setMembership(ms);
        // Is Membership is expired or not.
        // cannot perform to check both membershipt start and end date in plan id
        //need to review
        boolean isExpiredOrMemberShipPlan = memberships
                .stream()
                .anyMatch(membership -> LocalDate.now().isBefore(membership.getEndDate())
                        && LocalDate.now().isAfter(membership.getStartDate()));
        if(!isExpiredOrMemberShipPlan) {
            transaction.setType(TransactionType.DENIED);
            transactionRepository.save(transaction);
            throw new TransactionDeclinedException("Your membership has expired. Please update before use!");

        }
            //check plan
        boolean isCorrectPlan = memberships
                .stream()
                .map(membership -> membership.getPlan().getId())
                .anyMatch(planId -> Objects.equals(planId,requestTransactionDTO.getPlanId()));
        if(!isCorrectPlan) {
            transaction.setType(TransactionType.DENIED);
            transactionRepository.save(transaction);
            throw new TransactionDeclinedException("You dont have a membership for this plan!");

        }


        // Check the member is attended in the right time slot or not
        Location location = locationRepository.findById(requestTransactionDTO.getLocationId().longValue())
                .orElseThrow(() -> {
                    transaction.setType(TransactionType.DENIED);
                    transactionRepository.save(transaction);
                    return new TransactionDeclinedException("Location not found!");
                });
        transaction.setLocation(location);

        boolean isCorrectTimeSlot = location.getTimeSlots().stream()
                .anyMatch(timeSlot -> LocalTime.now().isAfter(timeSlot.getStartTime())
                        && LocalTime.now().isBefore(timeSlot.getEndTime())
                        && LocalDateTime.now().getDayOfWeek().toString().equals(timeSlot.getDay().toString()));
        if(!isCorrectTimeSlot){
            transaction.setType(TransactionType.DENIED);
            transactionRepository.save(transaction);
            throw new TransactionDeclinedException("You cant use this service at this time period!");
        }
        boolean isCountValid = true;
        if(ms.getPlanType().getPlanType().equals(PlanTypeEnum.LIMITED)){
            if(member.getRoles().stream().anyMatch(role -> role.getRoleType().equals(RoleType.STUDENT))){
                if (ms.getUsedAllowances() < studentMealPlanLimit){
                    ms.setUsedAllowances(ms.getUsedAllowances() +1);
                    isCountValid = true;
                }
                else
                    isCountValid = false;
            } else if ((member.getRoles().stream().anyMatch(role -> role.getRoleType().equals(RoleType.STAFF))) ||
                    (member.getRoles().stream().anyMatch(role -> role.getRoleType().equals(RoleType.FACULTY)))) {
                if(ms.getUsedAllowances() < staffFacultyMealPlanLimit){
                    ms.setUsedAllowances(ms.getUsedAllowances() + 1);
                    isCountValid = true;
                }
                else
                    isCountValid = false;
            }
        }
        if(!isCountValid) {
            transaction.setType(TransactionType.DENIED);
            transactionRepository.save(transaction);
            throw new TransactionDeclinedException("You have used all your allowance for the period!");
        }

        transaction.setType(TransactionType.ALLOWED);
        return modelMapper.map(transactionRepository.save(transaction), ResponseTransactionDTO.class);
    }

    @Override
    public ResponseTransactionDTO getTransaction(int id) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction with ID " + id + " not found");
        }
        return modelMapper.map(transaction, ResponseTransactionDTO.class);
    }


    @Override
    public List<ResponseTransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<ResponseTransactionDTO> responseTransactionDTOS = new ArrayList<>();
        for (Transaction transaction : transactions) {
            responseTransactionDTOS.add(modelMapper.map(transaction, ResponseTransactionDTO.class));
        }
        return responseTransactionDTOS;
    }

    @Scheduled(fixedRate = 200000)
    public void resetStudentMealCount(){
        membershipRepository.updateMembershipsMealCountByStudentRole();
    }
    @Scheduled(fixedRate = 500000)
    public void resetStaffMealCount(){
        membershipRepository.updateMembershipsMealCountByFacultyRole();
        membershipRepository.updateMembershipsMealCountByStaffRole();
    }
}

