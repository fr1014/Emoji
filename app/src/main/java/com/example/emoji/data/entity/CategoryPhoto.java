package com.example.emoji.data.entity;

import java.util.List;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CategoryPhoto {

    /**
     * totalCategory : 2
     * category : [{"id":1,"type":"photo","tags":"风景","imageURL":"https://cdn.pixabay.com/user/2018/01/12/08-06-25-409_250x250.jpg"},{"id":2,"type":"photo","tags":"人","imageURL":"https://cdn.pixabay.com/user/2019/04/11/22-45-05-994_250x250.jpg"}]
     */

    private int totalCategory;
    private List<CategoryBean> category;

    public int getTotalCategory() {
        return totalCategory;
    }

    public void setTotalCategory(int totalCategory) {
        this.totalCategory = totalCategory;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class CategoryBean {
        /**
         * id : 1
         * type : photo
         * tags : 风景
         * imageURL : https://cdn.pixabay.com/user/2018/01/12/08-06-25-409_250x250.jpg
         */

        private int id;
        private String type;
        private String tags;
        private String imageURL;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }
    }
}
