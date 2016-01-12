package com.creal.nestsaler;

public class Constants {
    public static final String SERVER_HOST = "manager.go.wuxian114.com";
    public static final String URL_ACTIVE_ACCOUNT            = "http://" + SERVER_HOST + "/lmk_interface/selleractivation/index.php";
    public static final String URL_LOGIN                     = "http://" + SERVER_HOST + "/lmk_interface/sellerlogin/index.php";
    public static final String URL_GET_ORDER_RECORD          = "http://" + SERVER_HOST + "/lmk_interface/sellerorder/index.php";
    public static final String URL_CHANGE_PWD                = "http://" + SERVER_HOST + "/lmk_interface/cpwd/index.php";

    public final static String KEY_CARD_ID = "card_id";
    public final static String KEY_CARD_NUM = "card_num";
    public final static String KEY_MOBILE = "mobile";
    public final static String KEY_VERIFICATION_CODE = "vcode";
    public final static String KEY_INTEGRAL = "integral";
    public final static String KEY_MONEY = "money";
    public final static String KEY_KEY = "key";
    public final static String KEY_PASSWORD = "password";
    public final static String KEY_QR_CODE = "qr_code";

    public static final String APP_DEFAULT_KEY        = "123456789";
    public static final String APP_BINDING_KEY        = "app_user_binding_key";
    public static final String APP_USER_AUTHORIZED    = "app_user_authorized";
    public static final String APP_USER_PHONE         = "app_user_phone";
    public static final String APP_USER_PWD           = "app_user_pwd";
    public static final String APP_USER_CARD_ID       = "app_user_card_id";
    public static final String APP_USER_CARD_NUM      = "app_user_card_num";

    public static final String APP_ACCOUNT_ACTIVE = "account_active";



    public static final String CACHE_DIR = "/nest";
    //Pagination
    public static final int PAGE_SIZE = 10;
    //Cached Keywords
    public static final int MAX_CACHED_KEYWORD_SIZE = 10;

    public final static String SERVICE_ID_NEWS = "S1116";
}
