package com.chanzor.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MessagePackage {
    private Integer id;

    private Integer packageMessageNum;

    private BigDecimal packageMessagePrice;

    private Integer packageMessageType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageMessageNum() {
        return packageMessageNum;
    }

    public void setPackageMessageNum(Integer packageMessageNum) {
        this.packageMessageNum = packageMessageNum;
    }

    public BigDecimal getPackageMessagePrice() {
        return packageMessagePrice;
    }

    public void setPackageMessagePrice(BigDecimal packageMessagePrice) {
        this.packageMessagePrice = packageMessagePrice;
    }

    public Integer getPackageMessageType() {
        return packageMessageType;
    }

    public void setPackageMessageType(Integer packageMessageType) {
        this.packageMessageType = packageMessageType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}