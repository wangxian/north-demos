package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PersonMapper extends BaseMapper<Person> {

    @Select("select * from person")
    List<Person> findAll();
}
