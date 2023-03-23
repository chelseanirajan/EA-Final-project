package edu.miu.badge.services.impl;

import edu.miu.badge.domains.*;
import edu.miu.badge.dto.ResponseBadgeDTO;
import edu.miu.badge.dto.RequestMemberDTO;
import edu.miu.badge.dto.ResponseMemberDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.repositories.MemberRepository;
import edu.miu.badge.repositories.RoleRepository;
import edu.miu.badge.repositories.UserRepository;
import edu.miu.badge.services.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseMemberDTO insertNewMember(RequestMemberDTO requestMemberDTO) throws ResourceNotFoundException{
        Set<Role> role = new HashSet<>();
        for(Integer roleId: requestMemberDTO.getRoles()){
            Optional<Role> role1 = roleRepository.findById(roleId);
            if(role1.isPresent()){
                role.add(role1.get());
            }else{
                throw new ResourceNotFoundException("Role with id " + roleId + " not found");
            }
        }
        Member member = modelMapper.map(requestMemberDTO, Member.class);
        member.setRoles(role);
        Member savedMember=memberRepository.save(member);
        User newUser = new User();
        newUser.setUsername(requestMemberDTO.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(requestMemberDTO.getPassword()));
        newUser.setMemberId(savedMember.getId());
        userRepository.save(newUser);
        return modelMapper.map(savedMember, ResponseMemberDTO.class);
    }

    @Override
    public ResponseMemberDTO getMemberById(int id) throws ResourceNotFoundException{
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
            return modelMapper.map(member.get(), ResponseMemberDTO.class);
        }
        throw new ResourceNotFoundException("Member with id " + id + " not found");
    }

    @Override
    public String deleteMemberById(int id) throws ResourceNotFoundException{
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
            memberRepository.delete(member.get());
            return "Member with id " + id + " deleted successfully";
        }
        throw new ResourceNotFoundException("Member with id " + id + " not found");
    }
    @Override
    public List<ResponseBadgeDTO> getMemberBadges(int id) {
        List<Badge> badges = memberRepository.allBadgesOfMember(id);
        List<ResponseBadgeDTO> responseBadgeDTOS = new ArrayList<>();
        for(Badge badge: badges){
            responseBadgeDTOS.add(modelMapper.map(badge, ResponseBadgeDTO.class));
        }
        return responseBadgeDTOS;
    }

    @Override
    public List<ResponseTransactionDTO> getMemberTransactions(int id) {
        List<Transaction> transactions = memberRepository.allTransactionsOfMember(id);
        List<ResponseTransactionDTO> responseTransactionDTOS = new ArrayList<>();
        for(Transaction transaction: transactions){
            responseTransactionDTOS.add(modelMapper.map(transaction, ResponseTransactionDTO.class));
        }
        return responseTransactionDTOS;
    }
    @Override
    public ResponseMemberDTO updateMember(int id, RequestMemberDTO requestMemberDTO) throws ResourceNotFoundException{
        Optional<Member> memberOptional = memberRepository.findById(id);
        if(memberOptional.isPresent()){
            Member toBeUpdated = memberOptional.get();
            toBeUpdated.setFirstName(requestMemberDTO.getFirstName());
            toBeUpdated.setLastName(requestMemberDTO.getLastName());
            toBeUpdated.setEmail(requestMemberDTO.getEmail());
            toBeUpdated.setRoles(requestMemberDTO.getRoles().stream().map(roleId -> {
                Optional<Role> role = roleRepository.findById(roleId);
                if(role.isPresent()){
                    return role.get();
                }else{
                    throw new ResourceNotFoundException("Role with id " + roleId + " not found");
                }
            }).collect(Collectors.toSet()));
            return modelMapper.map(memberRepository.save(toBeUpdated), ResponseMemberDTO.class);
        }else {
            throw new ResourceNotFoundException("Member with id " + id + " doesn't exists");
        }
    }
    @Override
    public List<ResponseMemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(member -> modelMapper.map(member, ResponseMemberDTO.class))
                .collect(Collectors.toList());
    }
}
