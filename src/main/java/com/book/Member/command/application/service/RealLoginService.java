package com.book.Member.command.application.service;

import com.book.Member.command.application.dto.MemberDTO;
import com.book.Member.command.domain.aggregate.entity.MemberEntity;
import com.book.Member.command.domain.repository.LoginRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RealLoginService {
    private final LoginRepository loginRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RealLoginService(LoginRepository loginRepository, ModelMapper modelMapper) {
        this.loginRepository = loginRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void saveRegister(MemberDTO memberdto) {

        MemberEntity member = new MemberEntity();
        member.setMemberId(memberdto.getMemberId());
        member.setMemberNickname(memberdto.getMemberNickname());
        member.setMemberEmail(memberdto.getMemberEmail());
        member.setIsDeleted(memberdto.getIsDeleted());
        loginRepository.save(member);
        loginRepository.flush();

    }
    @Transactional
    public void deletereportId(String reportNo) {

        try {
            MemberEntity member = loginRepository.findById(reportNo)
                    .orElseThrow(() -> new NotFoundException("존재하지 않은 넘버입니다."));
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public boolean duplicate(String reportNo) {
        return loginRepository.existsByMemberEmail(reportNo);

    }


}
