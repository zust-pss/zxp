package com.zust.zxp.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.util.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Date;

@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Value("${pic.save.path}")
    private String picSavePath;

    @PostMapping("/upload")
    ResultBean upload(@RequestParam("file") MultipartFile file) {
        Date currentDate = new Date();
        try {
            String savePath =
                    DateUtil.format(currentDate, "yyyy/MM/dd");
            String oriName = file.getOriginalFilename();
            String saveFileName = IdUtil.simpleUUID() + oriName.substring(oriName.lastIndexOf("."));
            File saveFile = new File( picSavePath + savePath, saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);
            return ResultBean.ok(savePath+"/"+saveFileName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.error();
        }

    }

}
