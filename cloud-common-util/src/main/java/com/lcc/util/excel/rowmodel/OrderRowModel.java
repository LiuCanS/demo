package com.lcc.util.excel.rowmodel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.lcc.util.excel.time.LocalDateTimeConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * excel导出 表头类
 * @author lcc
 */
@Data
public class OrderRowModel {

    @ColumnWidth(16)
    @ExcelProperty(value = {"订单号"}, index = 0)
    private String id;

    @ApiModelProperty(value = "桌台号")
    @ColumnWidth(16)
    @ExcelProperty(value = {"桌台号"}, index = 1)
    private String storeTableName;

    @ColumnWidth(16)
    @ExcelProperty(value = {"下单时间"}, index = 2, converter = LocalDateTimeConverter.class)
    private LocalDateTime orderTime;

    @ColumnWidth(16)
    @ExcelProperty(value = {"结账时间"}, index = 3, converter = LocalDateTimeConverter.class)
    private LocalDateTime overTime;

    @ColumnWidth(16)
    @ExcelProperty(value = {"订单金额"}, index = 4)
    private BigDecimal orderAmount;

    @ColumnWidth(16)
    @ExcelProperty(value = {"实收金额"}, index = 5)
    private BigDecimal receiptAmount;

    @ColumnWidth(16)
    @ExcelProperty(value = {"支付方式"}, index = 6)
    private String paymentMethod;

    @ColumnWidth(16)
    @ExcelProperty(value = {"订单状态"}, index = 7)
    private String orderStatus;

}
