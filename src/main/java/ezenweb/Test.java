package ezenweb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();list.add("수박"); list.add("딸기"); list.add("포도"); list.add("사과");
        // 1.
        for( int i = 0 ; i<list.size() ; i++ ){ System.out.println( "for1 : "+ list.get(i) ); }
        // 2.
        for( String str : list ){ System.out.println("for2 : "+ str ); }
        // 3.
        list.forEach( str -> { System.out.println("for3 : "+  str ); });
        // 4.
        List<String> newList = list.stream().map( str ->{ System.out.println( "for4 : "+str ); return str;  } ).collect(Collectors.toList());
        // 5.
        List<String> newArrays = list.stream().filter(str ->  { return str.equals("포도");  } ).collect(Collectors.toList());
    }
}
