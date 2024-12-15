package net.fullstack7.studyShare.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.studyShare.domain.Post;
import net.fullstack7.studyShare.dto.FriendCheckDTO;
import net.fullstack7.studyShare.dto.FriendDTO;
import net.fullstack7.studyShare.dto.member.MemberDTO;
import net.fullstack7.studyShare.dto.post.PostShareDTO;
import net.fullstack7.studyShare.repository.PostRepository;
import net.fullstack7.studyShare.repository.ShareRepository;
import net.fullstack7.studyShare.service.FriendService;
import net.fullstack7.studyShare.service.share.ShareServiceIf;
import net.fullstack7.studyShare.util.JSFunc;
import net.fullstack7.studyShare.util.Paging;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;
    private final ShareServiceIf shareService;
    private final PostRepository postRepository;
    private final ShareRepository shareRepository;

    @GetMapping("/list")
    public String list(Model model, HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam(required = false) String searchValue) {
        String userId = (String) request.getAttribute("userId");
        List<String> friendList = friendService.list(userId);
        model.addAttribute("friendList", friendList);
        return "friend/list";
    }

    @GetMapping("/searchUserById")
    @ResponseBody
    public List<FriendCheckDTO> searchUserById(@RequestParam String searchId, HttpServletRequest request) { // 여기에서 검색된 아이디가 있는지 없는지 여부를 확인하려면 ? 만약 null이 나오면 어떤 에러가 뜨는지?
        String userId = (String) request.getAttribute("userId");
        log.info("searchId: {}", searchId);
        List<String> friendList = friendService.list(userId);
        List<String> searchList = friendService.searchUsersById(userId, searchId);

        List<FriendCheckDTO> friendCheckedList = new ArrayList<>();
        for(String search : searchList) {
            FriendCheckDTO friendCheckDTO = new FriendCheckDTO();
            friendCheckDTO.setUserId(search);

            //여기서 나랑 친구 신청이 걸려있는지 여부를 체크해줘야 함.
            friendCheckDTO.setReceived(friendService.amIReceiver(userId, search)); // 내가 받았는지 여부 확인 0이면 없음, 1이면 내가 받은거임.
            friendCheckDTO.setSent(friendService.amISender(userId, search)); // 내가 보냈는지 여부 확인 0이면 없음 1이면 내가 보낸거임.

            for (String friend : friendList) {
                if (search.equals(friend)) {
                    friendCheckDTO.setIsFriend(1);
                    break;
                }
            }
            friendCheckedList.add(friendCheckDTO);
        }
        return friendCheckedList;
    }

    @PostMapping("/sendRequest")
    @ResponseBody  // 응답을 JSON으로 반환하도록 설정
    public ResponseEntity<?> sendRequest(@RequestBody FriendDTO friendDTO, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        friendDTO.setRequesterId(userId);
        friendDTO.setStatus(0);
        boolean success = friendService.sendFriendRequest(friendDTO);
        if(success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/cancelRequest")
    @ResponseBody  // 응답을 JSON으로 반환하도록 설정
    public ResponseEntity<?> cancelRequest(@RequestBody FriendDTO friendDTO, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        friendDTO.setRequesterId(userId);

        boolean success = friendService.cancelFriendRequest(friendDTO);
        if(success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/acceptRequest")
    @ResponseBody  // 응답을 JSON으로 반환하도록 설정
    public ResponseEntity<?> acceptRequest(@RequestBody FriendDTO friendDTO, HttpServletRequest request) {
        log.info("friendDTO:{}", friendDTO);
        String userId = (String) request.getAttribute("userId");
        friendDTO.setFriendId(userId);

        boolean success = friendService.acceptFriendRequest(friendDTO);
        if(success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/rejectRequest")
    @ResponseBody
    public ResponseEntity<?> rejectRequest(@RequestBody FriendDTO friendDTO, HttpServletRequest request) {
        log.info("friendDTO:{}", friendDTO);
        String userId = (String) request.getAttribute("userId");
        friendDTO.setFriendId(userId);

        boolean success = friendService.rejectFriendRequest(friendDTO);
        if(success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
    @PostMapping("/deleteFriend")
    @ResponseBody
    public ResponseEntity<?> deleteFriend(@RequestBody FriendDTO friendDTO, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        friendDTO.setFriendId(userId);
        //requesterId가 상대, userId가 나임
        //친구 관계가 어떻게 되어있는 지 몰라서 두 번 삭제를 해보든가, 아니면 올바른 정보를 가져와서 그걸 삭제하든가 이긴 한데
        //기냥 서비스에서 두 번 삭제해봄
        boolean deleteShare = friendService.deleteShare(friendDTO);
        boolean success = friendService.deleteFriend(friendDTO);

        List<Integer> postIdList1 = friendService.postIdList(friendDTO.getFriendId());
        log.info("PostIdList1:{}", postIdList1);
        List<Integer> postIdList2 = friendService.postIdList(friendDTO.getRequesterId());
        log.info("PostIdList2:{}", postIdList2);


        for(Integer postId : postIdList1) {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
            boolean isShared = shareRepository.existsByPostId(postId);
            if (!isShared) {
                post.setShare(0);
                postRepository.save(post);
            }
        }
        for(Integer postId : postIdList2) {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
            boolean isShared = shareRepository.existsByPostId(postId);
            if (!isShared) {
                post.setShare(0);
                postRepository.save(post);
            }
        }


        if(success || deleteShare) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/received")
    public String received(Model model, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        List<String> receivedList = friendService.receivedList(userId);
        log.info("receivedList: {}", receivedList);
        model.addAttribute("receivedList", receivedList);
        return "friend/received";
    }

    @GetMapping("/sent")
    public String sent(Model model, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        List<String> sentList = friendService.sentList(userId);
        log.info("sentList: {}", sentList);
        model.addAttribute("sentList", sentList);
        return "friend/sent";
    }





}
