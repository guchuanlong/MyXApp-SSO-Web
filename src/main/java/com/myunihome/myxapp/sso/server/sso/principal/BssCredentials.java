package com.myunihome.myxapp.sso.server.sso.principal;

import java.sql.Timestamp;

import org.jasig.cas.authentication.RememberMeUsernamePasswordCredential;

import com.myunihome.myxapp.sso.api.stafflogin.param.StaffLoginVo;
/**
 * 建立日期 : Mar 19, 2009 9:49:35 PM<br>
 * 作者 : yaoq<br>
 * 模块 : <br>
 * 描述 : <br>
 * 修改历史: 序号 日期 修改人 修改原因 <br>
 * 1 <br>
 * 2 <br>
 */
public class BssCredentials extends RememberMeUsernamePasswordCredential {
    private static final long serialVersionUID = -8147635836938729264L;

    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 租户名称
     */
    private String tenantName;
    
    //验证码
	private String captchaResponse;


	//员工相关信息
	private long staffnoId;
    private String staffId;
    private String staffNo;
    private String staffName;
    private String staffPasswd;
    private String state;
	private Timestamp activeTime;
	private Timestamp inactiveTime;
    
    private StaffLoginVo staffLoginVo;
    

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getStaffNo() {
		return staffNo;
	}


	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}


	public String getStaffName() {
		return staffName;
	}


	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}



	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}


	public Timestamp getInactiveTime() {
		return this.inactiveTime == null?null:(Timestamp)this.inactiveTime.clone();
	}

	public void setInactiveTime(Timestamp inactiveTime) {
		this.inactiveTime = inactiveTime == null?null:(Timestamp)inactiveTime.clone();
	}


	public Timestamp getActiveTime() {
		return this.activeTime == null?null:(Timestamp)this.activeTime.clone();
	}

	public void setActiveTime(Timestamp activeTime) {
		this.activeTime = activeTime == null?null:(Timestamp)activeTime.clone();
	}

	public StaffLoginVo getStaffLoginVo() {
		return staffLoginVo;
	}

	public void setStaffLoginVo(StaffLoginVo staffLoginVo) {
		this.staffLoginVo = staffLoginVo;
	}

	public long getStaffnoId() {
		return staffnoId;
	}

	public void setStaffnoId(long staffnoId) {
		this.staffnoId = staffnoId;
	}

	public String getStaffPasswd() {
		return staffPasswd;
	}

	public void setStaffPasswd(String staffPasswd) {
		this.staffPasswd = staffPasswd;
	}

}
