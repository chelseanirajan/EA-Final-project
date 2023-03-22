package edu.miu.badge.services;


import edu.miu.badge.dto.RequestUserDTO;
import edu.miu.badge.dto.ResponseMemberDTO;

public interface LoginService {
    public ResponseMemberDTO login(RequestUserDTO user);
}
