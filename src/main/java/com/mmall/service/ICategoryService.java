package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Xin on 5/9/2017.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId);

    ServerResponse<List<Integer>>  selectCategoryAndChildrenById(Integer categoryId);
}
