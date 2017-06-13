package com.jason.service.impl;

import com.jason.mybatis.dao.UserDao;
import com.jason.service.UserService;
import com.jason.utils.Constants;
import com.jason.vo.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-11-4
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Resource
    private UserDao userDao;

    @Override
    @Transactional(readOnly = false)
    public void saveUser(User user) {
        // 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
//        if (StringUtils.isNotBlank(user.getPassword())) {
//            entryptPassword(user);
//        }
        userDao.save(user);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
//    private void entryptPassword(User user) {
//        byte[] salt = Digests.generateSalt(SALT_SIZE);
////        user.setSalt(Encodes.encodeHex(salt));
//
//        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
//        user.setPassword(Encodes.encodeHex(hashPassword));
//    }

    @Override
    public List<User> list(Map<String, Object> searchParams) {
//        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
//        List<User> userList = userDao.findAll(spec);
        List<User> userList = userDao.list(searchParams);
        return userList;
    }

//    @Override
//    public Pager<User> pageList(UserModel userModel) {
//        Pager<User> pager = new Pager<User>();
//        pager.setPageSize(userModel.getPageSize());
//        // 数据统计
//        Integer totalCount = userDao.listCount(userModel);
//        pager.setTotalCount(totalCount);
//        if (pager.getTotalPages() < userModel.getPageNo()){
//            userModel.setPageNo(BaseModel.DEFAULT_PAGE_NO);
//        }
//        pager.setPageNo(userModel.getPageNo());
//        // 列表数据
//        List<User> list = userDao.pageList(userModel);
//        if ( list != null && list.size() > 0){
//            pager.setResult(list);
//        }
//        return pager;
//    }

    @Override
    public User getUser(Long id) {
        return userDao.get(id);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean update(User user) {
        int result = userDao.update(user);
        boolean resultFlag = (result > Constants.ZERO)?true:false;
        return resultFlag;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean delete(Long id) {
        int result = userDao.delete(id);
        boolean resultFlag = (result > Constants.ZERO)?true:false;
        return resultFlag;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean remove(Long id) {
        int result = userDao.remove(id);
        boolean resultFlag = (result > Constants.ZERO)?true:false;
        return resultFlag;
    }
}
