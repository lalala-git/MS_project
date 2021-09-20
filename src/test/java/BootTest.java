import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.yyds.PortalApplication;
import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.UserBaseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {PortalApplication.class})
@RunWith(SpringRunner.class)
public class BootTest {

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    @Test
    public void test01() {
        UserBaseInfo userBaseInfo = userBaseInfoDao.selectUserByEmail("root");
        System.out.println(userBaseInfo);
    }

    @Test
    public void test02() {
        String securePassword = SecureUtil.md5("root");
        System.out.println(securePassword);
    }

}
