package com.atzybank.springcloud.alibaba.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_storage")
public class Storage {
    @TableId
    private Long id;
    @TableField("product_id")
    private Long productId;
    @TableField("total")
    private Integer total;
    @TableField("used")
    private Integer used;
    @TableField("residue")
    private Integer residue;
}
