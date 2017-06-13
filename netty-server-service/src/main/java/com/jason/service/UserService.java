package com.jason.service;


import com.jason.vo.user.User;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-11-4
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    /**
     * 在保存用户时,发送用户修改通知消息, 由消息接收者异步进行较为耗时的通知邮件发送.
     *
     * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
     *
     */
    public void saveUser(User user);

    /**
     * 查询用户
     * @param searchParams
     * @return
     */
    public List<User> list(Map<String, Object> searchParams);

//    /**
//     * 分页查询
//     * @param userModel
//     * @return
//     */
//    public Pager<User> pageList(UserModel userModel);

    /**
     * 获取用户
     * @param id
     * @return
     */
    public User getUser(Long id);

    /**
     * 修改用户
     * @param user
     * @return boolean
     */
    public boolean update(User user);

    /**
     * 删除用户
     * @param id
     * @return boolean
     */
    public boolean delete(Long id);


    /**
     * 移除用户
     * @param id
     * @return boolean
     */
    public boolean remove(Long id);

}
