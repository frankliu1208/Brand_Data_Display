package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceImpl implements BrandService {
    //create SqlSessionFactory object,  SqlSessionFactoryUitls is a tool class
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();


    @Override
    public List<Brand> selectAll() {
        //2. create SqlSession object
        SqlSession sqlSession = factory.openSession();
        //3. get object of BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. call the related methods
        List<Brand> brands = mapper.selectAll();

        //5. release resources
        sqlSession.close();

        return brands;
    }

    @Override
    public void add(Brand brand) {

        SqlSession sqlSession = factory.openSession();

        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.add(brand);
        sqlSession.commit();

        sqlSession.close();
    }

    @Override
    public void deleteById(int id) {

        SqlSession sqlSession = factory.openSession();

        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.deleteById(id);

        sqlSession.commit();

        sqlSession.close();
    }


    @Override
    public void deleteByIds(int[] ids) {

        SqlSession sqlSession = factory.openSession();

        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.deleteByIds(ids);

        sqlSession.commit();

        sqlSession.close();
    }

//    according to the two parameters from front end, and calculate the starting index, and size
    @Override
    public PageBean<Brand> selectByPage(int currentPage, int pageSize) {

        SqlSession sqlSession = factory.openSession();

        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);


        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        List<Brand> rows = mapper.selectByPage(begin, size);
        int totalCount = mapper.selectTotalCount();

        //7. get pageBean object, put the data which is coming from mapper layer and put into the pagebean object
        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();

        return pageBean;
    }

    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {

        SqlSession sqlSession = factory.openSession();

        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);


        int begin = (currentPage - 1) * pageSize;

        int size = pageSize;


        String brandName = brand.getBrandName();
        if (brandName != null && brandName.length() > 0) {
            brand.setBrandName("%" + brandName + "%");
        }

        String companyName = brand.getCompanyName();
        if (companyName != null && companyName.length() > 0) {
            brand.setCompanyName("%" + companyName + "%");
        }



        List<Brand> rows = mapper.selectByPageAndCondition(begin, size, brand);

        int totalCount = mapper.selectTotalCountByCondition(brand);

        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();

        return pageBean;
    }


}
