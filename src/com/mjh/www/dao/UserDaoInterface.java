package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Comment;
import com.mjh.www.po.Page;
import com.mjh.www.po.User;

public interface UserDaoInterface {
    public abstract Msg login(User user);
    public abstract Msg count(int id,String inf);
    public abstract Msg search(Page page);
    public abstract Msg find(int id);
    public abstract Msg isRegister(User user);
    public abstract Msg register(User user);
    public abstract Msg active(String code);
    public abstract Msg modify(User user);
    public abstract Msg isFavourite(User user,int routeId);
    public abstract Msg setFavourite(User user,int routeId);
    public abstract Msg cancelFavourite(User user,int routeId);
    public abstract Msg checkMyFavourite(User user,Page page);
    public abstract Msg countMyFavourite(User user);
    public abstract Msg giveComment(Comment comment);
    public abstract Msg countComment(int routeId);
    public abstract Msg payFor(User user,float giveMoney);
}
