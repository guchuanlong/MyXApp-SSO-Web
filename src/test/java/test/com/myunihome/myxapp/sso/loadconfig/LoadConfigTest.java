package test.com.myunihome.myxapp.sso.loadconfig;

import org.junit.Test;

import com.myunihome.myxapp.utils.loader.Load2ConfigCenter;

public class LoadConfigTest {
	@Test
	public void testLoadCnf(){
		 Load2ConfigCenter.main(new String[]{"H:/test"});
		 System.out.println("====ok===");
	}
}
