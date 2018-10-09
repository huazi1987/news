package com.news.cms.common.model;

import java.io.Serializable;

public class LoggingEvent implements Serializable{
	
	/**
	 * @Description:
	 * @author wanghz
	 * @date 2018年9月7日
	*/
	private static final long serialVersionUID = 1L;
	private Long timestmp;
	private String formattedMessage;
	private String loggerName;
	private String levelString;
	private String threadName;
	private Integer referenceFlag;
	private String arg0;
	private String arg1;
	private String arg2;
	private String arg3;
	private String callerFilename;
	private String callerClass;
	private String callerMethod;
	private String callerLine;
	private Long eventId;
	
	public LoggingEvent() {
	}

	public Long getTimestmp() {
		return timestmp;
	}

	public String getFormattedMessage() {
		return formattedMessage;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public String getLevelString() {
		return levelString;
	}

	public String getThreadName() {
		return threadName;
	}

	public Integer getReferenceFlag() {
		return referenceFlag;
	}

	public String getArg0() {
		return arg0;
	}

	public String getArg1() {
		return arg1;
	}

	public String getArg2() {
		return arg2;
	}

	public String getArg3() {
		return arg3;
	}

	public String getCallerFilename() {
		return callerFilename;
	}

	public String getCallerClass() {
		return callerClass;
	}

	public String getCallerMethod() {
		return callerMethod;
	}

	public String getCallerLine() {
		return callerLine;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setTimestmp(Long timestmp) {
		this.timestmp = timestmp;
	}

	public void setFormattedMessage(String formattedMessage) {
		this.formattedMessage = formattedMessage;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public void setReferenceFlag(Integer referenceFlag) {
		this.referenceFlag = referenceFlag;
	}

	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}

	public void setCallerFilename(String callerFilename) {
		this.callerFilename = callerFilename;
	}

	public void setCallerClass(String callerClass) {
		this.callerClass = callerClass;
	}

	public void setCallerMethod(String callerMethod) {
		this.callerMethod = callerMethod;
	}

	public void setCallerLine(String callerLine) {
		this.callerLine = callerLine;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
}
