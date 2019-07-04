package rpf.authentication;

import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import rpf.pojo.User;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Component("primaryAuthenticationHandler")
public class UserAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        String username=credential.getUsername();
        String password=credential.getPassword();

        System.out.println("username=["+username+"]  password=["+password+"]");
        //自定义jdbc验证
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/sso_user?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        String sql="SELECT * FROM user WHERE username = ?";
        System.out.println(sql);
        User info = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));
        System.out.println("database username : "+ info.getUsername());
        System.out.println("database password : "+ info.getPassword());
        if (info==null){
            throw new AccountException("用户不存在");
        }else {
            System.out.println(info);
        }

        if (!info.getPassword().equals(password)){

            System.out.println("dateSourcePassword=["+info.getPassword()+"]");
            throw  new FailedLoginException("密码错误");
        }else{
            //自定义返回内容
//            HashMap<String, Object> returnInfo = new HashMap<>();
//            final List<MessageDescriptor> list=new ArrayList<>();
//            returnInfo.put("expired",info.getExpired());
//            returnInfo.put("userid","seits");
//            returnInfo.put("roles","%5B13%2C+3%2C+12%5D");
//            returnInfo.put("permissions","1%3A*%2C1%3Aaudit%2C1%3Acreate%2C1%3Adelete%2C1%3Aupdate%2C1%3Aview%2CAssyncException%3A*%2CAssyncException%3Aaudit%2CAssyncException%3Acreate%2CAssyncException%3Adelete%2CAssyncException%3Aupdate%2CAssyncException%3Aview%2CCommonMessage%3A*%2CCommonMessage%3Aaudit%2CCommonMessage%3Acreate%2CCommonMessage%3Adelete%2CCommonMessage%3Aupdate%2CCommonMessage%3Aview%2CDatCompression%3A*%2CDatCompression%3Aaudit%2CDatCompression%3Acreate%2CDatCompression%3Adelete%2CDatCompression%3Aupdate%2CDatCompression%3Aview%2CDatabaseSeparation%3A*%2CDatabaseSeparation%3Aaudit%2CDatabaseSeparation%3Acreate%2CDatabaseSeparation%3Adelete%2CDatabaseSeparation%3Aupdate%2CDatabaseSeparation%3Aview%2CDatalayer%3A*%2CDatalayer%3Aaudit%2CDatalayer%3Acreate%2CDatalayer%3Adelete%2CDatalayer%3Aupdate%2CDatalayer%3Aview%2CFieldSet%3A*%2CFieldSet%3Aaudit%2CFieldSet%3Acreate%2CFieldSet%3Adelete%2CFieldSet%3Aupdate%2CFieldSet%3Aview%2CFileExport%3A*%2CFileExport%3Aaudit%2CFileExport%3Acreate%2CFileExport%3Adelete%2CFileExport%3Aupdate%2CFileExport%3Aview%2CHisDataQuery%3A*%2CHisDataQuery%3Aaudit%2CHisDataQuery%3Acreate%2CHisDataQuery%3Adelete%2CHisDataQuery%3Aupdate%2CHisDataQuery%3Aview%2CImageButton%3A*%2CImageButton%3Aaudit%2CImageButton%3Acreate%2CImageButton%3Adelete%2CImageButton%3Aupdate%2CImageButton%3Aview%2CLayout%3A*%2CLayout%3Aaudit%2CLayout%3Acreate%2CLayout%3Adelete%2CLayout%3Aupdate%2CLayout%3Aview%2CLayout_UI%3A*%2CLayout_UI%3Aaudit%2CLayout_UI%3Acreate%2CLayout_UI%3Adelete%2CLayout_UI%3Aupdate%2CLayout_UI%3Aview%2CMenu%3A*%2CMenu%3Aaudit%2CMenu%3Acreate%2CMenu%3Adelete%2CMenu%3Aupdate%2CMenu%3Aview%2CMenuButton%3A*%2CMenuButton%3Aaudit%2CMenuButton%3Acreate%2CMenuButton%3Adelete%2CMenuButton%3Aupdate%2CMenuButton%3Aview%2CMessage_UI%3A*%2CMessage_UI%3Aaudit%2CMessage_UI%3Acreate%2CMessage_UI%3Adelete%2CMessage_UI%3Aupdate%2CMessage_UI%3Aview%2CNavigator_UI%3A*%2CNavigator_UI%3Aaudit%2CNavigator_UI%3Acreate%2CNavigator_UI%3Adelete%2CNavigator_UI%3Aupdate%2CNavigator_UI%3Aview%2COutlookBar%3A*%2COutlookBar%3Aaudit%2COutlookBar%3Acreate%2COutlookBar%3Adelete%2COutlookBar%3Aupdate%2COutlookBar%3Aview%2CPanel%3A*%2CPanel%3Aaudit%2CPanel%3Acreate%2CPanel%3Adelete%2CPanel%3Aupdate%2CPanel%3Aview%2CPoliceGpsInfo%3A*%2CPoliceGpsInfo%3Aaudit%2CPoliceGpsInfo%3Acreate%2CPoliceGpsInfo%3Adelete%2CPoliceGpsInfo%3Aupdate%2CPoliceGpsInfo%3Aview%2CPopUpWindow_UI%3A*%2CPopUpWindow_UI%3Aaudit%2CPopUpWindow_UI%3Acreate%2CPopUpWindow_UI%3Adelete%2CPopUpWindow_UI%3Aupdate%2CPopUpWindow_UI%3Aview%2CRadioButton%3A*%2CRadioButton%3Aaudit%2CRadioButton%3Acreate%2CRadioButton%3Adelete%2CRadioButton%3Aupdate%2CRadioButton%3Aview%2CRealDataQuery%3A*%2CRealDataQuery%3Aaudit%2CRealDataQuery%3Acreate%2CRealDataQuery%3Adelete%2CRealDataQuery%3Aupdate%2CRealDataQuery%3Aview%2CRealHisDataSeparation+%3A*%2CRealHisDataSeparation+%3Aaudit%2CRealHisDataSeparation+%3Acreate%2CRealHisDataSeparation+%3Adelete%2CRealHisDataSeparation+%3Aupdate%2CRealHisDataSeparation+%3Aview%2CRequestCertification%3A*%2CRequestCertification%3Aaudit%2CRequestCertification%3Acreate%2CRequestCertification%3Adelete%2CRequestCertification%3Aupdate%2CRequestCertification%3Aview%2CRequestEncoding%3A*%2CRequestEncoding%3Aaudit%2CRequestEncoding%3Acreate%2CRequestEncoding%3Adelete%2CRequestEncoding%3Aupdate%2CRequestEncoding%3Aview%2CRequestEncryption%3A*%2CRequestEncryption%3Aaudit%2CRequestEncryption%3Acreate%2CRequestEncryption%3Adelete%2CRequestEncryption%3Aupdate%2CRequestEncryption%3Aview%2CTableWindow%3A*%2CTableWindow%3Aaudit%2CTableWindow%3Acreate%2CTableWindow%3Adelete%2CTableWindow%3Aupdate%2CTableWindow%3Aview%2CTabs%3A*%2CTabs%3Aaudit%2CTabs%3Acreate%2CTabs%3Adelete%2CTabs%3Aupdate%2CTabs%3Aview%2CTest%2FTestPage%3A*%2CTest%2FTestPage%3Aaudit%2CTest%2FTestPage%3Acreate%2CTest%2FTestPage%3Adelete%2CTest%2FTestPage%3Aupdate%2CTest%2FTestPage%3Aview%2CTextImageButton%3A*%2CTextImageButton%3Aaudit%2CTextImageButton%3Acreate%2CTextImageButton%3Adelete%2CTextImageButton%3Aupdate%2CTextImageButton%3Aview%2CToolBarOverflow%3A*%2CToolBarOverflow%3Aaudit%2CToolBarOverflow%3Acreate%2CToolBarOverflow%3Adelete%2CToolBarOverflow%3Aupdate%2CToolBarOverflow%3Aview%2CTree%3A*%2CTree%3Aaudit%2CTree%3Acreate%2CTree%3Adelete%2CTree%3Aupdate%2CTree%3Aview%2CTreeWindow%3A*%2CTreeWindow%3Aaudit%2CTreeWindow%3Acreate%2CTreeWindow%3Adelete%2CTreeWindow%3Aupdate%2CTreeWindow%3Aview%2CUpdateWindow%3A*%2CUpdateWindow%3Aaudit%2CUpdateWindow%3Acreate%2CUpdateWindow%3Adelete%2CUpdateWindow%3Aupdate%2CUpdateWindow%3Aview%2CXssFilter%3A*%2CXssFilter%3Aaudit%2CXssFilter%3Acreate%2CXssFilter%3Adelete%2CXssFilter%3Aupdate%2CXssFilter%3Aview%2CactiveGps%3A*%2CactiveGps%3Aaudit%2CactiveGps%3Acreate%2CactiveGps%3Adelete%2CactiveGps%3Aupdate%2CactiveGps%3Aview%2Capp_interface%3A*%2Capp_interface%3Aaudit%2Capp_interface%3Acreate%2Capp_interface%3Adelete%2Capp_interface%3Aupdate%2Capp_interface%3Aview%2Capplication_controller%3A*%2Capplication_controller%3Aaudit%2Capplication_controller%3Acreate%2Capplication_controller%3Adelete%2Capplication_controller%3Aupdate%2Capplication_controller%3Aview%2Cassync_request%3A*%2Cassync_request%3Aaudit%2Cassync_request%3Acreate%2Cassync_request%3Adelete%2Cassync_request%3Aupdate%2Cassync_request%3Aview%2Cauthorize%3A*%2Cauthorize%3Aaudit%2Cauthorize%3Acreate%2Cauthorize%3Adelete%2Cauthorize%3Aupdate%2Cauthorize%3Aview%2Cbasic_ui%3A*%2Cbasic_ui%3Aaudit%2Cbasic_ui%3Acreate%2Cbasic_ui%3Adelete%2Cbasic_ui%3Aupdate%2Cbasic_ui%3Aview%2Cbutton_ui%3A*%2Cbutton_ui%3Aaudit%2Cbutton_ui%3Acreate%2Cbutton_ui%3Adelete%2Cbutton_ui%3Aupdate%2Cbutton_ui%3Aview%2Ccache_interacvive%3A*%2Ccache_interacvive%3Aaudit%2Ccache_interacvive%3Acreate%2Ccache_interacvive%3Adelete%2Ccache_interacvive%3Aupdate%2Ccache_interacvive%3Aview%2CchartDemo%3A*%2CchartDemo%3Aaudit%2CchartDemo%3Acreate%2CchartDemo%3Adelete%2CchartDemo%3Aupdate%2CchartDemo%3Aview%2Cclxxcx%3A*%2Cclxxcx%3Aaudit%2Cclxxcx%3Acreate%2Cclxxcx%3Adelete%2Cclxxcx%3Aupdate%2Cclxxcx%3Aview%2Ccommon_front_tools%3A*%2Ccommon_front_tools%3Aaudit%2Ccommon_front_tools%3Acreate%2Ccommon_front_tools%3Adelete%2Ccommon_front_tools%3Aupdate%2Ccommon_front_tools%3Aview%2Ccommon_ui%3A*%2Ccommon_ui%3Aaudit%2Ccommon_ui%3Acreate%2Ccommon_ui%3Adelete%2Ccommon_ui%3Aupdate%2Ccommon_ui%3Aview%2Ccontroller_exception%3A*%2Ccontroller_exception%3Aaudit%2Ccontroller_exception%3Acreate%2Ccontroller_exception%3Adelete%2Ccontroller_exception%3Aupdate%2Ccontroller_exception%3Aview%2Ccontroller_filter%3A*%2Ccontroller_filter%3Aaudit%2Ccontroller_filter%3Acreate%2Ccontroller_filter%3Adelete%2Ccontroller_filter%3Aupdate%2Ccontroller_filter%3Aview%2Ccontroller_interactive%3A*%2Ccontroller_interactive%3Aaudit%2Ccontroller_interactive%3Acreate%2Ccontroller_interactive%3Adelete%2Ccontroller_interactive%3Aupdate%2Ccontroller_interactive%3Aview%2Cdatalayer_interactive%3A*%2Cdatalayer_interactive%3Aaudit%2Cdatalayer_interactive%3Acreate%2Cdatalayer_interactive%3Adelete%2Cdatalayer_interactive%3Aupdate%2Cdatalayer_interactive%3Aview%2Cdemo-GIS%3A*%2Cdemo-GIS%3Aaudit%2Cdemo-GIS%3Acreate%2Cdemo-GIS%3Adelete%2Cdemo-GIS%3Aupdate%2Cdemo-GIS%3Aview%2Cdemo-fileOperate%3A*%2Cdemo-fileOperate%3Aaudit%2Cdemo-fileOperate%3Acreate%2Cdemo-fileOperate%3Adelete%2Cdemo-fileOperate%3Aupdate%2Cdemo-fileOperate%3Aview%2Cdemo-ghgl%3A*%2Cdemo-ghgl%3Aaudit%2Cdemo-ghgl%3Acreate%2Cdemo-ghgl%3Adelete%2Cdemo-ghgl%3Aupdate%2Cdemo-ghgl%3Aview%2Cdemo-zggl%3A*%2Cdemo-zggl%3Aaudit%2Cdemo-zggl%3Acreate%2Cdemo-zggl%3Adelete%2Cdemo-zggl%3Aupdate%2Cdemo-zggl%3Aview%2Cdemo-zgyzt%3A*%2Cdemo-zgyzt%3Aaudit%2Cdemo-zgyzt%3Acreate%2Cdemo-zgyzt%3Adelete%2Cdemo-zgyzt%3Aupdate%2Cdemo-zgyzt%3Aview%2Cdemo3%3A*%2Cdemo3%3Aaudit%2Cdemo3%3Acreate%2Cdemo3%3Adelete%2Cdemo3%3Aupdate%2Cdemo3%3Aview%2Cdemo%3A*%2Cdemo%3Aaudit%2Cdemo%3Acreate%2Cdemo%3Adelete%2Cdemo%3Aupdate%2Cdemo%3Aview%2CdeviceList%3A*%2CdeviceList%3Aaudit%2CdeviceList%3Acreate%2CdeviceList%3Adelete%2CdeviceList%3Aupdate%2CdeviceList%3Aview%2CdrawLine%3A*%2CdrawLine%3Aaudit%2CdrawLine%3Acreate%2CdrawLine%3Adelete%2CdrawLine%3Aupdate%2CdrawLine%3Aview%2Cexception-page%3A*%2Cexception-page%3Aaudit%2Cexception-page%3Acreate%2Cexception-page%3Adelete%2Cexception-page%3Aupdate%2Cexception-page%3Aview%2CfileDownload%3A*%2CfileDownload%3Aaudit%2CfileDownload%3Acreate%2CfileDownload%3Adelete%2CfileDownload%3Aupdate%2CfileDownload%3Aview%2CfileUpload%3A*%2CfileUpload%3Aaudit%2CfileUpload%3Acreate%2CfileUpload%3Adelete%2CfileUpload%3Aupdate%2CfileUpload%3Aview%2Cfront_architecture%3A*%2Cfront_architecture%3Aaudit%2Cfront_architecture%3Acreate%2Cfront_architecture%3Adelete%2Cfront_architecture%3Aupdate%2Cfront_architecture%3Aview%2Cfront_validation%3A*%2Cfront_validation%3Aaudit%2Cfront_validation%3Acreate%2Cfront_validation%3Adelete%2Cfront_validation%3Aupdate%2Cfront_validation%3Aview%2Cgis%3A*%2Cgis%3Aaudit%2Cgis%3Acreate%2Cgis%3Adelete%2Cgis%3Aupdate%2Cgis%3Aview%2Cgroup%3A*%2Cgroup%3Aaudit%2Cgroup%3Acreate%2Cgroup%3Adelete%2Cgroup%3Aupdate%2Cgroup%3Aview%2Cjs_util%3A*%2Cjs_util%3Aaudit%2Cjs_util%3Acreate%2Cjs_util%3Adelete%2Cjs_util%3Aupdate%2Cjs_util%3Aview%2ClineVolumeHsaRtManageList%3A*%2ClineVolumeHsaRtManageList%3Aaudit%2ClineVolumeHsaRtManageList%3Acreate%2ClineVolumeHsaRtManageList%3Adelete%2ClineVolumeHsaRtManageList%3Aupdate%2ClineVolumeHsaRtManageList%3Aview%2CmessageDemo%3A*%2CmessageDemo%3Aaudit%2CmessageDemo%3Acreate%2CmessageDemo%3Adelete%2CmessageDemo%3Aupdate%2CmessageD...");
            return  createHandlerResult(credential,new SimplePrincipal(username),null);
        }
    }
}
