package cn.jsprun.domain;
public class Debates  implements java.io.Serializable {
	private static final long serialVersionUID = -6120411520143372088L;
	private Integer tid;				
     private Integer uid;				
     private Integer starttime;			
     private Integer endtime;			
     private Integer affirmdebaters;	
     private Integer negadebaters;		
     private Integer affirmvotes;		
     private Integer negavotes;			
     private String umpire;				
     private Byte winner;				
     private String bestdebater;		
     private String affirmpoint;		
     private String negapoint;			
     private String umpirepoint;		
     private String affirmvoterids;		
     private String negavoterids;		
     private Integer affirmreplies;		
     private Integer negareplies;		
    public Debates() {
    }
    public Debates(Integer tid, Integer uid, Integer starttime, Integer endtime, Integer affirmdebaters, Integer negadebaters, Integer affirmvotes, Integer negavotes, String umpire, Byte winner, String bestdebater, String affirmpoint, String negapoint, String umpirepoint, String affirmvoterids, String negavoterids, Integer affirmreplies, Integer negareplies) {
        this.tid = tid;
        this.uid = uid;
        this.starttime = starttime;
        this.endtime = endtime;
        this.affirmdebaters = affirmdebaters;
        this.negadebaters = negadebaters;
        this.affirmvotes = affirmvotes;
        this.negavotes = negavotes;
        this.umpire = umpire;
        this.winner = winner;
        this.bestdebater = bestdebater;
        this.affirmpoint = affirmpoint;
        this.negapoint = negapoint;
        this.umpirepoint = umpirepoint;
        this.affirmvoterids = affirmvoterids;
        this.negavoterids = negavoterids;
        this.affirmreplies = affirmreplies;
        this.negareplies = negareplies;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getStarttime() {
        return this.starttime;
    }
    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }
    public Integer getEndtime() {
        return this.endtime;
    }
    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }
    public Integer getAffirmdebaters() {
        return this.affirmdebaters;
    }
    public void setAffirmdebaters(Integer affirmdebaters) {
        this.affirmdebaters = affirmdebaters;
    }
    public Integer getNegadebaters() {
        return this.negadebaters;
    }
    public void setNegadebaters(Integer negadebaters) {
        this.negadebaters = negadebaters;
    }
    public Integer getAffirmvotes() {
        return this.affirmvotes;
    }
    public void setAffirmvotes(Integer affirmvotes) {
        this.affirmvotes = affirmvotes;
    }
    public Integer getNegavotes() {
        return this.negavotes;
    }
    public void setNegavotes(Integer negavotes) {
        this.negavotes = negavotes;
    }
    public String getUmpire() {
        return this.umpire;
    }
    public void setUmpire(String umpire) {
        this.umpire = umpire;
    }
    public Byte getWinner() {
        return this.winner;
    }
    public void setWinner(Byte winner) {
        this.winner = winner;
    }
    public String getBestdebater() {
        return this.bestdebater;
    }
    public void setBestdebater(String bestdebater) {
        this.bestdebater = bestdebater;
    }
    public String getAffirmpoint() {
        return this.affirmpoint;
    }
    public void setAffirmpoint(String affirmpoint) {
        this.affirmpoint = affirmpoint;
    }
    public String getNegapoint() {
        return this.negapoint;
    }
    public void setNegapoint(String negapoint) {
        this.negapoint = negapoint;
    }
    public String getUmpirepoint() {
        return this.umpirepoint;
    }
    public void setUmpirepoint(String umpirepoint) {
        this.umpirepoint = umpirepoint;
    }
    public String getAffirmvoterids() {
        return this.affirmvoterids;
    }
    public void setAffirmvoterids(String affirmvoterids) {
        this.affirmvoterids = affirmvoterids;
    }
    public String getNegavoterids() {
        return this.negavoterids;
    }
    public void setNegavoterids(String negavoterids) {
        this.negavoterids = negavoterids;
    }
    public Integer getAffirmreplies() {
        return this.affirmreplies;
    }
    public void setAffirmreplies(Integer affirmreplies) {
        this.affirmreplies = affirmreplies;
    }
    public Integer getNegareplies() {
        return this.negareplies;
    }
    public void setNegareplies(Integer negareplies) {
        this.negareplies = negareplies;
    }
}