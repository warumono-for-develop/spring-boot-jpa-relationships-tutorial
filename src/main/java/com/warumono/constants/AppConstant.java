package com.warumono.constants;

public interface AppConstant
{
	public static final class Package
	{
		public static final String BASE = "com.warumono";
		public static final String ENTITIES = BASE + ".entities";
		public static final String REPOSITORIES = BASE + ".repositories";
	}
	
	public static final class Swagger
	{
		/*
		string (this includes dates and files)
		number
		integer
		boolean
		array
		object
		*/
		
		public static final class DataType
		{
			public static final String STRING = "string";
			public static final String DATE = "string";
			public static final String FILE = "string";
			public static final String NUMBER = "number";
			public static final String INTEGER = "integer";
			public static final String BOOOLEAN = "boolean";
			public static final String ARRAY = "array";
			public static final String OBJECT = "object";
		}
		
		public static final class ParamType
		{
			public static final String PATH = "path";
			public static final String QUERY = "query";
			public static final String HEADER = "header";
			public static final String COOKIE = "cookie";
		}
		
		public static final class Pagination
		{
			public static final String PAGE = "page";
			public static final String SIZE = "size";
			public static final String LIMIT = "limit";
			public static final String OFFSET = "offset";
			public static final String SORT = "sort";
			public static final String ORDER = "order";
		}
	}
	
	public static final class RestfulApi
	{
		public static final class Prefix
		{
			public static final String TEST = "test";
			public static final String APP = "app";
		}
	}
}
