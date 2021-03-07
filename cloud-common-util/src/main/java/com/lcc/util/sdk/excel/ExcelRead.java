package com.lcc.util.sdk.excel;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lcc
 */
@Api(tags = {"模板"})
@Slf4j
@RestController
@RequestMapping("/sdk")
public class ExcelRead {

//    @Autowired
//    private MsgModelServiceImpl msgModelService;

    @ApiOperation("批量导入 -- 短信模板")
    @PostMapping("/upload/list")
    public void importCategory(MultipartFile file)  {
        InputStream in = null;
        try {
            in = file.getInputStream();
            List<Map<Integer, String>> mapsList = EasyExcelUtil.excelToData(in);
            int size = mapsList.size();

            //表头信息  三行
            List<List<String>> excelHead = new ArrayList<>();
            List<String> rowMessage;
            for (int i = 0; i < 1; i++) {
                Map<Integer, String> map = mapsList.get(i);
                if (CollectionUtil.isNotEmpty(map)) {
                    rowMessage = new LinkedList<>();
                    for (int j = 0; j < 4; j++) {
                        rowMessage.add(map.get(j));
                    }
                    excelHead.add(rowMessage);
                }
            }
            System.out.println(size);
            MsgTemplate msgTemplate ;
            List<MsgTemplate> list = new LinkedList<>();
            for (int i = 1; i < size; i++) {
                Map<Integer, String> map = mapsList.get(i);
                msgTemplate = new MsgTemplate();
                if (CollectionUtil.isNotEmpty(map)) {

                    msgTemplate.setIndex(map.get(0));
                    msgTemplate.setSignature(map.get(1));
                    msgTemplate.setTemplateType(map.get(2));
                    msgTemplate.setContent(map.get(3));
                    msgTemplate.setTrade(map.get(4));
                    msgTemplate.setType(map.get(5));

                    list.add(msgTemplate);
                }
            }
            insertMessage(list);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (ObjectUtil.isNotNull(in)) {
                try {
                    in.close();
                } catch (IOException e) {

                }

            }
        }
    }


    /**
     * 保存到数据库
     * @param list
     */
    private void insertMessage(List<MsgTemplate> list){

//        if (CollectionUtil.isNotEmpty(list)) {
//            Collection<MsgModel> entityList = new LinkedList<>();
//            MsgModel msgModel;
//            for (MsgTemplate msg : list) {
//                msgModel = new MsgModel();
//                BeanUtil.copyProperties(msg,msgModel);
//                //主键
//                msgModel.setMsgModelId("200--0"+msg.getIndex());
//
//                //对应的sn pwd
//                msgModel.setTemplateCode(1);
//                //
//                msgModel.setPlatformType("B");
//
//                msgModel.setTemplateName("SDK备案短信");
//                msgModel.setTemplateType(msg.getTemplateType());
////                msgModel.setTemplateCode(msg.getTemplateType());
//
//                msgModel.setSystemCode("200--0"+msg.getIndex());
//                msgModel.setContent(msg.getContent()+ msg.getSignature());
//                msgModel.setCreateTime(LocalDateTime.now());
//                msgModel.setCreateDate(LocalDateTime.now());
//                entityList.add(msgModel);
//            }
//            msgModelService.saveBatch(entityList, 1000);
//        }


    }
}
