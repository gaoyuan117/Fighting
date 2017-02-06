package com.xiangying.fighting.utils;


import org.apache.http.client.CookieStore;

/**
 * Created by HJ on 2015/12/15.
 */
public class NetworkTools {
    //登陆cookie
    public static CookieStore cookieStore = null;

    //用户账户密码
    public static String username = "";
    public static String password = "";


    //  public static final String BASE_URL = "http://192.168.1.33:81/zdb/Api/";
    public static final String BASE_URL = "http://112.74.14.50/Api/";
    public static final String BASE_URL_PICTURE = "http://139.129.132.213:1280/";

    /**
     * 登录注册
     */
    public static final String GET_VERIFY_CODE = BASE_URL + "Public/verify";//获取手机验证码
    public static final String REGIST = BASE_URL + "User/register";//注册
    public static final String LOGIN = BASE_URL + "User/login";//登录
    public static final String FIND_PASSWORD = BASE_URL + "User/Forgotten";//找回密码
    public static final String SET_PASSWORD = BASE_URL + "User/resetPassword";//设置密码

    /**
     * 好友相关
     */
    public static final String APPLY_ADD_FRIEND = BASE_URL + "User/addFriend";//申请添加好友
    public static final String DELETE_FRIEND = BASE_URL + "User/delFriend";//删除好友
    public static final String SEARCH_FRIEND = BASE_URL + "User/index";//搜索用户
    public static final String OTHER_USERINFO = BASE_URL + "User/otherInfo";//获取其他用户信息
    public static final String MY_FRIEND_AND_GROUP_LIST = BASE_URL + "User/userList";//获取好友和群组列表

    //////////////////好友
    public static final String HX_FRIEND_LIST = BASE_URL + "Hx/showFriends";
    public static final String HX_FIND_FRIEND = BASE_URL + "Hx/findFriend";
    public static final String HX_FRIEND_INFO = BASE_URL + "Hx/checkFriend";
    public static final String HX_ADD_FRIEND = BASE_URL + "Hx/addFriend";
    public static final String HX_DELETE_FRIEND = BASE_URL + "Hx/deleteFriend";
    public static final String HX_TOP = BASE_URL + "Hx/friendTop";
    public static final String HX_NO_TOP = BASE_URL + "Hx/removeFriendTop";
    public static final String HX_FRIEND_GROUP = BASE_URL + "Hx/getFriendGroup"; //好友分组
    public static final String HX_FRIEND_ADD_TO_GROUP = BASE_URL + "Hx/friendAddGroup"; //好友分组
    public static final String HX_ADD_TEAM = BASE_URL + "Hx/addGroup";
    public static final String HX_ISFIRST = BASE_URL + "Hx/hasGroup";

    //群
    public static final String HX_ADD_GROUP = BASE_URL + "Hx/createGroup";
    public static final String HX_GROUP_LIST = BASE_URL + "Hx/getUserGroup";
    public static final String HX_GROUP_DETAIL = BASE_URL + "Hx/getGroupDetail";
    public static final String HX_GROUP_ADDGROUPMEMBERS = BASE_URL + "Hx/addGroupMembers";
    public static final String HX_GROUP_ADDGROUPMEMBER = BASE_URL + "Hx/hasGroup";
    public static final String HX_RE_GROUP_NAME = BASE_URL + "Hx/reGroupname";
    public static final String HX_EXIT_GROUP = BASE_URL + "Hx/leaveGroup";
    public static final String HX_GET_GROUP_USER = BASE_URL + "/Hx/getGroupUsers";
    public static final String HX_RENAME = BASE_URL + "Hx/rename";
    public static final String HX_GET_DETAIL = BASE_URL + "Hx/getTeamname";
    public static final String GET_NUM = BASE_URL + "/Hx/getNumber";
    public static final String NUM_MONEY = BASE_URL + "/Hx/getMoney";

    //投诉
    public static final String HX_REPORT_FRIEND = BASE_URL + "Hx/friendComplaint";
    public static final String HX_REPORT_GROUP = BASE_URL + "Hx/groupComplaint";


    /**
     * 正能量
     */
    public static final String SHARE_LIST = BASE_URL + "Energy/energyList"; //获取分享列表
    public static final String SHARE_ADD = BASE_URL + "Energy/add";//发送
    public static final String SHARE_ZAN = BASE_URL + "Energy/zan";//赞说说
    public static final String SHARE_COMMENT = BASE_URL + "Energy/addComment";//
    public static final String SHARE_DELETECOMMENT = BASE_URL + "Energy/deleteComment";//删除评论
    public static final String SHARE_DELETEENERGY = BASE_URL + "Energy/delete";//删除正能量
    public static final String SHARE_COMMENT_DETAIL = BASE_URL + "Energy/getCommentDetail";//删除正能量
    public static final String SHARE_COMPLAINT = BASE_URL + "Energy/complaint";//投诉

    public static final String UPLOAD_PICTURE = BASE_URL + "File/uploadPicture";
    public static final String UPLOAD_Image = BASE_URL + "File/uploadPicture";
    public static final String UPLOAD_Image_app = BASE_URL + "File/uploadPictureApp";
    public static final String UPLOAD_Movie = BASE_URL + "File/uploadMovie";
    public static final String HAS_RENT = BASE_URL + "/Rent/hasRent";


    /**
     * 资讯
     */
    public static final String NEWS_TITLE = BASE_URL + "News/getTitle";  //栏目
    public static final String NEWS_LIST = BASE_URL + "News/getList";   //列表
    public static final String NEWS_DETAIL = BASE_URL + "News/getDetail";  //详情
    public static final String NEWS_Comment = BASE_URL + "News/getComment";  //评论
    public static final String NEWS_Comment_add = BASE_URL + "News/addComment";  //添加评论
    public static final String NEWS_Comment_delete = BASE_URL + "News/deleteComment";  //删除评论

    /**
     * 个人日记相册视频
     */
    public static final String ME_NOTE = BASE_URL + "Self/diaryIndex";
    public static final String ME_NOTE_ADD = BASE_URL + "Self/diaryAdd";
    public static final String ME_NOTE_DELETE = BASE_URL + "Self/diaryDelete";
    public static final String ME_THUMB = BASE_URL + "Self/thumbIndex";
    public static final String ME_THUMB_ADD = BASE_URL + "Self/thumbAdd";
    public static final String ME_THUMB_DELETE = BASE_URL + "Self/thumbDelete";
    public static final String ME_VIDEO = BASE_URL + "Self/movieIndex";
    public static final String ME_VIDEO_ADD = BASE_URL + "Self/movieAdd";
    public static final String ME_CIDEO_DELETE = BASE_URL + "Self/movieDelete";
    public static final String ME_ALL_NOTE = BASE_URL + "Self/Index";
    public static final String ME_ALL_NOTE_ADD = BASE_URL + "Self/add";
    public static final String ME_ALL_NOTE_DELETE = BASE_URL + "Self/delete";
    public static final String AUTH = BASE_URL + "Self/setAuth";
    public static final String HAS_AUTH = BASE_URL + "Self/hasAuth";


    /**
     * 用户信息
     */
    //获取本人的用户信息
    public static final String GET_USER_INFO_SELF = BASE_URL + "User/BaseInfo";
    public static final String Edit_user_info = BASE_URL + "User/completeInfo";
    //我的资产
    public static final String SELF_PROPERTY = BASE_URL + "/User/myAssets";
    public static final String SELF_BANK = BASE_URL + "/User/bank";
    public static final String SELF_BANK_ADD = BASE_URL + "/User/bank";
    public static final String SELE_ADD_BANK = BASE_URL + "/User/addBankCard";
    public static final String SELE_BANK_LIST = BASE_URL + "/User/bankcardList";
    public static final String SELE_BANK_TIXIAN = BASE_URL + "/User/withdraw";
    public static final String SHOP_TX = BASE_URL + "/User/withdraw1";
    public static final String SELE_BANK_TRANSFER = BASE_URL + "/User/transfer";
    public static final String RECHARGE_ORDER = BASE_URL + "User/rechargeOrder";
    public static final String NOTIFY = BASE_URL + "Api/notify";
    public static final String RENZHENGS = BASE_URL + "/User/identify";
    public static final String HASIDENTIFY = BASE_URL + "/User/hasIdentify";


    //更新用户昵称
    public static final String UPDATE_USER_NICKNAME = BASE_URL + "User/editNickname";
    //更新用户头像
    public static final String UPDATE_USER_HEAD = BASE_URL + "User/updateAvatar";

    //更新用户信息
    public static final String UPDATE_USER_INFO = BASE_URL + "User/update";

    /**
     * 找工作
     */
    public static final String FIND_JOB_INDEX = BASE_URL + "Work/index"; //找工作首页和搜索
    public static final String JOB_DETAIL = BASE_URL + "work/getWorkDetail";  //岗位详情
    public static final String IS_CREATE_COMPANY = BASE_URL + "Work/is_create"; //是否创建过企业
    public static final String ADD_NEW_JOB = BASE_URL + "Work/add"; //发布
    public static final String EDIT_JOB = BASE_URL + "Work/edit"; //修改
    public static final String DELETE_WORK = BASE_URL + "Work/deleteMyWork"; //删除岗位
    public static final String JOB_COMPANY_ADD = BASE_URL + "Work/companyAdd"; //完善企业信息
    public static final String JOB_WORK_MYWORD = BASE_URL + "Work/myword"; //我发布的岗位
    public static final String JOB_HOTS_RECOMENT = BASE_URL + "Work/getList"; //热门职位推荐
    public static final String JOB_MY_COMPANY = BASE_URL + "Work/myCompanyDetail"; //我的企业


    /**
     * 找出组
     */
    public static final String FIND_RENT_INDEX = BASE_URL + "Rent/rentList"; //出租列表和搜索
    public static final String RENT_DETAIL = BASE_URL + "Rent/Info"; //查看出租
    public static final String RENT_NEW = BASE_URL + "Rent/add"; //发布和编辑出租信息
    public static final String RENT_EDIT = BASE_URL + "Rent/edit"; //发布和编辑出租信息
    public static final String RENT_MY_RENT = BASE_URL + "Rent/myrent"; //发布和编辑出租信息
    public static final String RENT_MY_RENT_DELETE = BASE_URL + "Rent/delete"; //发布和编辑出租信息

    /**
     * 结婚吧
     */
    public static final String API_MERRY_INDEX = BASE_URL + "Marry/index";//结婚首页
    public static final String API_MERRY_INFO = BASE_URL + "Marry/info";//结婚个人资料
    public static final String API_MERRY_SELECT = BASE_URL + "Marry/select";//结婚吧搜索
    public static final String API_MERRY_ADD = BASE_URL + "Marry/add";//结婚吧添加个人资料

    /**
     * 军团分队
     */
    public static final String API_USER_USERLIST = BASE_URL + "User/userList";//获取好友列表和群

    public static final String API_ELEMENT_LIST = BASE_URL + "Element/elementList";//获取分队列表
    public static final String API_ELEMENT_ADD = BASE_URL + "Element/add";//添加分队
    public static final String API_ELEMENT_UPDATE = BASE_URL + "Element/update";//修改分队
    public static final String API_ELEMENT_ELEMENT = BASE_URL + "Element/delete";//删除分队
    public static final String API_USER_ELEMENT = BASE_URL + "User/element";//将好友移动到另一分队

    /**
     * 公益活动
     */
    public static final String API_WELFARE_INDEX = BASE_URL + "Welfare/index";//公益列表
    public static final String API_WELFARE_DONATION = BASE_URL + "Welfare/donation";//公益捐款
    public static final String API_WELFARE_MYDONATION = BASE_URL + "Welfare/myDonation";//我的公益活动捐款
    public static final String API_WELFARE_INFO = BASE_URL + "Welfare/info";//公益详情


    public static final String ADDADVISE = BASE_URL + "Advise/addAdvise";//公益详情

}
