package example.상호작용;

public class Service2 {

    private static Service2 싱글톤 = new Service2();
    private Service2(){}
    public static Service2 getInstance(){ return 싱글톤; }

    public void 메소드2(){}

}
