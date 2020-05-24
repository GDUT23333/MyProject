package com.mjh.www.service;

import com.mjh.www.bean.Msg;
import com.mjh.www.dao.UserDao;
import com.mjh.www.po.Comment;
import com.mjh.www.po.Page;
import com.mjh.www.po.Route;
import com.mjh.www.po.User;
import com.mjh.www.util.PageUtil;

public class UserService {
    UserDao userDao = new UserDao();

    /**
     * 获得查询商品的数目，包括模糊查询的商品
     * @param id
     * @param inf
     * @return
     */
    public Msg totalCount(int id,String inf){
        Msg msg = userDao.count(id,inf);
        return msg;
    }

    /**
     * 分页获得所有商品简介，包括模糊查询的商品
     * @param page
     * @return
     */
    public Msg search(Page page){
        Msg msg = userDao.search(page);
        return msg;
    }

    /**
     * 查看商品的详情
     * @param id
     * @return
     */
    public Msg find(int id){
        Msg msg = userDao.find(id);
        Msg count = userDao.countComment(id);
        int totalCount = 0;
        if("成功".equals(count.getResult())&&"成功".equals(msg.getResult())){
            totalCount = (int)count.getMessage();
            Route route = (Route)msg.getMessage();
            route.setTotalCount(totalCount);
            return new Msg("成功",route);
        }

        /*if("成功".equals(msg.getResult())){
            Route route = (Route)msg.getMessage();
            route.setTotalCount(totalCount);
            return new Msg("成功",route);
        }*/
        return new Msg("失败",null);
    }

    /**
     * 判断用户是否已经注册
     * @return
     */
    public Msg isRegister(String email,String phone){
        User user = new User(email,phone);
        Msg msg = userDao.isRegister(user);
        return msg;
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    public Msg register(User user){
        Msg msg = userDao.register(user);
        return msg;
    }

    /**
     * 激活用户账号
     * @param code
     * @return
     */
    public Msg active(String code){
        Msg msg = userDao.active(code);
        return msg;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public Msg modify(User user){
        Msg msg = userDao.modify(user);
        return msg;
    }

    /**
     * 判断商品是否被用户收藏，调整前端的收藏按钮
     * @param user
     * @param routeId
     * @return
     */
    public Msg isFavourite(User user,int routeId){
        Msg msg = userDao.isFavourite(user,routeId);
        return msg;
    }

    /**
     * 收藏商品
     * @param user
     * @param routeId
     * @return
     */
    public Msg setFavoutite(User user,int routeId){
        Msg msg = userDao.setFavourite(user,routeId);
        return msg;
    }

    /**
     * 取消收藏的商品
     * @param user
     * @param routeId
     * @return
     */
    public Msg cancelFavourite(User user,int routeId){
        Msg msg = userDao.cancelFavourite(user,routeId);
        return msg;
    }

    /**
     * 查看用户的收藏
     * @param user
     * @return
     */
    public Msg checkMyFavourite(User user,int startLine,int pageSize,int currentPage,int totalCount,int totalPage){
        Page page = new Page();
        page.setStartLine(startLine);
        page.setPageSize(pageSize);
        page.setCurrentPage(currentPage);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);
        Msg msg = userDao.checkMyFavourite(user,page);
        return msg;
    }

    /**
     * 用户收藏的数据总数
     * @param user
     * @return
     */
    public Msg countFavourite(User user){
        Msg msg = userDao.countMyFavourite(user);
        return msg;
    }

    /**
     * 评论商品
     * @param user
     * @param content
     * @param routeId
     * @return
     */
    public Msg giveComment(User user,String content,int routeId){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setRouteId(routeId);
        comment.setUserNameTwo(user.getNameTwo());
        comment.setUserHeadPitcher(user.getHeadPitcher());
        Msg msg = userDao.giveComment(comment);
        return msg;
    }
    public Msg payFor(User user,Float price){
        Float balance = user.getMoney() - price;
        if(balance < 0){
            return new Msg("余额不足",user);
        }
        else{
            Msg msg = userDao.payFor(user,balance);
            if("支付成功".equals(msg.getResult())){
                user.setMoney(balance);
                return new Msg("支付成功",user);
            }
            return new Msg("支付失败",user);
        }
    }
}
