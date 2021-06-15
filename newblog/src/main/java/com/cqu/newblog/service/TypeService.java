package com.cqu.newblog.service;

import com.cqu.newblog.dao.TypeDao;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.redis.RedisService;
import com.cqu.newblog.redis.TypeVoKey;
import com.cqu.newblog.vo.TypeVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  19:50
 */
@Service
public class TypeService {
    @Autowired
    TypeDao typeDao;
    @Autowired
    BlogService blogService;
    @Autowired
    RedisService redisService;

    public List<Type> getTypeList() {
        return typeDao.getTypeList();
    }

    public List<TypeVo> getTypeVoList() {
        List<TypeVo> typeVos = new ArrayList<TypeVo>();
        List<Integer> typeIds = getTypeIdList();
        for (Integer typeId : typeIds) {
            TypeVo typeVo = redisService.get(TypeVoKey.getTypeVoList, "" + typeId, TypeVo.class);
            if (typeVo == null) {
                break;
            }
            typeVos.add(typeVo);
        }
        if (typeVos.isEmpty()) {
            List<Type> typeList = getTypeList();

            for (Type type : typeList) {
                Integer num = blogService.getTypeCountById(type.getId());
                if (num == null) {
                    num = 0;
                }
                TypeVo typeVo = new TypeVo();
                typeVo.setBlogNumber(num);
                typeVo.setType(type);
                redisService.set(TypeVoKey.getTypeVoList, "" + type.getId(), typeVo);
                typeVos.add(typeVo);
            }
            return typeVos;
        } else return typeVos;


    }

    private List<Integer> getTypeIdList() {
        return typeDao.getTypeIdList();
    }

    public Type getTypeByTypeId(int typeId) {
        return typeDao.getTypeByTypeId(typeId);
    }


    public boolean createType(Type type) {
        String curName = typeDao.getTypeByName(type.getName());
        if(curName!=null){
            return false;
        }
        int result = typeDao.createByName(type.getName());
        return result > 0;
    }

    public void updateType(Type type) {
        typeDao.update(type.getId(),type.getName());
    }

    public void delete(int id) {
        typeDao.delete(id);
    }
}
