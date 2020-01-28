package com.jdc.lokerriau.Activities;

public class Lowongan {
    private String instansi;
    private String name;
    private String imageURL;
    private String key;
    private String posisi;
    private String gaji;
    private String jenis;
    private String buka;
    private String tutup;
    private String ket;

    private int position;

    public Lowongan() {
        //empty constructor needed
    }
    public Lowongan(int position){
        this.position = position;
    }
    public Lowongan(String instansi, String name, String posisi, String gaji, String jenis, String buka, String tutup, String ket, String imageUrl) {
        this.setInstansi(instansi);
        this.setName(name);
        this.setPosisi(posisi);
        this.setGaji(gaji);
        this.setJenis(jenis);
        this.setBuka(buka);
        this.setKet(tutup);

        this.setKet(ket);
        this.setImageURL(imageUrl);
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getGaji() {
        return gaji;
    }

    public void setGaji(String gaji) {
        this.gaji = gaji;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getBuka() {
        return buka;
    }

    public void setBuka(String buka) {
        this.buka = buka;
    }

    public String getTutup() {
        return tutup;
    }

    public void setTutup(String tutup) {
        this.tutup = tutup;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
