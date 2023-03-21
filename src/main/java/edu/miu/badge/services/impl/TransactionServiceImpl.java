package edu.miu.badge.services.impl;

import edu.miu.badge.controllers.TransactionNotFoundException;
import edu.miu.badge.domains.*;
import edu.miu.badge.dto.RequestTransactionDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.enumeration.PlanTypeEnum;
import edu.miu.badge.enumeration.TransactionType;
import edu.miu.badge.repositories.BadgeRepository;
import edu.miu.badge.repositories.LocationRepository;
import edu.miu.badge.repositories.MembershipRepository;
import edu.miu.badge.repositories.TransactionRepository;
import edu.miu.badge.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final static int LIMITEDPLAN = 200;

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
    public ResponseTransactionDTO createTransaction(RequestTransactionDTO requestTransactionDTO) throws Exception {
        Badge badgeOptional = badgeRepository.getBadgeByBadgeNumber(requestTransactionDTO.getBadgeId())
                .orElseThrow(() -> {
                    return new TransactionNotFoundException("You are not allowed to use this service!");
                });
        List<Membership> memberships = membershipRepository.findMembershipsByMemberId(badgeOptional.getMember().getId());

        // Is Membership is expired or not.
        // cannot perform to check both membershipt start and end date in plan id
        //need to review
        boolean isExpiredOrMemberShipPlan = memberships
                .stream()
                .anyMatch(membership -> LocalDate.now().isBefore(membership.getEndDate())
                        && LocalDate.now().isAfter(membership.getStartDate()));
        //check plan
        boolean isCorrectPlan = memberships
                .stream()
                .map(membership -> membership.getPlan().getId())
                .anyMatch(planId -> Objects.equals(planId,requestTransactionDTO.getPlanId()));


        // Check the member is attended in the right time slot or not
        Location location = locationRepository.findById(requestTransactionDTO.getLocationId().longValue())
                .orElseThrow(() -> {
                    return new Exception("Location cannot find.");
                });
        boolean isCorrectTimeSlot = location.getTimeSlots().stream()
                .anyMatch(timeSlot -> LocalTime.now().isAfter(timeSlot.getStartTime())
                        && LocalTime.now().isBefore(timeSlot.getEndTime())
                        && LocalDateTime.now().getDayOfWeek().toString().equals(timeSlot.getDay().toString()));
        //Testing Data from utils
        boolean isValid = memberships
                .stream().anyMatch(membership ->
                     Objects.equals(membership.getPlan().getId(), requestTransactionDTO.getPlanId())
                            && membership.getPlanType().getPlanType().toString().equals(PlanTypeEnum.LIMITED.toString())
                            && membership.getNumberOfAllowance() <= LIMITEDPLAN
                );
        if (isExpiredOrMemberShipPlan && isCorrectTimeSlot && isValid) {
            Transaction transaction = new Transaction();
            transaction.setType(TransactionType.ALLOWED);
            transaction.setMember(badgeOptional.getMember());
            transaction.setMembership(memberships.stream().filter(membership -> Objects.equals(membership.getPlan().getId(), requestTransactionDTO.getPlanId())).collect(Collectors.toList()).get(0));
            transaction.setDate(LocalDateTime.now());
            transaction.setLocation(location);
            return modelMapper.map(transactionRepository.save(transaction), ResponseTransactionDTO.class);
        } else {
            throw new TransactionNotFoundException("Transaction cannot be inserted");
        }

    }

    @Override
    public ResponseTransactionDTO getTransaction(int id) throws TransactionNotFoundException {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found");
        }
        return modelMapper.map(transaction, ResponseTransactionDTO.class);
    }

    @Override
    public ResponseTransactionDTO updateTransaction(int transactionId, ResponseTransactionDTO transaction) throws TransactionNotFoundException {
        Transaction transactionToBeUpdated = transactionRepository.findById(transactionId).orElse(null);
        if (transactionToBeUpdated == null) {
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found");
        }
        transactionToBeUpdated.setDate(transaction.getDate());
        transactionToBeUpdated.setMember(modelMapper.map(transaction.getMember(), Member.class));
        transactionToBeUpdated.setMembership(modelMapper.map(transaction.getMembership(), Membership.class));
        transactionToBeUpdated.setLocation(modelMapper.map(transaction.getLocation(), Location.class));
        transactionToBeUpdated.setType(transaction.getType());
        return modelMapper.map(transactionRepository.save(transactionToBeUpdated), ResponseTransactionDTO.class);
    }

    @Override
    public String deleteTransaction(int id) throws TransactionNotFoundException {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found");
        }
        transactionRepository.deleteById(id);
        return "Transaction with ID " + id + " deleted";
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
}

