package cn.jsprun.domain;
public class Usergroups  implements java.io.Serializable {
	private static final long serialVersionUID = 8244008497125548157L;
     private Short groupid;
     private Short radminid;
     private String type;
     private String system;
     private String grouptitle;
     private Integer creditshigher;
     private Integer creditslower;
     private Short stars;
     private String color;
     private String groupavatar;
     private Short readaccess;
     private Byte allowvisit;
     private Byte allowpost;
     private Byte allowreply;
     private Byte allowpostpoll;
     private Byte allowpostreward;
     private Byte allowposttrade;
     private Byte allowpostactivity;
     private Byte allowpostvideo;
     private Byte allowdirectpost;
     private Byte allowgetattach;
     private Byte allowpostattach;
     private Byte allowvote;
     private Byte allowmultigroups;
     private Byte allowsearch;
     private Byte allowavatar;
     private Byte allowcstatus;
     private Byte allowuseblog;
     private Byte allowinvisible;
     private Byte allowtransfer;
     private Byte allowsetreadperm;
     private Byte allowsetattachperm;
     private Byte allowhidecode;
     private Byte allowhtml;
     private Byte allowcusbbcode;
     private Byte allowanonymous;
     private Byte allownickname;
     private Byte allowsigbbcode;
     private Byte allowsigimgcode;
     private Byte allowviewpro;
     private Byte allowviewstats;
     private Byte disableperiodctrl;
     private Byte reasonpm;
     private Short maxprice;
     private Short maxpmnum;
     private Short maxsigsize;
     private Integer maxattachsize;
     private Integer maxsizeperday;
     private Short maxpostsperhour;
     private String attachextensions;
     private String raterange;
     private Short mintradeprice;
     private Short maxtradeprice;
     private Short minrewardprice;
     private Short maxrewardprice;
     private Byte magicsdiscount;
     private Byte allowmagics;
     private Short maxmagicsweight;
     private Byte allowbiobbcode;
     private Byte allowbioimgcode;
     private Short maxbiosize;
     private Byte allowinvite;
     private Byte allowmailinvite;
     private Short maxinvitenum;
     private Short inviteprice;
     private Short maxinviteday;
     private Byte allowpostdebate;
     private Byte tradestick;
     private Byte allowviewdigest;
    public Byte getAllowviewdigest() {
		return allowviewdigest;
	}
	public void setAllowviewdigest(Byte allowviewdigest) {
		this.allowviewdigest = allowviewdigest;
	}
    public Usergroups() {
    }
    public Usergroups(Short radminid, String type, String system, String grouptitle, Integer creditshigher, Integer creditslower, Short stars, String color, String groupavatar, Short readaccess, Byte allowvisit, Byte allowpost, Byte allowreply, Byte allowpostpoll, Byte allowpostreward, Byte allowposttrade, Byte allowpostactivity, Byte allowpostvideo, Byte allowdirectpost, Byte allowgetattach, Byte allowpostattach, Byte allowvote, Byte allowmultigroups, Byte allowsearch, Byte allowavatar, Byte allowcstatus, Byte allowuseblog, Byte allowinvisible, Byte allowtransfer, Byte allowsetreadperm, Byte allowsetattachperm, Byte allowhidecode, Byte allowhtml, Byte allowcusbbcode, Byte allowanonymous, Byte allownickname, Byte allowsigbbcode, Byte allowsigimgcode, Byte allowviewpro, Byte allowviewstats, Byte disableperiodctrl, Byte reasonpm, Short maxprice, Short maxpmnum, Short maxsigsize, Integer maxattachsize, Integer maxsizeperday, Short maxpostsperhour, String attachextensions, String raterange, Short mintradeprice, Short maxtradeprice, Short minrewardprice, Short maxrewardprice, Byte magicsdiscount, Byte allowmagics, Short maxmagicsweight, Byte allowbiobbcode, Byte allowbioimgcode, Short maxbiosize, Byte allowinvite, Byte allowmailinvite, Short maxinvitenum, Short inviteprice, Short maxinviteday, Byte allowpostdebate, Byte tradestick,Byte allowviewdigest) {
        this.radminid = radminid;
        this.type = type;
        this.system = system;
        this.grouptitle = grouptitle;
        this.creditshigher = creditshigher;
        this.creditslower = creditslower;
        this.stars = stars;
        this.color = color;
        this.groupavatar = groupavatar;
        this.readaccess = readaccess;
        this.allowvisit = allowvisit;
        this.allowpost = allowpost;
        this.allowreply = allowreply;
        this.allowpostpoll = allowpostpoll;
        this.allowpostreward = allowpostreward;
        this.allowposttrade = allowposttrade;
        this.allowpostactivity = allowpostactivity;
        this.allowpostvideo = allowpostvideo;
        this.allowdirectpost = allowdirectpost;
        this.allowgetattach = allowgetattach;
        this.allowpostattach = allowpostattach;
        this.allowvote = allowvote;
        this.allowmultigroups = allowmultigroups;
        this.allowsearch = allowsearch;
        this.allowavatar = allowavatar;
        this.allowcstatus = allowcstatus;
        this.allowuseblog = allowuseblog;
        this.allowinvisible = allowinvisible;
        this.allowtransfer = allowtransfer;
        this.allowsetreadperm = allowsetreadperm;
        this.allowsetattachperm = allowsetattachperm;
        this.allowhidecode = allowhidecode;
        this.allowhtml = allowhtml;
        this.allowcusbbcode = allowcusbbcode;
        this.allowanonymous = allowanonymous;
        this.allownickname = allownickname;
        this.allowsigbbcode = allowsigbbcode;
        this.allowsigimgcode = allowsigimgcode;
        this.allowviewpro = allowviewpro;
        this.allowviewstats = allowviewstats;
        this.disableperiodctrl = disableperiodctrl;
        this.reasonpm = reasonpm;
        this.maxprice = maxprice;
        this.maxpmnum = maxpmnum;
        this.maxsigsize = maxsigsize;
        this.maxattachsize = maxattachsize;
        this.maxsizeperday = maxsizeperday;
        this.maxpostsperhour = maxpostsperhour;
        this.attachextensions = attachextensions;
        this.raterange = raterange;
        this.mintradeprice = mintradeprice;
        this.maxtradeprice = maxtradeprice;
        this.minrewardprice = minrewardprice;
        this.maxrewardprice = maxrewardprice;
        this.magicsdiscount = magicsdiscount;
        this.allowmagics = allowmagics;
        this.maxmagicsweight = maxmagicsweight;
        this.allowbiobbcode = allowbiobbcode;
        this.allowbioimgcode = allowbioimgcode;
        this.maxbiosize = maxbiosize;
        this.allowinvite = allowinvite;
        this.allowmailinvite = allowmailinvite;
        this.maxinvitenum = maxinvitenum;
        this.inviteprice = inviteprice;
        this.maxinviteday = maxinviteday;
        this.allowpostdebate = allowpostdebate;
        this.tradestick = tradestick;
        this.allowviewdigest = allowviewdigest;
    }
    public Short getGroupid() {
        return this.groupid;
    }
    public void setGroupid(Short groupid) {
        this.groupid = groupid;
    }
    public Short getRadminid() {
        return this.radminid;
    }
    public void setRadminid(Short radminid) {
        this.radminid = radminid;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSystem() {
        return this.system;
    }
    public void setSystem(String system) {
        this.system = system;
    }
    public String getGrouptitle() {
        return this.grouptitle;
    }
    public void setGrouptitle(String grouptitle) {
        this.grouptitle = grouptitle;
    }
    public Integer getCreditshigher() {
        return this.creditshigher;
    }
    public void setCreditshigher(Integer creditshigher) {
        this.creditshigher = creditshigher;
    }
    public Integer getCreditslower() {
        return this.creditslower;
    }
    public void setCreditslower(Integer creditslower) {
        this.creditslower = creditslower;
    }
    public Short getStars() {
        return this.stars;
    }
    public void setStars(Short stars) {
        this.stars = stars;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getGroupavatar() {
        return this.groupavatar;
    }
    public void setGroupavatar(String groupavatar) {
        this.groupavatar = groupavatar;
    }
    public Short getReadaccess() {
        return this.readaccess;
    }
    public void setReadaccess(Short readaccess) {
        this.readaccess = readaccess;
    }
    public Byte getAllowvisit() {
        return this.allowvisit;
    }
    public void setAllowvisit(Byte allowvisit) {
        this.allowvisit = allowvisit;
    }
    public Byte getAllowpost() {
        return this.allowpost;
    }
    public void setAllowpost(Byte allowpost) {
        this.allowpost = allowpost;
    }
    public Byte getAllowreply() {
        return this.allowreply;
    }
    public void setAllowreply(Byte allowreply) {
        this.allowreply = allowreply;
    }
    public Byte getAllowpostpoll() {
        return this.allowpostpoll;
    }
    public void setAllowpostpoll(Byte allowpostpoll) {
        this.allowpostpoll = allowpostpoll;
    }
    public Byte getAllowpostreward() {
        return this.allowpostreward;
    }
    public void setAllowpostreward(Byte allowpostreward) {
        this.allowpostreward = allowpostreward;
    }
    public Byte getAllowposttrade() {
        return this.allowposttrade;
    }
    public void setAllowposttrade(Byte allowposttrade) {
        this.allowposttrade = allowposttrade;
    }
    public Byte getAllowpostactivity() {
        return this.allowpostactivity;
    }
    public void setAllowpostactivity(Byte allowpostactivity) {
        this.allowpostactivity = allowpostactivity;
    }
    public Byte getAllowpostvideo() {
        return this.allowpostvideo;
    }
    public void setAllowpostvideo(Byte allowpostvideo) {
        this.allowpostvideo = allowpostvideo;
    }
    public Byte getAllowdirectpost() {
        return this.allowdirectpost;
    }
    public void setAllowdirectpost(Byte allowdirectpost) {
        this.allowdirectpost = allowdirectpost;
    }
    public Byte getAllowgetattach() {
        return this.allowgetattach;
    }
    public void setAllowgetattach(Byte allowgetattach) {
        this.allowgetattach = allowgetattach;
    }
    public Byte getAllowpostattach() {
        return this.allowpostattach;
    }
    public void setAllowpostattach(Byte allowpostattach) {
        this.allowpostattach = allowpostattach;
    }
    public Byte getAllowvote() {
        return this.allowvote;
    }
    public void setAllowvote(Byte allowvote) {
        this.allowvote = allowvote;
    }
    public Byte getAllowmultigroups() {
        return this.allowmultigroups;
    }
    public void setAllowmultigroups(Byte allowmultigroups) {
        this.allowmultigroups = allowmultigroups;
    }
    public Byte getAllowsearch() {
        return this.allowsearch;
    }
    public void setAllowsearch(Byte allowsearch) {
        this.allowsearch = allowsearch;
    }
    public Byte getAllowavatar() {
        return this.allowavatar;
    }
    public void setAllowavatar(Byte allowavatar) {
        this.allowavatar = allowavatar;
    }
    public Byte getAllowcstatus() {
        return this.allowcstatus;
    }
    public void setAllowcstatus(Byte allowcstatus) {
        this.allowcstatus = allowcstatus;
    }
    public Byte getAllowuseblog() {
        return this.allowuseblog;
    }
    public void setAllowuseblog(Byte allowuseblog) {
        this.allowuseblog = allowuseblog;
    }
    public Byte getAllowinvisible() {
        return this.allowinvisible;
    }
    public void setAllowinvisible(Byte allowinvisible) {
        this.allowinvisible = allowinvisible;
    }
    public Byte getAllowtransfer() {
        return this.allowtransfer;
    }
    public void setAllowtransfer(Byte allowtransfer) {
        this.allowtransfer = allowtransfer;
    }
    public Byte getAllowsetreadperm() {
        return this.allowsetreadperm;
    }
    public void setAllowsetreadperm(Byte allowsetreadperm) {
        this.allowsetreadperm = allowsetreadperm;
    }
    public Byte getAllowsetattachperm() {
        return this.allowsetattachperm;
    }
    public void setAllowsetattachperm(Byte allowsetattachperm) {
        this.allowsetattachperm = allowsetattachperm;
    }
    public Byte getAllowhidecode() {
        return this.allowhidecode;
    }
    public void setAllowhidecode(Byte allowhidecode) {
        this.allowhidecode = allowhidecode;
    }
    public Byte getAllowhtml() {
        return this.allowhtml;
    }
    public void setAllowhtml(Byte allowhtml) {
        this.allowhtml = allowhtml;
    }
    public Byte getAllowcusbbcode() {
        return this.allowcusbbcode;
    }
    public void setAllowcusbbcode(Byte allowcusbbcode) {
        this.allowcusbbcode = allowcusbbcode;
    }
    public Byte getAllowanonymous() {
        return this.allowanonymous;
    }
    public void setAllowanonymous(Byte allowanonymous) {
        this.allowanonymous = allowanonymous;
    }
    public Byte getAllownickname() {
        return this.allownickname;
    }
    public void setAllownickname(Byte allownickname) {
        this.allownickname = allownickname;
    }
    public Byte getAllowsigbbcode() {
        return this.allowsigbbcode;
    }
    public void setAllowsigbbcode(Byte allowsigbbcode) {
        this.allowsigbbcode = allowsigbbcode;
    }
    public Byte getAllowsigimgcode() {
        return this.allowsigimgcode;
    }
    public void setAllowsigimgcode(Byte allowsigimgcode) {
        this.allowsigimgcode = allowsigimgcode;
    }
    public Byte getAllowviewpro() {
        return this.allowviewpro;
    }
    public void setAllowviewpro(Byte allowviewpro) {
        this.allowviewpro = allowviewpro;
    }
    public Byte getAllowviewstats() {
        return this.allowviewstats;
    }
    public void setAllowviewstats(Byte allowviewstats) {
        this.allowviewstats = allowviewstats;
    }
    public Byte getDisableperiodctrl() {
        return this.disableperiodctrl;
    }
    public void setDisableperiodctrl(Byte disableperiodctrl) {
        this.disableperiodctrl = disableperiodctrl;
    }
    public Byte getReasonpm() {
        return this.reasonpm;
    }
    public void setReasonpm(Byte reasonpm) {
        this.reasonpm = reasonpm;
    }
    public Short getMaxprice() {
        return this.maxprice;
    }
    public void setMaxprice(Short maxprice) {
        this.maxprice = maxprice;
    }
    public Short getMaxpmnum() {
        return this.maxpmnum;
    }
    public void setMaxpmnum(Short maxpmnum) {
        this.maxpmnum = maxpmnum;
    }
    public Short getMaxsigsize() {
        return this.maxsigsize;
    }
    public void setMaxsigsize(Short maxsigsize) {
        this.maxsigsize = maxsigsize;
    }
    public Integer getMaxattachsize() {
        return this.maxattachsize;
    }
    public void setMaxattachsize(Integer maxattachsize) {
        this.maxattachsize = maxattachsize;
    }
    public Integer getMaxsizeperday() {
        return this.maxsizeperday;
    }
    public void setMaxsizeperday(Integer maxsizeperday) {
        this.maxsizeperday = maxsizeperday;
    }
    public Short getMaxpostsperhour() {
        return this.maxpostsperhour;
    }
    public void setMaxpostsperhour(Short maxpostsperhour) {
        this.maxpostsperhour = maxpostsperhour;
    }
    public String getAttachextensions() {
        return this.attachextensions;
    }
    public void setAttachextensions(String attachextensions) {
        this.attachextensions = attachextensions;
    }
    public String getRaterange() {
        return this.raterange;
    }
    public void setRaterange(String raterange) {
        this.raterange = raterange;
    }
    public Short getMintradeprice() {
        return this.mintradeprice;
    }
    public void setMintradeprice(Short mintradeprice) {
        this.mintradeprice = mintradeprice;
    }
    public Short getMaxtradeprice() {
        return this.maxtradeprice;
    }
    public void setMaxtradeprice(Short maxtradeprice) {
        this.maxtradeprice = maxtradeprice;
    }
    public Short getMinrewardprice() {
        return this.minrewardprice;
    }
    public void setMinrewardprice(Short minrewardprice) {
        this.minrewardprice = minrewardprice;
    }
    public Short getMaxrewardprice() {
        return this.maxrewardprice;
    }
    public void setMaxrewardprice(Short maxrewardprice) {
        this.maxrewardprice = maxrewardprice;
    }
    public Byte getMagicsdiscount() {
        return this.magicsdiscount;
    }
    public void setMagicsdiscount(Byte magicsdiscount) {
        this.magicsdiscount = magicsdiscount;
    }
    public Byte getAllowmagics() {
        return this.allowmagics;
    }
    public void setAllowmagics(Byte allowmagics) {
        this.allowmagics = allowmagics;
    }
    public Short getMaxmagicsweight() {
        return this.maxmagicsweight;
    }
    public void setMaxmagicsweight(Short maxmagicsweight) {
        this.maxmagicsweight = maxmagicsweight;
    }
    public Byte getAllowbiobbcode() {
        return this.allowbiobbcode;
    }
    public void setAllowbiobbcode(Byte allowbiobbcode) {
        this.allowbiobbcode = allowbiobbcode;
    }
    public Byte getAllowbioimgcode() {
        return this.allowbioimgcode;
    }
    public void setAllowbioimgcode(Byte allowbioimgcode) {
        this.allowbioimgcode = allowbioimgcode;
    }
    public Short getMaxbiosize() {
        return this.maxbiosize;
    }
    public void setMaxbiosize(Short maxbiosize) {
        this.maxbiosize = maxbiosize;
    }
    public Byte getAllowinvite() {
        return this.allowinvite;
    }
    public void setAllowinvite(Byte allowinvite) {
        this.allowinvite = allowinvite;
    }
    public Byte getAllowmailinvite() {
        return this.allowmailinvite;
    }
    public void setAllowmailinvite(Byte allowmailinvite) {
        this.allowmailinvite = allowmailinvite;
    }
    public Short getMaxinvitenum() {
        return this.maxinvitenum;
    }
    public void setMaxinvitenum(Short maxinvitenum) {
        this.maxinvitenum = maxinvitenum;
    }
    public Short getInviteprice() {
        return this.inviteprice;
    }
    public void setInviteprice(Short inviteprice) {
        this.inviteprice = inviteprice;
    }
    public Short getMaxinviteday() {
        return this.maxinviteday;
    }
    public void setMaxinviteday(Short maxinviteday) {
        this.maxinviteday = maxinviteday;
    }
    public Byte getAllowpostdebate() {
        return this.allowpostdebate;
    }
    public void setAllowpostdebate(Byte allowpostdebate) {
        this.allowpostdebate = allowpostdebate;
    }
    public Byte getTradestick() {
        return this.tradestick;
    }
    public void setTradestick(Byte tradestick) {
        this.tradestick = tradestick;
    }
}