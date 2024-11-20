package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.dto.message.ChatRoomDTO;
import net.fullstack7.edusecond.edusecond.dto.message.ChatMessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {
  List<ChatRoomDTO> selectChatRoomList(@Param("userId") String userId);
  ChatRoomDTO selectChatRoom(@Param("roomId") int roomId, @Param("userId") String userId);
  void insertChatRoom(ChatRoomDTO chatRoom);
  void insertMessage(ChatMessageDTO message);
  void updateMessageRead(@Param("roomId") int roomId, @Param("userId") String userId);
  List<ChatMessageDTO> selectChatMessages(@Param("roomId") int roomId);
  void updateUserChatRoomStatus(@Param("roomId") int roomId,
                                @Param("userType") String userType,
                                @Param("status") String status);
  ChatRoomDTO findExistingChatRoom(@Param("productId") int productId,
                                  @Param("sellerId") String sellerId,
                                  @Param("buyerId") String buyerId);
  int updateChatRoomsByUserId(@Param("userId") String userId);
  // int updateUserChatRoomStatus(@Param("roomId") Long roomId, 
  //                             @Param("userType") String userType, 
  //                             @Param("status") String status);
  boolean isActiveRoom(@Param("roomId") int roomId);
  int getUnreadCount(@Param("userId") String userId);
}
