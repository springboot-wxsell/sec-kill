package com.ww.seckill.service;

import com.ww.seckill.exception.BusinessException;
import com.ww.seckill.service.model.ItemModel;

import java.util.List;

/**
 * Created by wangwei on 2019/2/5.
 */
public interface ItemService {

    /**
     * 创建商品
     * @param itemModel
     * @return
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;


    /**
     *  商品列表浏览
     * @return
     */
    List<ItemModel> itemList();

    /**
     * 商品详情浏览
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id);
}
