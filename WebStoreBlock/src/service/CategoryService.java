package service;

import Dao.impls.CategoyrDaoImpls;
import bean.Category;
import utils.PageHelper;

import java.util.List;

public class CategoryService {
    CategoyrDaoImpls categoyrDaoImpls = new CategoyrDaoImpls();


    public int addCategoryInfo(String cname) {
        boolean same = categoyrDaoImpls.selectSameFromDB(cname);
        int res;
        if (same) {
            //已存在相同元素
            res = -1;
        } else {
            boolean b = categoyrDaoImpls.addCategoryToDataBase(cname);
            if (b) {
                //插入成功
                res = 1;
            } else {
                //插入失败
                res = -2;
            }
        }
        return res;
    }

    public List<Category> getAllCategory() {
        List<Category> categoryList = categoyrDaoImpls.getAllCategoryInDB();
        return categoryList;
    }

    public boolean modifyCategory(String id, String cname) {
        int cid = Integer.parseInt(id);
        boolean res = categoyrDaoImpls.modifyCategoryInDB(cid, cname);
        return res;
    }

    public boolean deleteTheInfoIn(String cname) {
        boolean res = categoyrDaoImpls.deleteThisInfoInDB(cname);
        return res;
    }

    public PageHelper<Category> findCategoryListByNum(String num) {
        int currentPageNum = 1;
        try {
            currentPageNum = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        List<Category> categoryList = categoyrDaoImpls.getAllCategoryInDB();
        int totalRecordsNum = categoryList.size();

        int PAGE_COUNT = 3;
        PageHelper<Category> pageHelper = new PageHelper<Category>(currentPageNum, totalRecordsNum, PAGE_COUNT);

        //limit为每一页显示的条数，offset为从哪个元素开始起始，表示查找从offset起始的limit个元素
        int limit = PAGE_COUNT;
        int offset = (currentPageNum - 1) * PAGE_COUNT;
        List<Category> list = categoyrDaoImpls.getPartCategoryInDB(limit, offset);
        pageHelper.setRecords(list);
        return pageHelper;
    }
}
