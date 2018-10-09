package com.news.common.util;


public final class CommonStatic {
	
	private CommonStatic() {}
	
	public static final String DOMAIN = "http://cdn.news.me/";
	
	/**
	 * 通用菜单ROOT
	 */
	public static final String MENU_ROOT = "0";
	public static final String MENU_NAME = "根目录";
	/**
	 * 通用菜单层级
	 */
	public static final int MENU_LEVEL = 0;
	public static final Integer TRUE = 1;
	public static final Integer FALSE = 0;
	public static final String W_LANG = "W_LANG";
	
	public static final String ANSWER_BANK_LIST = "answer/v1/questionBank/list";
	public static final String ANSWER_BANK_OPERAT = "answer/v1/questionBank/operating";
	public static final String ANSWER_ANCHOR_RATE = "answer/anchor/setvrate";
	public static final String ANSWER_COMMAND_NOTICE ="answer/anchor/command";
//	public static final String ACCOUNT_ADVANCE_LIST = "account/advanceList";
//	public static final String ACCOUNT_ADVANCE_STATUS = "account/updateAdvanceStatus";
//	public static final String ACCOUNT_AADVANCE_STATUS = "account/updateAdvanceStatus";
	public static final String ACCOUNT_AADVANCE_HGET = "account/getRedis";
	public static final String ACCOUNT_AADVANCE_HSET = "account/setHRedis";
	
	public static final String PKTHEME_S3_IMAGE = "pk/theme/image/";
	public static final String PKTHEME_S3_VIDEO = "pk/theme/video/";
	
	/**
	 * 人数增幅基数KEY
	 */
	public static final String REDIS_CLIENT_RATE = "client_virtual_num_rate";
	/**
	 * 场次是否开启抽奖
	 */
	public static final String REKEY_QUESTION_BANK_IS_LOTTERY = "question_bank_is_Lottery";
	/**
	 * 答题场次语言列表
	 */
	public static final String REKEY_QUESTION_BANK_LANGUAGE = "question_bank_language_";
	/**
	 * 场次主持人
	 */
	public static final String REKEY_QUESTION_BANK_AUTHOR = "question_bank_author_";
	/**
	 * 场次对应的直播地址
	 */
	public static final String REKEY_BANK_LIVE_URL = "bank_live_";
	
	/**
	 * 活动页配置
	 */
	public static final String REKEY_ACTIVITY_HOME_CONFIG = "activity_home_config";
	
	/**
	 * 主题缓存
	 */
	public static final String REKEY_PK_THEME = "pk_theme_%s";
	public static final String REKEY_PK_THEME_LIST = "pk_theme_list";
	public static final String REKEY_PK_ILLEGAL_VIDEO = "PK_ILLEGAL_VIDEO";			//被举报的视频
	public static final String REKEY_PK_VIDEO_HOT = "video_hot_recommend_ids_%s";	//被推荐视频列表
	public static final String REKEY_PK_VIDEO_HOT_RECORD = "video_hot_record_%s";	//被推荐视频记录 cms用于查询是否被推荐
	
	public static final String PK_VIDEO_AUDIT_SUCCESS = "Congrats! Your video is approved.Please enjoy the competition and good luck!";
	public static final String PK_VIDEO_OBTAINED = "Your video has been removed due to multiple reports of violations";
	public static final String[] PK_VIDEO_AUDTI_FAIL = {
			"Sorry, your video has been rejected，please upload a new one.%s",
			"Sorry, video rejected because the content is not relevant with %s.",
			"Sorry, video rejected because its cover is blurred, fuzzy or not relevant.%s",
			"Sorry, video rejected because of stuttering，fuzzy or soundless.%s",
			"Sorry, video rejected for violating our content requirements.%s",
			"Sorry, video rejected. Please upload video in full screen potrait mode.%s",
			"Sorry, video rejected because it may copy from others.%s",
			"Sorry, video rejected because the length is too short.%s",
			"Sorry, %s challenge has been closed, join new trending challenges!"
		};
}
