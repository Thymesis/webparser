package httpparser;

public class Data {
    private String ip;
    private int port; 
    private String type;

    public void setIP(String ip){
        this.ip=ip;
    }
    public void setPort(int port){
        this.port=port;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getIP(){
        return ip;
    }
    public int getPort(){
        return port;
    }
    public String getType(){
        return type;
    }
}
