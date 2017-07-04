package com.xiaxiao.riji.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2017/7/4.
 */
public class RiJiUser extends BmobUser {
    String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
