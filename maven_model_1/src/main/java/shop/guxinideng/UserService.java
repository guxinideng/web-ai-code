package shop.guxinideng;

import java.time.LocalDate;
import java.time.Period;

/**
 * 用户业务类：根据身份证号获取年龄、性别。
 */
public class UserService {

    /** 根据身份证号计算年龄（18位，第7~14位为出生日期 yyyyMMdd） */
    public Integer getAge(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            throw new IllegalArgumentException("身份证号长度必须为18位");
        }
        String birth = idCard.substring(6, 14);
        LocalDate birthday = LocalDate.of(
                Integer.parseInt(birth.substring(0, 4)),
                Integer.parseInt(birth.substring(4, 6)),
                Integer.parseInt(birth.substring(6, 8)));
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    /** 根据身份证号获取性别（第17位：奇数为男，偶数为女） */
    public String getGender(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            throw new IllegalArgumentException("身份证号长度必须为18位");
        }
        return Integer.parseInt(idCard.substring(16, 17)) % 2 == 1 ? "男" : "女";
    }
}
