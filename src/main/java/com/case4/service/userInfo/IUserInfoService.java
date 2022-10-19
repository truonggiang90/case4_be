package com.case4.service.userInfo;

import com.case4.model.entity.user.UserInfo;
import com.case4.service.IGeneralService;

public interface IUserInfoService extends IGeneralService<UserInfo> {
    UserInfo findByUserId(Long id);
    Long findUserByUserInfo(Long id);
}
