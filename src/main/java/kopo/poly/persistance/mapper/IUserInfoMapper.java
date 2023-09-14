package kopo.poly.persistance.mapper;

import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserInfoMapper {

    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;

    List<UserInfoDTO> getUserList() throws Exception;
    UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;
}
