package com.pt.lib_common.constants;

public interface HttpConstant {
    String BASE_URL = "http://120.76.137.162:18081";
    String STS_SERVER = "http://120.76.137.162:18081/oss/get_token";
    String END_POINT = "oss-cn-shenzhen.aliyuncs.com";
    String API_LOGIN_SMS_URL = "/login/login_sms";
    String API_LOGOUT_URL = "/login/logout";
    String API_SEND_SMS_URL = "/login/send_sms_code";
    String API_GET_AD = "/ad/get_ad";
    String API_HOME_CATE = "/cate/home_page_cate";
    String API_HOME_PAGE = "/goods/home_page_promote";
    String API_SEARCH = "/goods/search_goods";
    String API_ORDER_ALL = "/order/all_page";
    String API_ORDER_BUY = "/order/buy_page";
    String API_ORDER_SALE = "/order/sale_page";
    String API_ORDER_MONEY = "/order/funder_page";
    String API_CAN_SALE = "/user/can_sale";
    String API_USER_FUND = "/user/fund";
    String API_APPAY_SALE = "/user/apply_sale";
    String API_MONEY_APPLY = "/user/apply_funder";
    String API_ADD_CATE_SEARCH = "/cate/goods_page_cate";
    String API_CREATE_GOODS = "/goods/create";
    String API_PUBLISH_GOODS = "/goods/my_goods";
    String API_FUND_SIDE_LIST = "/user/get_funder";
    String API_CATEGORY_LIST = "/goods/search_goods";
    String API_GOODS_DETAIL = "/goods/get_goods";
    String API_OFF_SHELF = "/goods/off";
    String API_ON_SHELF = "/goods/release";
    String API_DELETE_GOODS = "/goods/del";
    String API_PHONE_USER = "/order/get_last_person";
    String API_SEEK_REQUEST = "/order/create_seek_order";
    String API_SALE_REQUEST = "/order/create_sale_order";
}
