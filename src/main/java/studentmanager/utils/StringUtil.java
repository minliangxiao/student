package studentmanager.utils;

import java.util.Date;
import java.util.List;

public class StringUtil {
    public static String joinString(List<Long> list, String split){
        String ret = "";
        for(Long l:list){
            ret += l + split;
        }
        if(!"".equals(ret)){
            ret = ret.substring(0,ret.length() - split.length());
        }
        return ret;
    }
    /*随机生成学号的方法*/
    public static  String generateSn(String prefix,String suffix){//参数prefix代表string的前缀，suffix代表string的后缀
        return prefix+new Date().getTime()+suffix;
    }



}
