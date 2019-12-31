package com.posidex.eod.impl;


public class PsxDataSourceMSTDTO
{
	private String	SOURCE_SYS_ID	= null;
	private String	SOURCE_NAME		= null;
	private String	ALLOCATE_UCIC	= null;
	private long	DAYS_TO_RETAIN;
	private long	PRIORITY;

	public String getSOURCE_SYS_ID()
	{
		return SOURCE_SYS_ID;
	}

	public void setSOURCE_SYS_ID( String sOURCESYSID )
	{
		SOURCE_SYS_ID = sOURCESYSID;
	}

	public String getSOURCE_NAME()
	{
		return SOURCE_NAME;
	}

	public void setSOURCE_NAME( String sOURCENAME )
	{
		SOURCE_NAME = sOURCENAME;
	}

	public String getALLOCATE_UCIC()
	{
		return ALLOCATE_UCIC;
	}

	public void setALLOCATE_UCIC( String aLLOCATEUCIC )
	{
		ALLOCATE_UCIC = aLLOCATEUCIC;
	}

	public long getDAYS_TO_RETAIN()
	{
		return DAYS_TO_RETAIN;
	}

	public void setDAYS_TO_RETAIN( long dAYSTORETAIN )
	{
		DAYS_TO_RETAIN = dAYSTORETAIN;
	}

	public long getPRIORITY()
	{
		return PRIORITY;
	}

	public void setPRIORITY( long pRIORITY )
	{
		PRIORITY = pRIORITY;
	}

}
