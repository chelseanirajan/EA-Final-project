package edu.miu.badge.services;

import edu.miu.badge.dto.ResponseBadgeDTO;
import edu.miu.badge.dto.RequestMemberDTO;
import edu.miu.badge.dto.ResponseMemberDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;

import java.util.List;

public interface MemberService {
    public ResponseMemberDTO insertNewMember(RequestMemberDTO requestMemberDTO);
    public ResponseMemberDTO getMemberById(int id);
    public String deleteMemberById(int id);
    List<ResponseMemberDTO> getAllMembers();
    ResponseMemberDTO updateMember(int id, RequestMemberDTO requestMemberDTO);
    List<ResponseBadgeDTO> getMemberBadges(int id);
    List<ResponseTransactionDTO> getMemberTransactions(int id);
}
