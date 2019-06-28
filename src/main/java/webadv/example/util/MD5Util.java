package webadv.example.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    
    /**
     * 加密方法
     * @param src
     * @return
     */
    public String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
    
    //固定盐
    private static final String salt = "1a2b3c4d";
    
    /**
     * 将用户输入的明文密码与固定盐进行拼装后再进行MD5加密
     * @param inputPass
     * @return
     */
    public String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }
    
    /**
     * 将form表单中的密码转换成数据库中存储的密码
     * @param formPass
     * @param salt 随机盐
     * @return
     */
    public String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    
    public String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
    
}
