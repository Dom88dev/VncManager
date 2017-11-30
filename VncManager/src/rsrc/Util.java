package rsrc;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * 각 클래스에서 사용되는 기타 기능성 메소드를 모아둔 클래스
 * 
 * @author Dominic
 *
 */
public class Util {

	/**
	 *  날짜가 yyyymmdd 형식으로 입력되었을 경우 java.sql.Date로 변경하는 메서드
	 * 
	 * @param date
	 * yyyymmdd형식으로 된 String 인자 값
	 * @return
	 * yyyy-mm-dd형식으로 변경하여 java.sql.Date객체로 반환된다.
	 */
    public static Date transformDate(String date)
    {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
        
        // Date로 변경하기 위해서는 날짜 형식을 yyyy-mm-dd로 변경해야 한다.
        SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
        
        java.util.Date tempDate = null;
        
        try {
            // 현재 yyyymmdd로된 날짜 형식으로 java.util.Date객체를 만든다.
            tempDate = beforeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        // java.util.Date를 yyyy-mm-dd 형식으로 변경하여 String로 반환한다.
        String transDate = afterFormat.format(tempDate);
        
        // 반환된 String 값을 Date로 변경한다.
        Date d = Date.valueOf(transDate);
        
        return d;
    }
}
