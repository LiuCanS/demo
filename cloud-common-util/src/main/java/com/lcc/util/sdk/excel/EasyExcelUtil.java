package com.lcc.util.sdk.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: LL
 * @date: 2019/10/9 16:20
 * @description: easy-excel工具类
 */
public class EasyExcelUtil {

  /**
   * 导出excel
   *
   * @param out 输出流
   * @param columns 表头
   * @param data 数据
   * @return 导出excel结果
   */
  public static Boolean exportExcel(
      OutputStream out, List<List<String>> columns, List<List<String>> data) {
    // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
    HorizontalCellStyleStrategy horizontalCellStyleStrategy =
        new HorizontalCellStyleStrategy(getHeadWriteCellStyle(), getContentWriteCellStyle());
    EasyExcel.write(out)
        .head(columns)
        .registerWriteHandler(horizontalCellStyleStrategy)
        .sheet(1)
        .doWrite(data);
    return true;
  }

    public static void writeExcel(String fileName, List<List<String>> header, List<List<String>> body) {
        EasyExcel.write(fileName).head(header).sheet("qrcode").doWrite(body);
    }

  /**
   * 使用easyexcel将excel转为数据
   *
   * @param in
   * @return List<List> 类型
   */
  public static List<Map<Integer,String>> excelToData(InputStream in) {
    ExcelListener excelListener = new ExcelListener();
    EasyExcel.read(in).sheet().headRowNumber(0).registerReadListener(excelListener).doReadSync();
    return excelListener.getDatas();
  }

  /**
   * 动态生成表头数据
   *
   * @param head 表头
   * @param remakeHead 备注栏
   * @return 表头
   */
  public static List<List<String>> createHead(List<String> head, List<String> remakeHead) {
    List<List<String>> headList = new ArrayList<List<String>>();
    List<String> headTemp;
    for (String headString : head) {
      headTemp = new ArrayList<>();
      if (!CollectionUtils.isEmpty(remakeHead)) {
        headTemp.addAll(remakeHead);
      }
      headTemp.add(headString);
      headList.add(headTemp);
    }
    return headList;
  }

  /**
   * 获取excel内容策略
   *
   * @return
   */
  private static WriteCellStyle getContentWriteCellStyle() {
    // 内容的策略
    WriteCellStyle contentWriteCellStyle = getHeadWriteCellStyle();
    // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了
    // FillPatternType所以可以不指定
    contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

    // 背景设置为黄色
    contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

    return contentWriteCellStyle;
  }

  /**
   * 获取excel头的策略
   *
   * @return
   */
  public static WriteCellStyle getHeadWriteCellStyle() {
    // 头的策略
    WriteCellStyle headWriteCellStyle = new WriteCellStyle();
    WriteFont headWriteFont = new WriteFont();
    // 字体大小
    headWriteFont.setFontHeightInPoints((short) 12);
    headWriteFont.setBold(false);
    headWriteFont.setFontName("楷体");
    headWriteCellStyle.setWriteFont(headWriteFont);

    // 背景设置为黄色
    headWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

    // 设置边框
    headWriteCellStyle.setBorderTop(BorderStyle.THIN);
    headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
    headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
    headWriteCellStyle.setBorderRight(BorderStyle.THIN);

    //设置水平左对齐
    headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);

    return headWriteCellStyle;
  }

  public static void setResponseHeaderForExcel(String sheetName, HttpServletResponse response) throws UnsupportedEncodingException {
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(sheetName+ ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
  }
}
