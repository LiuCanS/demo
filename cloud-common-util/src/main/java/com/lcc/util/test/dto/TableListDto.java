package com.lcc.util.test.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 刘灿灿
 */
@Data
public class TableListDto {

    List<List<String>> excelHead;

    int failCount;

    int successCount;

    List<StoreTableDto> storeTableDtoList;
}
