package com.lcc.util.excel.rowmodel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Data;

/**
 * excel导出 表头类
 * @author lcc
 */
@Data
public class StoreTableRowModel {

    @ColumnWidth(16)
    @ExcelProperty(value = {"序号","填写说明","示例"}, index = 0)
    private String index;

    @ColumnWidth(16)
    @ExcelProperty(value = {"*桌台名称","必填，桌台号最多为10个字，不可与已存在桌台号相同，否则上传失败","A01"}, index = 1)
    private String name;

    @ColumnWidth(16)
    @ExcelProperty(value = {"桌台区域","可填可不填","大厅"}, index = 2)
    private String storeTableAreaName;

    @ColumnWidth(16)
    @ExcelProperty(value = {"*座位数","必填，请勿更改单元格格式","4"}, index = 3)
    private Integer seats;

}
