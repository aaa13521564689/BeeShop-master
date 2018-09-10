package com.beeshop.beeshop.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class GoodBean1 implements Serializable {

    private   int code,time;
    private String msg;
    private int allMoney;
    private int allCount;
    private boolean isAllSelect;

    private ArrayList<Data>  data;

//    public GoodBean1(int code, int time, String msg, int allMoney, int allCount, boolean isAllSelect, List<Data> list) {
//        this.code = code;
//        this.time = time;
//        this.msg = msg;
//        this.allMoney = allMoney;
//        this.allCount = allCount;
//        this.isAllSelect = isAllSelect;
//        this.list = list;
//    }
//
//    public GoodBean1() {
//        super();
//    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(int allMoney) {
        this.allMoney = allMoney;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public boolean isAllSelect() {
        return isAllSelect;
    }

    public void setAllSelect(boolean allSelect) {
        isAllSelect = allSelect;
    }

    public ArrayList<Data> getdata() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data implements Serializable{

        private int id;
        private String address;
        private boolean isSelected;

        private ArrayList<GoodDetail> goodDetail;

//        public Data(int id, String address, boolean isSelected, List<GoodDetail> goodDetails) {
//            this.id = id;
//            this.address = address;
//            this.isSelected = isSelected;
//            this.goodDetails = goodDetails;
//        }
//
//        public Data() {
//            super();
//        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public ArrayList<GoodDetail> getGoodDetail() {
            return goodDetail;
        }

        public void setGoodDetail(ArrayList<GoodDetail> goodDetail) {
            this.goodDetail = goodDetail;
        }

        public static  class GoodDetail implements Serializable{

            private   int id,user_id,product_id,shop_id,num,status,create_time;
            private String product_title,cover,price,unit;
            private     boolean isEdit,isSelected;

//            public GoodDetail(int id, int user_id, int product_id, int shop_id, int num, int status, int create_time, String product_title, String cover, String price, String unit, boolean isEdit, boolean isSelected) {
//                this.id = id;
//                this.user_id = user_id;
//                this.product_id = product_id;
//                this.shop_id = shop_id;
//                this.num = num;
//                this.status = status;
//                this.create_time = create_time;
//                this.product_title = product_title;
//                this.cover = cover;
//                this.price = price;
//                this.unit = unit;
//                this.isEdit = isEdit;
//                this.isSelected = isSelected;
//            }
//
//            public GoodDetail() {
//                super();
//            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public boolean isEdit() {
                return isEdit;
            }

            public void setEdit(boolean edit) {
                isEdit = edit;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }
        }
    }
}
