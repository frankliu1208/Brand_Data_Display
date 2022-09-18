package com.itheima.mapper;

import com.itheima.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {

    /**
     * search all method
     * for simple code, we use annotation to write the sql, like below,  for complicated sql, we use .xml file,  it is in resources folder
     */
    @Select("select * from tb_brand")
//    mapping between entity class and database sheets because the names are not complied.
//    it is located in BrandMapper.xml.  the name within the bracket below shall be the same
//    with id property in resultMap tag in .xml file
    @ResultMap("brandResultMap")
    List<Brand> selectAll();

    /**
     * Add the data
     */
    @Insert("insert into tb_brand values(null,#{brandName},#{companyName},#{ordered},#{description},#{status})")
    void add(Brand brand);

    /**
     * delete by id
     */
    void deleteById(int id);

    /**
     * series delete
     * sql in .xml file
     */
    void deleteByIds(@Param("ids") int[] ids);



    /**
     * search based on splitting the pages
     */
    @Select("select * from tb_brand limit #{begin} , #{size}")
    @ResultMap("brandResultMap")
    List<Brand> selectByPage(@Param("begin") int begin,@Param("size") int size);

    /**
     * search for total record number
     * @return
     */
    @Select("select count(*) from tb_brand ")
    int selectTotalCount();

    /**
     *  By-page and conditional search
     * 不使用@Param注解时，参数只能有一个，并且是Javabean。在SQL语句里可以引用JavaBean的属性，而且只能引用JavaBean的属性。
     * the conditions are in parameter brand
     */
//  usage of @Param   https://www.cnblogs.com/lenve/p/11229590.html
    List<Brand> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("brand") Brand brand);

    /**
     *  search for total record number based on condition
     */
    int selectTotalCountByCondition(Brand brand);

}
