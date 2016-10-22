package com.myunihome.myxapp.sso.server.sso.web.service;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.myunihome.myxapp.paas.util.Md5Encoder;
import com.myunihome.myxapp.sso.api.stafflogin.interfaces.IStaffLoginSV;
import com.myunihome.myxapp.sso.api.stafflogin.param.StaffLoginVo;
import com.myunihome.myxapp.sso.server.sso.common.exception.BusinessException;
import com.myunihome.myxapp.sso.server.sso.common.exception.SystemException;
import com.myunihome.myxapp.sso.server.sso.constants.Constants;
import com.myunihome.myxapp.sso.server.sso.principal.BssCredentials;
import com.myunihome.myxapp.utils.util.DubboConsumerFactory;

@Service
public class LoadSessionService {

	//public static final String OPERLIST_KEY="operList";
	private static final Logger LOG =  Logger.getLogger(LoadSessionService.class);
	
	/**
	 * 加载员工信息及其对应的操作员列表信息
	 * @param request
	 * @param servletResponse
	 * @param bssCredentials
	 * @throws BusinessException
	 */
	public void loadStaff(ServletRequest request, ServletResponse servletResponse,
			BssCredentials bssCredentials) throws BusinessException {
		try {
			/**1、查询员工登录信息 */
			repackStaff(bssCredentials);
		} catch (Exception e) {
			LOG.error("加载员工登录信息异常",e);
			throw new BusinessException(e.getMessage());
		} 
	}

	public StaffLoginVo repackStaff(BssCredentials bssCredentials) throws SystemException {
		StaffLoginVo staffLoginVo = null;
	        if(bssCredentials!=null){
	        	staffLoginVo = bssCredentials.getStaffLoginVo();
	        	if(staffLoginVo == null){
	        		//获取登录员工信息
	        		LOG.info("------------------------------开始调用接口获取员工【"+bssCredentials.getUsername()+"】信息------------------------------");
		        	try {
						
		        		staffLoginVo = DubboConsumerFactory.getService(IStaffLoginSV.class).getStaffLoginBystaffno(bssCredentials.getUsername());
						
					} catch (Exception e1) {
						LOG.error("------------------调用员工登录接口异常---------------------",e1);
						throw new SystemException("获得员工信息失败，请联系系统管理员");
					}
		        	LOG.info("------------------------------调用接口获取员工【"+bssCredentials.getUsername()+"】信息结束------------------------------");
		            if(staffLoginVo == null ){
		            	//账号不存在
		            	LOG.error("------------------------------账号【"+bssCredentials.getUsername()+"】不存在------------------------------");
		            	throw new SystemException("账号【"+bssCredentials.getUsername()+"】不存在");
		            }else{
		            	//账号存在
		            	bssCredentials.setStaffLoginVo(staffLoginVo);
		            	//密码错误
		            	if(!validatePwd(bssCredentials.getPassword(),staffLoginVo.getStaffPasswd(),Constants.MYXAPP_SALT_KEY)){
		            		LOG.error("------------------------------账号【"+bssCredentials.getUsername()+"】密码错误------------------------------");
			            	throw new SystemException("账号【"+bssCredentials.getUsername()+"】密码错误");
		            	}
		            	//TODO 还需判断账号的生效时间、失效时间、状态等。
		            	
		            	
		            	try {
		            		//登录成功，拷贝员工信息到凭证
							BeanUtils.copyProperties(bssCredentials, staffLoginVo);
						} catch (IllegalAccessException | InvocationTargetException e) {
							LOG.warn("拷贝员工信息至认证凭证失败",e);
							throw new SystemException("拷贝账号【"+bssCredentials.getUsername()+"】到认证凭证失败");
						}//end catch
		            	LOG.info("------------------------------获取员工【"+bssCredentials.getUsername()+"】信息成功------------------------------");
		            }//end if
	        	}
	        	
	        	
	        }
	        return staffLoginVo;
	}
	
	 private boolean validatePwd(String pwdInput, String staffPasswd,String saltKey) {
	    	
	    	if(pwdInput == null || pwdInput.trim().length() == 0){
	    		return false;
	    	}
	    	String encodePwd = pwdInput;
	        String encodedPwd2 = Md5Encoder.encodePassword(saltKey+staffPasswd);
	        return encodePwd.equals(encodedPwd2);
		}


}
