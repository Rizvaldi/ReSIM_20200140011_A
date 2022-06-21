package com.example.resim.user;

public class ReSIM {
    private  String id;
    private String nik;
    private String nohp;

    public ReSIM(String nik, String nohp ) {
        this.nik = nik;
        this.nohp = nohp;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getNik(){
        return nik;
    }

    public void setNik(String nik){
        this.nik = nik;
    }

    public String getNohp(){
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}
