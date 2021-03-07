package com.lcc.util.excel.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lanli
 * @date: 2019/7/18 17:59
 * @description:
 */
@Data
public class ExcelListener extends AnalysisEventListener {

  // 自定义用于暂时存储data。
  // 可以通过实例获取该值
  private List<Map<Integer, String>> datas = new ArrayList<>();

  /** 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据 */
  @Override
  public void invoke(Object object, AnalysisContext context) {
    doSomething(object);
  }

  private void doSomething(Object object) {
    if (object instanceof Map) {
      Map<Integer, String> data = (Map<Integer, String>) object;
      data.forEach(
          (index, value) -> {
            data.put(index, StrUtil.trim(replaceSpecialChar(value)));
          });
      datas.add(data);
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    /*
       datas.clear();
       解析结束销毁不用的资源
    */
  }

  /**
   * 对Excel中不可见字符处理
   *
   * @param value 目标字符
   */
  public static String replaceSpecialChar(String value) {
    String temp = "";
    if (value != null) {
      Matcher m = PATTERN.matcher(value);
      temp = m.replaceAll("");
    }
    return temp;
  }

  /** 正则表达式预编译 */
  private static final Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n|\u202C|\u202D|\u202E");

  public static void main(String[] args) {
    //
    System.out.println(replaceSpecialChar("0.01\u202E1"));
  }
}
