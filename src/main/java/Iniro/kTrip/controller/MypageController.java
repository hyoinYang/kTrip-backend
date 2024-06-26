package Iniro.kTrip.controller;


import Iniro.kTrip.domain.Favorite;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import Iniro.kTrip.dto.MemberDetails;
import Iniro.kTrip.dto.NicknameDto;
import Iniro.kTrip.dto.PasswordDto;
import Iniro.kTrip.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
@CrossOrigin(origins = "*") // CORS 에러 방지
public class MypageController {

    private final MypageService mypageService;

    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto) {
        mypageService.changePassword(passwordDto);
        return ResponseEntity.ok("/mypage");
    }

    @PostMapping("/nickname")
    public ResponseEntity<?> changeNickname(@RequestBody NicknameDto nicknameDto) {
        mypageService.changeNickname(nicknameDto);
        return ResponseEntity.ok("/mypage");
    }

    @GetMapping
    public ResponseEntity<?> showMypage(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("name", member.getName());
            response.put("nickname", member.getNickname());
            response.put("email", member.getEmail());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
        }
    }

    @GetMapping("/review")
    public ResponseEntity<?> showReview(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            List<Review> reviews = mypageService.FindReviews(member);
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
        }
    }

    @DeleteMapping("/review/{rid}")
    @PreAuthorize("hasAuthority('DELETE_REVIEW')")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable int rid) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            mypageService.deleteReview(rid, member);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
        }
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> showFavorite(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            List<Favorite> favorites = mypageService.FindFavorite(member);
            return ResponseEntity.ok(favorites);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
        }
    }

    @DeleteMapping("/favorite/{fid}")
    @PreAuthorize("hasAuthority('DELETE_FAVORITE')")
    public ResponseEntity<?> deleteFavorite(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable int fid) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            mypageService.deleteFavorite(fid, member);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
        }
    }
}