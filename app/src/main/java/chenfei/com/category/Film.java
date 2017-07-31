package chenfei.com.category;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/5/25.
 */

@Table(name = "film")

public class Film {
    @Column(name ="id", isId = true,autoGen=false)
    private  int id;
    @Column(name ="leixing")
    private  int leixing;
    @Column(name = "name" )
    private  String  name;
    @Column(name = "country")
    private  String  country;
    @Column(name = "actor")
    private  String actor;
    @Column(name = "publictime")
    private  String publictime;
    @Column(name = "description")
    private  String description;
    @Column(name = "imageurl")
    private  String imageurl;
    @Column(name = "filmurl")
    private  String filmurl;
    @Column(name = "filmtime")
    private  String  filmtime;
    @Column(name = "likenumber")
    private  int likenumber;
    @Column(name = "conmentnumber")
    private  int conmentnumber;
    @Column(name = "love")
    private  int love;
    @Column(name = "comedy")
    private  int comedy;
    @Column(name = "action")
    private  int action;
    @Column(name = "plot")
    private int plot;
    @Column(name = "sciencefiction")
    private int sciencefiction;
    @Column(name = "terror")
    private int terror;
    @Column(name = "animation")
    private  int animation;
    @Column(name = "Thriller")
    private  int Thriller;
    @Column(name = "Crime")
    private  int Crime;
    @Column(name = "Funny")
    private  int Funny;
    @Column(name = "USAfilm")
    private  int USAfilm;
    @Column(name = "niandai")
    private  String niandai;
    @Column(name = "doubanpingfen")
    private  float  doubanpingfen;
    @Column(name = "creattime")
    private  String creattime;
    @Column(name = "updatetime")
    private  String updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeixing() {
        return leixing;
    }

    public void setLeixing(int leixing) {
        this.leixing = leixing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPublictime() {
        return publictime;
    }

    public void setPublictime(String publictime) {
        this.publictime = publictime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getFilmurl() {
        return filmurl;
    }

    public void setFilmurl(String filmurl) {
        this.filmurl = filmurl;
    }

    public String getFilmtime() {
        return filmtime;
    }

    public void setFilmtime(String filmtime) {
        this.filmtime = filmtime;
    }

    public int getLikenumber() {
        return likenumber;
    }

    public void setLikenumber(int likenumber) {
        this.likenumber = likenumber;
    }

    public int getConmentnumber() {
        return conmentnumber;
    }

    public void setConmentnumber(int conmentnumber) {
        this.conmentnumber = conmentnumber;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getComedy() {
        return comedy;
    }

    public void setComedy(int comedy) {
        this.comedy = comedy;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getPlot() {
        return plot;
    }

    public void setPlot(int plot) {
        this.plot = plot;
    }

    public int getSciencefiction() {
        return sciencefiction;
    }

    public void setSciencefiction(int sciencefiction) {
        this.sciencefiction = sciencefiction;
    }

    public int getTerror() {
        return terror;
    }

    public void setTerror(int terror) {
        this.terror = terror;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getThriller() {
        return Thriller;
    }

    public void setThriller(int thriller) {
        Thriller = thriller;
    }

    public int getCrime() {
        return Crime;
    }

    public void setCrime(int crime) {
        Crime = crime;
    }

    public int getFunny() {
        return Funny;
    }

    public void setFunny(int funny) {
        Funny = funny;
    }

    public int getUSAfilm() {
        return USAfilm;
    }

    public void setUSAfilm(int USAfilm) {
        this.USAfilm = USAfilm;
    }

    public String getNiandai() {
        return niandai;
    }

    public void setNiandai(String niandai) {
        this.niandai = niandai;
    }

    public float getDoubanpingfen() {
        return doubanpingfen;
    }

    public void setDoubanpingfen(float doubanpingfen) {
        this.doubanpingfen = doubanpingfen;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }


    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", leixing=" + leixing +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", actor='" + actor + '\'' +
                ", publictime='" + publictime + '\'' +
                ", description='" + description + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", filmurl='" + filmurl + '\'' +
                ", filmtime='" + filmtime + '\'' +
                ", likenumber=" + likenumber +
                ", conmentnumber=" + conmentnumber +
                ", love=" + love +
                ", comedy=" + comedy +
                ", action=" + action +
                ", plot=" + plot +
                ", sciencefiction=" + sciencefiction +
                ", terror=" + terror +
                ", animation=" + animation +
                ", Thriller=" + Thriller +
                ", Crime=" + Crime +
                ", Funny=" + Funny +
                ", USAfilm=" + USAfilm +
                ", niandai='" + niandai + '\'' +
                ", doubanpingfen=" + doubanpingfen +
                ", creattime='" + creattime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
