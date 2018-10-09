package com.news.common.datasource;

public enum DataSourceType {
	
	SOURCE_LOCAL{
		@Override
		public String toString() {
			return "ds_local";
		}
	}
}
