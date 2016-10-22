package com.myunihome.myxapp.sso.server.sso.handler;

import java.security.GeneralSecurityException;
import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.login.CredentialException;
import javax.security.auth.login.LoginException;
import javax.validation.constraints.NotNull;

import org.jasig.cas.Message;
import org.jasig.cas.authentication.BasicCredentialMetaData;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.handler.NoOpPrincipalNameTransformer;
import org.jasig.cas.authentication.handler.PasswordEncoder;
import org.jasig.cas.authentication.handler.PlainTextPasswordEncoder;
import org.jasig.cas.authentication.handler.PrincipalNameTransformer;
import org.jasig.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.authentication.support.PasswordPolicyConfiguration;
import org.springframework.util.StringUtils;

import com.myunihome.myxapp.sso.api.stafflogin.param.StaffLoginVo;
import com.myunihome.myxapp.sso.server.sso.common.exception.PasswordIsNullException;
import com.myunihome.myxapp.sso.server.sso.common.exception.SystemBusyException;
import com.myunihome.myxapp.sso.server.sso.common.exception.SystemException;
import com.myunihome.myxapp.sso.server.sso.common.exception.TenantIdIsNullException;
import com.myunihome.myxapp.sso.server.sso.common.exception.UsernameIsNullException;
import com.myunihome.myxapp.sso.server.sso.principal.BssCredentials;
import com.myunihome.myxapp.sso.server.sso.web.service.LoadSessionService;

public final class BssCredentialsAuthencationHandler extends AbstractPreAndPostProcessingAuthenticationHandler{

	@Resource
	private LoadSessionService loadSessionService;
	@NotNull
	private PasswordEncoder passwordEncoder;

	@NotNull
	private PrincipalNameTransformer principalNameTransformer;
	private PasswordPolicyConfiguration passwordPolicyConfiguration;
	
	public BssCredentialsAuthencationHandler(){
		this.passwordEncoder = new PlainTextPasswordEncoder();
		this.principalNameTransformer = new NoOpPrincipalNameTransformer();
	}
	@Override
	public boolean supports(Credential credentials) {
		return credentials!=null&&(BssCredentials.class.isAssignableFrom(credentials.getClass()));
	}

	@Override
	protected HandlerResult doAuthentication(final Credential credentials)
			throws GeneralSecurityException, PreventedException {
		logger.info("--------------------开始认证用户凭证credentials--------------------");
		if(credentials == null){
			logger.info("--------------------用户凭证credentials为空--------------------");
			throw new LoginException("Credentials is null");
		}
		BssCredentials bssCredentials = (BssCredentials) credentials;
		final String username = bssCredentials.getUsername();
		final String pwd = bssCredentials.getPassword();
		
		if(StringUtils.hasText(username)&&StringUtils.hasText(pwd)){
			StaffLoginVo staff = null;
			try {
				staff = loadSessionService.repackStaff(bssCredentials);
			} catch (SystemException e) {
				logger.error("验证员工登录失败",e);
				throw new SystemBusyException();
			}
			if(staff == null){
				throw new CredentialException("用户名或密码错误");
			}
			logger.info("用户 [" + username + "] 认证成功。");
            return creatHandlerResult(bssCredentials, new SimplePrincipal(username),
    				null);
		}else{
			logger.error("用户 [" + username + "] 认证失败。");
			if(!StringUtils.hasText(username)){
				throw new UsernameIsNullException();
			}else if(!StringUtils.hasText(pwd)){
				throw new PasswordIsNullException();
			}else{
				throw new TenantIdIsNullException();
			}
		}
	}

	private HandlerResult creatHandlerResult(BssCredentials bssCredentials,
			SimplePrincipal simplePrincipal, List<Message> warnings) {
		return new HandlerResult(this, new BasicCredentialMetaData(bssCredentials), simplePrincipal, warnings);
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PrincipalNameTransformer getPrincipalNameTransformer() {
		return principalNameTransformer;
	}

	public void setPrincipalNameTransformer(
			PrincipalNameTransformer principalNameTransformer) {
		this.principalNameTransformer = principalNameTransformer;
	}

	public PasswordPolicyConfiguration getPasswordPolicyConfiguration() {
		return passwordPolicyConfiguration;
	}

	public void setPasswordPolicyConfiguration(
			PasswordPolicyConfiguration passwordPolicyConfiguration) {
		this.passwordPolicyConfiguration = passwordPolicyConfiguration;
	}

}
