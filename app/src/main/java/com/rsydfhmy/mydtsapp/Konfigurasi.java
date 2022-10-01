package com.rsydfhmy.mydtsapp;

import java.util.List;

public class Konfigurasi {
//    private int id;
    private String nama;
    private String alamat;
    private String jenis_kelamin;
    private byte[] foto;
    private String Lokasi;
    private String noHp;
    public Konfigurasi(String nama, String alamat , String jenis_kelamin, String noHp , String lokasi, byte[] foto) {
//        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.Lokasi= lokasi;
        this.foto = foto;
        this.noHp = noHp;
    }

    public Konfigurasi(TampilData tampilData, List<Konfigurasi> daftarMhs) {
        // empty konstruktor
    }

//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat ;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp(){
        return noHp;
    }
    public void setNoHp(String noHp){

    }
    public String getJenisKelamin(){
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin){
        jenis_kelamin = this.jenis_kelamin;
    }
    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    public String getLokasi(){
        return Lokasi;
    }
    public void setLokasi(String Lokasi){
        this.Lokasi = Lokasi;
    }
}
