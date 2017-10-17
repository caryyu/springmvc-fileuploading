package com.github.caryyu.controller;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MultipartFileForm {

    public static class MultipartFileItem {
        private Integer sort;
        private MultipartFile multipartFile;

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public MultipartFile getMultipartFile() {
            return multipartFile;
        }

        public void setMultipartFile(MultipartFile multipartFile) {
            this.multipartFile = multipartFile;
        }
    }


    private List<MultipartFileItem> multipartFileItems;


    public List<MultipartFileItem> getMultipartFileItems() {
        return multipartFileItems;
    }

    public void setMultipartFileItems(List<MultipartFileItem> multipartFileItems) {
        this.multipartFileItems = multipartFileItems;
    }
}
