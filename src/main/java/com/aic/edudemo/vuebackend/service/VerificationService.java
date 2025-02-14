package com.aic.edudemo.vuebackend.service;

import com.aic.edudemo.vuebackend.domain.dto.ReviewRequestDto;
import com.aic.edudemo.vuebackend.domain.entity.Users;
import com.aic.edudemo.vuebackend.domain.entity.Verified;
import com.aic.edudemo.vuebackend.repository.UserRepository;
import com.aic.edudemo.vuebackend.repository.VerifiedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService {

    private final VerifiedRepository verifiedRepository;
    private final UserRepository userRepository;

    public void createVerificationRequest(String userName, Integer userId) {

        if (!verifiedRepository.existsByUserIdAndStatus(userId)) {
            Verified verified = Verified.builder().
                    userId(userId).
                    build();
            verifiedRepository.save(verified);
        } else throw new RuntimeException("已申請過");

    }

    public Verified getVerifiedStatus(Integer userId) {


        return verifiedRepository.findByUserId(userId);

    }

    public List<Verified> getAllVerifiedHistory() {


        return verifiedRepository.findAllByHistory();
    }

    public List<Verified> getVerifiedList() {

        return verifiedRepository.findAllByStatus();
    }

    @Transactional
    public void VerifiedReview(String userName, Integer userId, Integer requestId, String role, ReviewRequestDto dto) {

        Verified verifiedOld = verifiedRepository.findById(requestId).orElse(null);

        Verified verified = Verified.builder().
                requestId(requestId).
                userId(verifiedOld.getUserId()).
                status(dto.getStatus()).
                requestAt(verifiedOld.getRequestAt()).
                reviewerId(userId).
                reviewerRole(role).
                comments(dto.getComments()).
                build();
        verifiedRepository.save(verified);

        Users user = userRepository.findById(userId).orElse(null);

        Users userUpate = Users.builder().
                userName(user.getUserName()).
                userEmail(user.getUserEmail()).
                userPhone(user.getUserPhone()).
                userId(user.getUserId()).
                userBirthDate(user.getUserBirthDate()).
                userIsVerified(true).
                userPwdHash(user.getUserPwdHash()).
                userIdCard(user.getUserIdCard()).
                userRegdate(user.getUserRegdate()).
                build();
        userRepository.save(userUpate);


    }


}
