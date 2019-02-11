package com.ww.seckill.dataobject;

import lombok.Data;

@Data
public class ItemStockDO {
    private Integer id;

    private Integer stock;

    private Integer itemId;
}