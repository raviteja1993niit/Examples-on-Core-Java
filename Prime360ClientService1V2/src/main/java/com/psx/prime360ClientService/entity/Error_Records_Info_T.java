package com.psx.prime360ClientService.entity;

public class Error_Records_Info_T {

	public String customer_no;
	public String error_description;
	public String insert_ts;
	public String psx_batch_id;
	public String record;

	public String getCustomer_no() {
		return customer_no;
	}

	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public String getInsert_ts() {
		return insert_ts;
	}

	public void setInsert_ts(String insert_ts) {
		this.insert_ts = insert_ts;
	}

	public String getPsx_batch_id() {
		return psx_batch_id;
	}

	public void setPsx_batch_id(String psx_batch_id) {
		this.psx_batch_id = psx_batch_id;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "ERROR_RECORDS_INFO_T [customer_no=" + customer_no
				+ ", error_description=" + error_description + ", insert_ts="
				+ insert_ts + ", psx_batch_id=" + psx_batch_id + ", record="
				+ record + "]";
	}

}
