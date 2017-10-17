package com.github.caryyu.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/main")
public class MainController {

    private static final String FILE_STORE_PATH = "/Users/cary/Desktop/fileUploading";

    /**
     * 单文件上传示例
     * @param multipartFile
     * @return
     */
    @RequestMapping(path = "/fileUploading",method = RequestMethod.POST)
    public String fileUploading(
            @RequestParam("multipartFile") MultipartFile multipartFile)
            throws IOException {
        String suffix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fullPath = wrapFullPath(getAutoGenFileName(suffix));
        File target = new File(fullPath);
        target.getParentFile().mkdirs();
        FileCopyUtils.copy(multipartFile.getBytes(),target);
        return fullPath;
    }

    /**
     * 多文件上传示例(必须全量，且文件有关业务顺序服务端无法有效控制）
     * @param multipartFiles
     * @return
     */
    @RequestMapping(path = "/multiFileUploadingUnableSorting",method = RequestMethod.POST)
    public List<String> multiFileUploadingUnableSorting(
            @RequestParam("multipartFiles") MultipartFile[] multipartFiles)
            throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            String suffix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String fullPath = wrapFullPath(getAutoGenFileName(suffix));
            File target = new File(fullPath);
            target.getParentFile().mkdirs();
            FileCopyUtils.copy(multipartFile.getBytes(),target);
            fileNames.add(fullPath);
        }
        return fileNames;
    }

    /**
     * 多文件上传示例(可选分量，且文件有关业务顺序服务端可以有效控制）
     * @param multipartFileForm
     * @return
     */
    @RequestMapping(path = "/multiFileUploadingAbleSorting",method = RequestMethod.POST)
    public List<String> multiFileUploadingAbleSorting(MultipartFileForm multipartFileForm)
            throws IOException {
        List<String> lineOfOutput = new ArrayList<>();
        for (MultipartFileForm.MultipartFileItem multipartFileItem : multipartFileForm.getMultipartFileItems()) {
            Integer sort = multipartFileItem.getSort();
            MultipartFile multipartFile = multipartFileItem.getMultipartFile();
            String suffix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String fullPath = wrapFullPath(getAutoGenFileName(suffix));
            File target = new File(fullPath);
            target.getParentFile().mkdirs();
            FileCopyUtils.copy(multipartFile.getBytes(),target);
            lineOfOutput.add( sort + ": " + fullPath);
        }
        return lineOfOutput;
    }

    /**
     * 按时间自动生成文件名
     * @param suffix
     * @return
     */
    private String getAutoGenFileName(String suffix) {
        return String.valueOf(new Date().getTime()) + "." + suffix;
    }

    /**
     * 包装成全路径形式
     * @param filename
     * @return
     */
    private String wrapFullPath(String filename) {
        return FILE_STORE_PATH + File.separator + filename;
    }
}